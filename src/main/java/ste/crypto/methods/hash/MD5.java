package ste.crypto.methods.hash;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.methods.symmetric.AbstractSymmetricCryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by marklabenski on 04.07.17.
 */
public class MD5 extends AbstractMessageDigestMethod {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("iterationCount", new MethodOptionDescription("Int"));
            }
        };
        CryptoMethod.availableCryptoMethods = new HashMap<String, AlgoMethodDescription>()
        {
            {
                put("hash", new AlgoMethodDescription(Arrays.asList("input"), "hashValue"));
            }
        };
    }

    @Override
    public TransferableCryptoDetails hash(CryptoSettings cryptoSettings, String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        Integer iterationCount = Integer.parseInt(cryptoSettings.getStringOption("iterationCount"));
        for(Integer i = 0; i <= iterationCount; i++) {
            md.update(input.getBytes("UTF-8"));
        }

        String base64Secret = new String(Base64.encode(md.digest()), "US-ASCII");

        return new TransferableCryptoDetails("hashValue", base64Secret, cryptoSettings);
    }
}

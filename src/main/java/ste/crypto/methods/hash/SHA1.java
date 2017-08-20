package ste.crypto.methods.hash;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by marklabenski on 04.07.17.
 */
public class SHA1 extends AbstractMessageDigestMethod {
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
        MessageDigest md = MessageDigest.getInstance("SHA1");
        Integer iterationCount = Integer.parseInt(cryptoSettings.getStringOption("iterationCount"));
        for(Integer i = 0; i < iterationCount; i++) {
            md.update(input.getBytes("UTF-8"));
        }

        return new TransferableCryptoDetails("hashValue", this.convertDigestBytesToBase64String(md.digest()), cryptoSettings);
    }
}

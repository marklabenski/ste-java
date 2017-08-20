package ste.crypto.methods.symmetric;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.methods.symmetric.AbstractPBCryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by marklabenski on 04.07.17.
 */
public class SHAAndRC4 extends AbstractPBCryptoMethod {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("password", new MethodOptionDescription("String"));
                put("salt", new MethodOptionDescription("String"));
                put("iterationCount", new MethodOptionDescription("String"));
            }
        };
        CryptoMethod.availableCryptoMethods = new HashMap<String, AlgoMethodDescription>()
        {
            {
                put("encrypt", new AlgoMethodDescription(Arrays.asList("plainText"), "cipherText"));
                put("decrypt", new AlgoMethodDescription(Arrays.asList("cipherText"), "plainText"));
            }
        };
    }

    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String cipherText) throws Exception {

        Key    sKey = generateKey(cryptoSettings);
        Cipher cipher = Cipher.getInstance("PBEWithSHAAnd40BitRC4", "BC");


        cipher.init(Cipher.DECRYPT_MODE, sKey);

        byte[] base64DecodedCipherText = Base64.decode(cipherText.getBytes());
        byte[] text = cipher.doFinal(base64DecodedCipherText);

        return new TransferableCryptoDetails("plainText", new String(text, "US-ASCII"), cryptoSettings);
    }

    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {

        Key sKey = generateKey(cryptoSettings);
        Cipher cipher = Cipher.getInstance("PBEWithSHAAnd40BitRC4", "BC");

        String base64Key = new String(Base64.encode(sKey.getEncoded()));
        cryptoSettings.addOption("key", base64Key);

        cipher.init(Cipher.ENCRYPT_MODE, sKey);

        String base64Secret = new String(Base64.encode(cipher.doFinal(plainText.getBytes("UTF-8"))), "US-ASCII");

        return new TransferableCryptoDetails("cipherText", base64Secret, cryptoSettings);
    }

    public Key generateKey(CryptoSettings cryptoSettings) throws Exception {
        byte[] passwordBytes = Base64.decode(cryptoSettings.getStringOption("password").getBytes("UTF-8"));
        char[] password = new String(passwordBytes).toCharArray();
        byte[] saltBytes = Base64.decode(cryptoSettings.getStringOption("salt").getBytes("UTF-8"));
        Integer iterationCount = Integer.parseInt(cryptoSettings.getStringOption("iterationCount"));

        PBEKeySpec pbeSpec = new PBEKeySpec(password, saltBytes, iterationCount);
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance("PBEWithSHAAnd40BitRC4", "BC");
        return keyFact.generateSecret(pbeSpec);
    }
}

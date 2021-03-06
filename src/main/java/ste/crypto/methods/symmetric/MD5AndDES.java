package ste.crypto.methods.symmetric;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.Key;
import java.util.Arrays;
import java.util.HashMap;

/**
 * PBEWithMD5AndDES implementation
 *
 * @author Mark Labenski
 */
public class MD5AndDES extends AbstractPBCryptoMethod {
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

    /**
     * decrypt a given cipherText with password, salt and iteration count from the settings
     *
     * @param cryptoSettings
     * @param cipherText
     * @return Settings and the plainText as payload
     * @throws Exception
     */
    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String cipherText) throws Exception {
        Key    sKey = generateKey(cryptoSettings);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES", "BC");

        cipher.init(Cipher.DECRYPT_MODE, sKey);

        byte[] base64DecodedCipherText = Base64.decode(cipherText.getBytes());
        byte[] plainText = cipher.doFinal(base64DecodedCipherText);

        return new TransferableCryptoDetails("plainText", new String(plainText, "US-ASCII"), cryptoSettings);
    }

    /**
     * encrypt a given plainText with password, salt and iteration count from the settings
     *
     * @param cryptoSettings
     * @param plainText
     * @return Settings and the cipherText as payload
     * @throws Exception
     */
    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {
        Key sKey = generateKey(cryptoSettings);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES", "BC");

        cipher.init(Cipher.ENCRYPT_MODE, sKey);

        String cipherText = new String(Base64.encode(cipher.doFinal(plainText.getBytes("UTF-8"))), "US-ASCII");

        return new TransferableCryptoDetails("cipherText", cipherText, cryptoSettings);
    }

    /**
     * generate the key needed in cipher.init calls of the JCA
     * The key will be generated by using a password, a salt and an iteration count
     *
     * @param cryptoSettings
     * @return generated Key
     * @throws Exception
     */
    private Key generateKey(CryptoSettings cryptoSettings) throws Exception {
        // gather Settings
        byte[] passwordBytes = Base64.decode(cryptoSettings.getStringOption("password").getBytes("UTF-8"));
        char[] password = new String(passwordBytes).toCharArray();
        byte[] saltBytes = Base64.decode(cryptoSettings.getStringOption("salt").getBytes("UTF-8"));
        Integer iterationCount = Integer.parseInt(cryptoSettings.getStringOption("iterationCount"));

        PBEKeySpec pbeSpec = new PBEKeySpec(password, saltBytes, iterationCount);
        SecretKeyFactory keyFact = SecretKeyFactory.getInstance("PBEWithMD5AndDES", "BC");

        return keyFact.generateSecret(pbeSpec);
    }
}

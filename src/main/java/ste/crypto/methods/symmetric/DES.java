package ste.crypto.methods.symmetric;

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
import java.util.Arrays;
import java.util.HashMap;

/**
 * DES implemtation
 *
 * @author Mark Labenski
 */
public class DES extends AbstractSymmetricCryptoMethod {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("key", new MethodOptionDescription("String"));
                put("padding", new MethodOptionDescription("String", Arrays.asList("NoPadding", "ZeroBytePadding", "PKCS7Padding")));
                put("blockMode", new MethodOptionDescription("String", Arrays.asList("ECB", "CBC", "OFB", "CTS")));
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

    // randomly picket IV for some block modes
    private static final byte[] ivBytes = new byte[] {
            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

    /**
     * DES decryption of a given cipherText using the "key" option from Settings
     *
     * @param cryptoSettings
     * @param cipherText
     * @return
     * @throws Exception
     */
    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(getCipherSuiteFromCryptoSettings(cryptoSettings), "BC");

        // get key from options
        byte[] key = Base64.decode(cryptoSettings.getStringOption("key").getBytes("UTF-8"));
        SecretKeySpec spec = new SecretKeySpec(key, "DES");

        // some block modes need the IV
        if(cryptoSettings.getStringOption("blockMode").equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, spec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, spec, new IvParameterSpec(ivBytes));
        }

        byte[] base64DecodedCipherText = Base64.decode(cipherText.getBytes("UTF-8"));
        byte[] plainText = cipher.doFinal(base64DecodedCipherText);

        return new TransferableCryptoDetails("plainText", new String(plainText, "US-ASCII"), cryptoSettings);
    }

    /**
     * DES encryption of a given plainText
     * The cryptographic key needed for encryption will be generated and placed back into the settings object
     * for consumers who need later decryption
     *
     * @param cryptoSettings
     * @param plainText
     * @return Settings where the "key" option is added and the cipher text as payload
     * @throws Exception
     */
    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(getCipherSuiteFromCryptoSettings(cryptoSettings), "BC");

        // generate a DES key with 64 bytes
        KeyGenerator keyGen = KeyGenerator.getInstance("DES", "BC");
        keyGen.init(64);
        Key encryptionKey = keyGen.generateKey();

        // add the key options to settings
        String base64Key = new String(Base64.encode(encryptionKey.getEncoded()));
        cryptoSettings.addOption("key", base64Key);

        if(cryptoSettings.getStringOption("blockMode").equals("ECB")) {
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, new IvParameterSpec(ivBytes));
        }
        String cipherText = new String(Base64.encode(cipher.doFinal(plainText.getBytes("UTF-8"))), "US-ASCII");

        return new TransferableCryptoDetails("cipherText", cipherText, cryptoSettings);
    }

    /**
     * concat the Instance string needed for JCA according to the given Settings
     *
     * @param settings
     * @return
     */
    private String getCipherSuiteFromCryptoSettings(CryptoSettings settings) {
        return "DES" + "/" + settings.getStringOption("blockMode") + "/" + settings.getStringOption("padding");
    }
}

package ste.crypto.methods.symmetric;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
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
 * AES implementation with supported keylengths of 128, 192 and 256
 *
 * @author Mark Labenski
 */
public class AES extends AbstractSymmetricCryptoMethod {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("key", new MethodOptionDescription("String"));
                put("keyLength", new MethodOptionDescription("Int", Arrays.asList("128", "192", "256")));
                put("padding", new MethodOptionDescription("String", Arrays.asList("NoPadding", "ZeroBytePadding", "PKCS7Padding")));
                put("blockMode", new MethodOptionDescription("String", Arrays.asList("ECB", "CBC", "OFB", "CTS", "GCM")));
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

    // randomly picked IV for block modes where the IV is needed
    private static final byte[] ivBytes = new byte[] {
            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

    /**
     * AES decryption of a given cipherText using the "key" option from Settings
     *
     * @param cryptoSettings
     * @param cipherText
     * @return Settings and the plain text as payload
     * @throws Exception
     */
    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance(getCipherSuiteFromCryptoSettings(cryptoSettings), "BC");

        // get key for encryption
        byte[] key = Base64.decode(cryptoSettings.getStringOption("key").getBytes("UTF-8"));
        SecretKeySpec spec = new SecretKeySpec(key, "AES");

        // some block modes need the IV
        if(cryptoSettings.getStringOption("blockMode").equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, spec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, spec, new IvParameterSpec(ivBytes));
        }

        byte[] base64DecodedCipherText = Base64.decode(cipherText.getBytes());
        byte[] plainText = cipher.doFinal(base64DecodedCipherText);

        return new TransferableCryptoDetails("plainText", new String(plainText, "US-ASCII"), cryptoSettings);
    }

    /**
     * AES encryption of a given plainText
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

        // generate AES key with given key length
        KeyGenerator keyGen = KeyGenerator.getInstance("AES", "BC");
        keyGen.init(Integer.parseInt(cryptoSettings.getStringOption("keyLength")));
        Key encryptionKey = keyGen.generateKey();

        String base64Key = new String(Base64.encode(encryptionKey.getEncoded()));
        cryptoSettings.addOption("key", base64Key);

        // some block modes need an IV
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
     * @return suitable instance String for JCA
     */
    private String getCipherSuiteFromCryptoSettings(CryptoSettings settings) {
        String blockMode = settings.getStringOption("blockMode");
        String padding = settings.getStringOption("padding");
        if(settings.getStringOption("blockMode").equals("GCM")) {
            padding = "NoPadding";
        }
        return "AES" + "/" + blockMode + "/" + padding;
    }
}

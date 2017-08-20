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
 * Created by marklabenski on 04.07.17.
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

    private static final byte[] ivBytes = new byte[] {
            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String cipherText) throws Exception {

        Cipher cipher = Cipher.getInstance(getCipherSuiteFromCryptoSettings(cryptoSettings), "BC");
        byte[] key = Base64.decode(cryptoSettings.getStringOption("key").getBytes("UTF-8"));
        SecretKeySpec spec = new SecretKeySpec(key, "AES");

        if(cryptoSettings.getStringOption("blockMode").equals("ECB")) {
            cipher.init(Cipher.DECRYPT_MODE, spec);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, spec, new IvParameterSpec(ivBytes));
        }

        System.out.println(cipherText);
        byte[] base64DecodedCipherText = Base64.decode(cipherText.getBytes());
        byte[] text = cipher.doFinal(base64DecodedCipherText);

        return new TransferableCryptoDetails("plainText", new String(text, "US-ASCII"), cryptoSettings);
    }

    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {

        Cipher cipher = Cipher.getInstance(getCipherSuiteFromCryptoSettings(cryptoSettings), "BC");

        KeyGenerator keygen = KeyGenerator.getInstance("AES", "BC");
        keygen.init(Integer.parseInt(cryptoSettings.getStringOption("keyLength")));

        Key encryptionKey = keygen.generateKey();

        String base64Key = new String(Base64.encode(encryptionKey.getEncoded()));
        cryptoSettings.addOption("key", base64Key);

        if(cryptoSettings.getStringOption("blockMode").equals("ECB")) {
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, new IvParameterSpec(ivBytes));
        }
        String base64Secret = new String(Base64.encode(cipher.doFinal(plainText.getBytes("UTF-8"))), "US-ASCII");


        return new TransferableCryptoDetails("cipherText", base64Secret, cryptoSettings);
    }

    private String getCipherSuiteFromCryptoSettings(CryptoSettings settings) {
        return "AES" + "/" + settings.getStringOption("blockMode") + "/" + settings.getStringOption("padding");
    }
}

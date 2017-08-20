package ste.crypto.methods.asymmetric;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Implementation of the RSA algorithm.
 * Used for encrypting and decrypting
 *
 * @author Mark Labenski
 */
public class RSA extends AbstractAsymmetricCryptoMethod {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("keyLength", new MethodOptionDescription("Int", Arrays.asList("1024", "4096")));
                put("publicKey", new MethodOptionDescription("String"));
                put("privateKey", new MethodOptionDescription("String"));
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
     * decrypt a given cipherText by using the "privateKey" option from Settings
     *
     * @param cryptoSettings
     * @param cipherText
     * @return Settings and the plain text as payload
     * @throws Exception
     */
    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding", "BC");

        byte[] privKeyBytes = Base64.decode(cryptoSettings.getStringOption("privateKey").getBytes("UTF-8"));

        // use key spec in order to get Key object nedded for cipher.init
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateKey = keyFactory.generatePrivate(keySpec);

        cipher.init(Cipher.DECRYPT_MODE, privateKey, RSAKeyPair.generateSecureRandom());

        byte[] base64DecodedCipherText = Base64.decode(cipherText.getBytes());
        byte[] plainText = cipher.doFinal(base64DecodedCipherText);

        return new TransferableCryptoDetails("plainText", new String(plainText, "US-ASCII"), cryptoSettings);
    }

    /**
     * encrypt a given plainText by using the "publicKey" option
     *
     * @param cryptoSettings
     * @param plainText
     * @return Settings and the cipher text as payload
     * @throws Exception
     */
    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding", "BC");

        byte[] pubKeyBytes = Base64.decode(cryptoSettings.getStringOption("publicKey").getBytes("UTF-8"));

        // use key spec in order to get Key object needed for cipher.init
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key pubKey = keyFactory.generatePublic(keySpec);

        cipher.init(Cipher.ENCRYPT_MODE, pubKey, RSAKeyPair.generateSecureRandom());

        byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));

        String base64Secret = new String(Base64.encode(cipherText), "US-ASCII");

        return new TransferableCryptoDetails("cipherText", base64Secret, cryptoSettings);
    }
}

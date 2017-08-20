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
 * Created by marklabenski on 20.08.17.
 */
public class RSA extends AbstractAsymmetricCryptoMethod {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("keyLength", new MethodOptionDescription("Int", Arrays.asList("1024", "4096")));
                put("padding", new MethodOptionDescription("String", Arrays.asList("NoPadding", "PKCS7Padding")));
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

    private static final byte[] ivBytes = new byte[] {
            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00 };

    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String cipherText) throws Exception {

        Cipher cipher = Cipher.getInstance(getCipherSuiteFromCryptoSettings(cryptoSettings), "BC");

        byte[] privKeyBytes = Base64.decode(cryptoSettings.getStringOption("privateKey").getBytes("UTF-8"));

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privKey = keyFactory.generatePrivate(keySpec);

        cipher.init(Cipher.DECRYPT_MODE, privKey, RSAKeyPair.generateSecureRandom());

        byte[] base64DecodedCipherText = Base64.decode(cipherText.getBytes());
        byte[] text = cipher.doFinal(base64DecodedCipherText);

        return new TransferableCryptoDetails("plainText", new String(text, "US-ASCII"), cryptoSettings);
    }

    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {

        Cipher cipher = Cipher.getInstance(getCipherSuiteFromCryptoSettings(cryptoSettings), "BC");

        byte[] pubKeyBytes = Base64.decode(cryptoSettings.getStringOption("publicKey").getBytes("UTF-8"));

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key pubKey = keyFactory.generatePublic(keySpec);

        cipher.init(Cipher.ENCRYPT_MODE, pubKey, RSAKeyPair.generateSecureRandom());

        String base64Secret = new String(Base64.encode(cipher.doFinal(plainText.getBytes("UTF-8"))), "US-ASCII");

        return new TransferableCryptoDetails("cipherText", base64Secret, cryptoSettings);
    }

    private String getCipherSuiteFromCryptoSettings(CryptoSettings settings) {
        return "RSA" + "/ECB/" + settings.getStringOption("padding");
    }
}

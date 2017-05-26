package ste.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONObject;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;


/**
 * Created by marklabenski on 16.05.17.
 */
public class CryptoBackend {
    private static CryptoBackend ourInstance = new CryptoBackend();

    public static CryptoBackend getInstance() {
        return ourInstance;
    }

    private CryptoBackend() {
        //Security.addProvider(new BouncyCastleProvider());
    }

    public String simpleEncrypt(FileEncryptionSettingsTransferObject encryptionSettings, String fileContent) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(encryptionSettings.cipherSuite, "BC");
        byte[] key = encryptionSettings.key.getBytes("UTF-8");
        SecretKeySpec spec = new SecretKeySpec(key, "DES");
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        return new String(Base64.encode(cipher.doFinal(fileContent.getBytes("UTF-8"))),  "US-ASCII");
    }

    public String simpleDecrypt(FileEncryptionSettingsTransferObject encryptionSettings, String cypherText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, ShortBufferException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(encryptionSettings.cipherSuite, "BC");
        byte[] key = encryptionSettings.key.getBytes("UTF-8");
        SecretKeySpec spec = new SecretKeySpec(key, "DES");
        cipher.init(Cipher.DECRYPT_MODE, spec);
        System.out.println(cypherText);
        byte[] base64DecodedCipherText = Base64.decode(cypherText.getBytes());
        byte[] text = cipher.doFinal(base64DecodedCipherText);

        return new String(text);
    }
}
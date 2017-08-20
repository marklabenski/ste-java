package ste.crypto;

import org.bouncycastle.util.encoders.Base64;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.*;


/**
 * Created by marklabenski on 16.05.17.
 */
public class CryptoBackend {
    private static CryptoBackend ourInstance = new CryptoBackend();

    public static CryptoBackend getInstance() {
        return ourInstance;
    }

    private CryptoBackend() {
        // can't add provider at runtime. Thanks maven...
        // Security.addProvider(new BouncyCastleProvider());
    }

 /*   public EncryptedFileTransferObject simpleEncrypt(FileEncryptionSettingsTransferObject encryptionSettings, String fileContent) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

    }*/

    public String simpleDecrypt(FileEncryptionSettingsTransferObject encryptionSettings, String cypherText) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, ShortBufferException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance(encryptionSettings.cipherSuite, "BC");
        byte[] key = Base64.decode(encryptionSettings.key.getBytes("UTF-8"));
        SecretKeySpec spec = new SecretKeySpec(key, encryptionSettings.keySuite);
        cipher.init(Cipher.DECRYPT_MODE, spec);
        System.out.println(cypherText);
        byte[] base64DecodedCipherText = Base64.decode(cypherText.getBytes());
        byte[] text = cipher.doFinal(base64DecodedCipherText);

        return new String(text);
    }

    /*public EncryptedFileTransferObject pbeEncrypt(EncryptedFileTransferObject encryptionSettings, String fileContent) throws Exception {
        byte[]          keyBytes = new byte[] {
                0x73, 0x2f, 0x2d, 0x33, (byte)0xc8, 0x01, 0x73,
                0x2b, 0x72, 0x06, 0x75, 0x6c, (byte)0xbd, 0x44,
                (byte)0xf9, (byte)0xc1 };
        byte[]		    ivBytes = new byte[] {
                (byte)0xb0, 0x7b, (byte)0xf5, 0x22, (byte)0xc8,
                (byte)0xd6, 0x08, (byte)0xb8 };

        byte[]              in = "hallo".getBytes(Charset.forName("UTF-8"));

        char[]              password = "password".toCharArray();
        byte[]              salt = new byte[] { 0x7d, 0x60, 0x43, 0x5f, 0x02, (byte)0xe9, (byte)0xe0, (byte)0xae };
        int                 iterationCount = 2048;
        PBEKeySpec pbeSpec = new PBEKeySpec(password, salt, iterationCount);
        SecretKeyFactory    keyFact = SecretKeyFactory.getInstance("PBEWithSHA1And128BitAES-CBC-BC", "BC");

        Cipher cDec = Cipher.getInstance("PBEWithSHA1And128BitAES-CBC-BC", "BC");
        Key    sKey = keyFact.generateSecret(pbeSpec);
        PBEWithoutParamsExample.key = sKey;

        cDec.init(Cipher.ENCRYPT_MODE, sKey);

        PBEWithoutParamsExample.encrypted = cDec.doFinal(in);

        PBEWithoutParamsExample.iv = cDec.getIV();
    }

    public String pbeDecrypt(EncryptedFileTransferObject encryptionSettings, String cyptherText) throws Exception {
        Cipher cDec = Cipher.getInstance("PBEWithSHA1And128BitAES-CBC-BC", "BC");

        cDec.init(Cipher.DECRYPT_MODE, PBEWithoutParamsExample.key);

        byte[] decrypted = cDec.doFinal(encrypted);

        System.out.println("gen iv : " + PBEWithoutParamsExample.toHex(PBEWithoutParamsExample.iv));
        System.out.println("plain  : " + new String(decrypted));
    }*/
}
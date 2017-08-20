package chapter2;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;


/**
 * Basic symmetric encryption example
 */
public class SimpleSymmetricExample
{
    public static void main(
            String[]    args)
            throws Exception
    {
        //Security.addProvider(new BouncyCastleProvider());
        byte[]        input = new byte[] {
                0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77,
                (byte)0x88, (byte)0x99, (byte)0xaa, (byte)0xbb,
                (byte)0xcc, (byte)0xdd, (byte)0xee, (byte)0xff };
        byte[] weakKey1 = new byte[] {
            (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x01 };
        byte[] weakKey2 = new byte[] {
            (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE, (byte)0xFE };
        byte[] weakKey3 = new byte[] {
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
        byte[] weakKey4 = new byte[] {
            (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF };

        SecretKeySpec key1 = new SecretKeySpec(weakKey1, "DES");
        SecretKeySpec key2 = new SecretKeySpec(weakKey2, "DES");
        SecretKeySpec key3 = new SecretKeySpec(weakKey3, "DES");
        SecretKeySpec key4 = new SecretKeySpec(weakKey4, "DES");

        Cipher        cipher = Cipher.getInstance("DES/ECB/NoPadding", "BC");

        System.out.println("weakKey1: " + Utils.toHex(weakKey1));
        System.out.println("weakKey2: " + Utils.toHex(weakKey2));
        System.out.println("weakKey3: " + Utils.toHex(weakKey3));
        System.out.println("weakKey4: " + Utils.toHex(weakKey4));
        System.out.println("input text : " + Utils.toHex(input));

        // encryption pass

        byte[] cipherText1 = new byte[input.length];

        cipher.init(Cipher.ENCRYPT_MODE, key1);

        int ctLength = cipher.update(input, 0, input.length, cipherText1, 0);

        ctLength += cipher.doFinal(cipherText1, ctLength);

        System.out.println("cipher text weakKey1: " + Utils.toHex(cipherText1) + " bytes: " + ctLength);

        byte[] cipherText2 = new byte[input.length];

        cipher.init(Cipher.ENCRYPT_MODE, key2);

        ctLength = cipher.update(input, 0, input.length, cipherText2, 0);

        ctLength += cipher.doFinal(cipherText2, ctLength);

        System.out.println("cipher text weakKey2: " + Utils.toHex(cipherText2) + " bytes: " + ctLength);

        byte[] cipherText3 = new byte[input.length];

        cipher.init(Cipher.ENCRYPT_MODE, key3);

        ctLength = cipher.update(input, 0, input.length, cipherText3, 0);

        ctLength += cipher.doFinal(cipherText3, ctLength);

        System.out.println("cipher text weakKey3: " + Utils.toHex(cipherText3) + " bytes: " + ctLength);

        byte[] cipherText4 = new byte[input.length];

        cipher.init(Cipher.ENCRYPT_MODE, key4);

        ctLength = cipher.update(input, 0, input.length, cipherText4, 0);

        ctLength += cipher.doFinal(cipherText4, ctLength);

        System.out.println("cipher text weakKey4: " + Utils.toHex(cipherText4) + " bytes: " + ctLength);

        // decryption pass

        byte[] plainText = new byte[ctLength];

        cipher.init(Cipher.DECRYPT_MODE, key3);

        int ptLength = cipher.update(cipherText1, 0, ctLength, plainText, 0);

        ptLength += cipher.doFinal(plainText, ptLength);

        System.out.println("plain text 1 with key3: " + Utils.toHex(plainText) + " bytes: " + ptLength);

        plainText = new byte[ctLength];

        cipher.init(Cipher.DECRYPT_MODE, key4);

        ptLength = cipher.update(cipherText2, 0, ctLength, plainText, 0);

        ptLength += cipher.doFinal(plainText, ptLength);

        System.out.println("plain text 2 with key4: " + Utils.toHex(plainText) + " bytes: " + ptLength);
    }
}

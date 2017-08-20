package ste;

import org.json.JSONObject;
import ste.crypto.AvailableCryptoMethods;
import ste.crypto.methods.asymmetric.RSA;
import ste.crypto.methods.asymmetric.RSAKeyPair;
import ste.crypto.methods.hash.MD5;
import ste.crypto.methods.hash.SHA2;
import ste.crypto.methods.symmetric.AbstractPBCryptoMethod;
import ste.crypto.methods.symmetric.SHAAndRC4;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;
import ste.ipc.SocketIPCClient;

/**
 * Created by marklabenski on 09.05.17.
 */
public class SecureTextEditor {
    public static void main(String[] args) throws Exception
    {
        //CryptoBackend cryptoBackend = CryptoBackend.getInstance();
        SocketIPCClient socketListener = SocketIPCClient.getInstance();

        System.out.println("ste java started");

        AvailableCryptoMethods methods = AvailableCryptoMethods.getInstance();

        //methods.executeAlgorithmMethod("DES", "encrypt", new JSONObject());

        JSONObject test = methods.getAllAvailableCryptoMethodsJSON();

        System.out.println(test);

        System.out.println(test.getJSONArray("cryptoMethods").getJSONObject(0).getString("name"));


        CryptoSettings test2 = new CryptoSettings();

        test2.addOption("password", "hallo");
        test2.addOption("salt", "12345678");
        test2.addOption("iterationCount", "2048");



        AbstractPBCryptoMethod destest = new SHAAndRC4();
        TransferableCryptoDetails encrypted = destest.encrypt(test2, "hallo");
        String cipherText = encrypted.payload;

        CryptoSettings test4 = new CryptoSettings();

        test4.addOption("password", "hallo");
        test4.addOption("iterationCount", "2048");
        test4.addOption("salt", "12345678");
        TransferableCryptoDetails decrypted = destest.decrypt(test4, cipherText);
        String plainText = decrypted.payload;

        CryptoSettings test5 = new CryptoSettings();

        test5.addOption("keyLength", "1024");
        RSAKeyPair keypair = new RSAKeyPair();
        TransferableCryptoDetails keyPairStuff = keypair.generateKeyPair(test5);

        String[] keyParts = keyPairStuff.payload.split("\\|");
        System.out.println("----RSA:");
        System.out.println(keyPairStuff.payload);
        System.out.println(keyParts[0]);
        System.out.println(keyParts[1]);

        test5.addOption("publicKey", keyParts[0]);
        test5.addOption("privateKey", keyParts[1]);
        test5.addOption("padding", "NoPadding");



        RSA rsa = new RSA();
        String encryptedRSA = rsa.encrypt(test5, "hallo").payload;

        System.out.println(encryptedRSA);

        String decryptedRSA = rsa.decrypt(test5, encryptedRSA).payload;
        System.out.println(decryptedRSA);
        System.out.println("--------------");

        CryptoSettings hashSettings1 = new CryptoSettings();

        hashSettings1.addOption("iterationCount", "1");
        MD5 md5 = new MD5();
        String hashValue1 = md5.hash(hashSettings1, "hallo").payload;
        System.out.println(hashValue1);

        CryptoSettings hashSettings2 = new CryptoSettings();

        hashSettings2.addOption("iterationCount", "5");
        String hashValue2 = md5.hash(hashSettings2, "hallo").payload;
        System.out.println(hashValue2);

        CryptoSettings hashSettings3 = new CryptoSettings();

        hashSettings3.addOption("iterationCount", "5");
        hashSettings3.addOption("hashLength", "512");
        SHA2 sha2 = new SHA2();
        String hashValue3 = sha2.hash(hashSettings3, "hallo").payload;
        System.out.println(hashValue3);

        System.out.println(cipherText);
        System.out.println(plainText);



        while(!socketListener.isDisconnected) {


        }
    }
}

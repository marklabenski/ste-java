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
        SocketIPCClient socketListener = SocketIPCClient.getInstance();

        System.out.println("ste java started");

        while(!socketListener.isDisconnected) {
            // keep on listening
        }
    }
}

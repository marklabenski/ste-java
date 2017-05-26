package ste;

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

        while(!socketListener.isDisconnected) {

        }
    }
}

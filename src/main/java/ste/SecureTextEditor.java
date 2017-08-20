package ste;

import ste.ipc.SocketIPCClient;

/**
 * API with cryptographic algorithms that connects to a socket server via Socket.IO
 * and provides methods on these algorithms for consumers
 *
 * @author Mark Labenski
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

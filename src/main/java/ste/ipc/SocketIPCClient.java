package ste.ipc;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import ste.crypto.AvailableCryptoMethods;
import ste.crypto.CryptoBackend;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by marklabenski on 23.05.17.
 */
public class SocketIPCClient {
    private final String SOCKET_HOSTNAME = "http://localhost";

    private final String SOCKET_PORT = "41414";

    private final int SOCKET_TIMEOUT_MS = 3000;

    private Socket socket = null;

    private ArrayList<AbstractListener> listenerList = null;

    public boolean isDisconnected = false;

    private static SocketIPCClient ourInstance = new SocketIPCClient();

    public static SocketIPCClient getInstance() {
        return ourInstance;
    }

    private SocketIPCClient() {
        listenerList = new ArrayList<AbstractListener>();
        this.attachAllListeners();

        final SocketIPCClient _this = this;

        IO.Options opts = new IO.Options();
        //opts.reconnection = false;
        opts.timeout = this.SOCKET_TIMEOUT_MS;

        createSocketConnection(_this, opts);
    }

    private void createSocketConnection(final SocketIPCClient _this, IO.Options opts) {
        try {
            socket = IO.socket(this.SOCKET_HOSTNAME + ":" + this.SOCKET_PORT, opts);
        } catch(Exception e) {
            this.isDisconnected = true;
            System.out.println(e.getMessage());
            System.exit(22);
        }
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            public void call(Object... args) {
                System.out.println("socket connected");
            }

        }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            public void call(Object... args) {
                _this.isDisconnected = true;
            }
        });

        for(AbstractListener listener: listenerList) {
            this.attachListenerToSocket(listener);
        }

        socket.connect();
    }

    private void attachAllListeners() {


        listenerList.add(new AlgorithmListListener(this));
        listenerList.add(new AlgorithmListener(this));
    }

    private void attachListenerToSocket(AbstractListener listener) {

        socket.on(listener.eventName, listener);
    }

    public void emitEvent(String eventName, JSONObject obj) {
        socket.emit(eventName, obj);
    }
}

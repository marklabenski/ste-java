package ste.ipc;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * socket.IO client for ipc
 *
 * @author Mark Labenski
 */
public class SocketIPCClient {
    /**
     * hostname of the socket server
     */
    private static final String SOCKET_HOSTNAME = "http://localhost";

    /**
     * port of the socket server
     */
    private static final String SOCKET_PORT = "41414";

    /**
     * socket timeout
     */
    private static final int SOCKET_TIMEOUT_MS = 3000;

    /**
     * socket connection
     */
    private Socket socket = null;

    /**
     * list of event listeners
     */
    private ArrayList<AbstractListener> listenerList = null;

    /**
     * property to check from outside if the socket connection is disconnected
     */
    public boolean isDisconnected = false;

    /**
     * singleton instance
     */
    private static SocketIPCClient ourInstance = new SocketIPCClient();

    /**
     * singleton instance getter
     *
     * @return
     */
    public static SocketIPCClient getInstance() {
        return ourInstance;
    }

    private SocketIPCClient() {
        listenerList = new ArrayList<>();
        this.attachAllListeners();

        final SocketIPCClient _this = this;

        IO.Options opts = new IO.Options();

        opts.timeout = SOCKET_TIMEOUT_MS;

        createSocketConnection(_this, opts);
    }

    /**
     * creates and connects the socket connection to the server
     *
     * @param _this
     * @param opts
     */
    private void createSocketConnection(final SocketIPCClient _this, IO.Options opts) {
        try {
            socket = IO.socket(SOCKET_HOSTNAME + ":" + SOCKET_PORT, opts);
        } catch(Exception e) {
            this.isDisconnected = true;
            System.out.println(e.getMessage());
            System.exit(22);
        }
        socket
                .on(Socket.EVENT_CONNECT, args -> System.out.println("socket connected"))
                .on(Socket.EVENT_DISCONNECT, args -> _this.isDisconnected = true);

        for(AbstractListener listener: listenerList) {
            this.attachListenerToSocket(listener);
        }

        socket.connect();
    }

    /**
     * attaches all listeners to the list in order to retrieve events
     */
    private void attachAllListeners() {
        listenerList.add(new AlgorithmListListener(this));
        listenerList.add(new AlgorithmListener(this));
    }

    /**
     * attach the defined listeners to socket connection
     *
     * @param listener
     */
    private void attachListenerToSocket(AbstractListener listener) {
        socket.on(listener.eventName, listener);
    }

    /**
     * emit an event to the server
     *
     * @param eventName name of the event
     * @param obj JSONObject parameter
     */
    public void emitEvent(String eventName, JSONObject obj) {
        socket.emit(eventName, obj);
    }
}

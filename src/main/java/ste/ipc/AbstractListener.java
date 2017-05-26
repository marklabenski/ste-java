package ste.ipc;

import io.socket.emitter.Emitter;


/**
 * Created by marklabenski on 24.05.17.
 */
public abstract class AbstractListener implements Emitter.Listener {
    String eventName;

    SocketIPCClient socketClientInstance;

    abstract public void call(Object... objects);

    public AbstractListener(SocketIPCClient _socketClientInstance) {
        this.socketClientInstance = _socketClientInstance;
    }
}


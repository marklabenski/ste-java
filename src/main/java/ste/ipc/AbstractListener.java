package ste.ipc;

import io.socket.emitter.Emitter;


/**
 * abstact Socket.IO listener
 * listens for one event and has the socket available to emit events as reaction
 *
 * @author Mark Labenski
 */
public abstract class AbstractListener implements Emitter.Listener {
    /**
     * name of the event the listener listens to
     */
    String eventName;

    /**
     * the instance of the socket client to emit events
     */
    SocketIPCClient socketClientInstance;

    /**
     * caller that gets called on event emission
     *
     * @param objects objects in JSON format
     */
    abstract public void call(Object... objects);

    /**
     * constructor with injecting the instance of the socket client
     *
     * @param _socketClientInstance
     */
    public AbstractListener(SocketIPCClient _socketClientInstance) {
        this.socketClientInstance = _socketClientInstance;
    }
}


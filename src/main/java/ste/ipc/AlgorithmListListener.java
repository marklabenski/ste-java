package ste.ipc;

import ste.crypto.AvailableCryptoMethods;
import java.util.Arrays;

/**
 * listen for consumer who needs the list of available algorithms
 *
 * @author Mark Labenski
 */
public class AlgorithmListListener extends AbstractListener {
    /**
     * name of the listen event
     */
    private final String EVENT_NAME = "info.list-algorithms";

    /**
     * name of the reaction event
     */
    private final String EMIT_EVENT_NAME = "info.list-algorithms";

    /**
     * constructor with getting client instance and defines event name
     *
     * @param _socketClientInstance
     */
    AlgorithmListListener(SocketIPCClient _socketClientInstance) {
        super(_socketClientInstance);

        this.eventName = this.EVENT_NAME;
    }

    /**
     * return the list of available algorithms on listen event emit
     *
     * @param objects objects in JSON format
     */
    public void call(Object... objects) {
        try {
            AvailableCryptoMethods algorithms = new AvailableCryptoMethods();
            this.socketClientInstance.emitEvent(this.EMIT_EVENT_NAME, algorithms.getAllAvailableCryptoMethodsJSON());
        } catch (Exception e) {
            System.err.println("1");
            System.err.println(e);
            System.err.println("\n2");
            System.err.println(e.getMessage());
            System.err.println("\n3");
            System.err.println(e.getLocalizedMessage());
            System.err.println("\n4");
            System.err.println(e.getCause());
            System.err.println("\n5");
            System.err.println(Arrays.toString(e.getStackTrace()));
            System.err.println("\n6");
            e.printStackTrace();
        }
    }
}

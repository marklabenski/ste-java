package ste.ipc;

import org.json.JSONObject;
import ste.crypto.AvailableCryptoMethods;
import ste.crypto.CryptoBackend;
import ste.crypto.FileEncryptionSettingsTransferObject;

import java.security.InvalidKeyException;
import java.util.Arrays;

/**
 * Created by marklabenski on 24.05.17.
 */
public class AlgorithmListListener extends AbstractListener {
    private final String EVENT_NAME = "list-algorithms";

    private final String EMIT_EVENT_NAME = "info.list-algorithms";

    public AlgorithmListListener(SocketIPCClient _socketClientInstance) {
        super(_socketClientInstance);

        this.eventName = this.EVENT_NAME;
    }

    public void call(Object... objects) {
        JSONObject algorithmListObj = null;
        try {
            AvailableCryptoMethods algos = new AvailableCryptoMethods();
            System.out.println("oh my god! list the algorithms");
            this.socketClientInstance.emitEvent(this.EMIT_EVENT_NAME, algos.getAllAvailableCryptoMethodsJSON());
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

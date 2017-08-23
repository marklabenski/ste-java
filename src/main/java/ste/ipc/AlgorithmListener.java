package ste.ipc;

import org.json.JSONObject;
import ste.crypto.AvailableCryptoMethods;
import java.util.Arrays;

/**
 * listen for execution of an available algorithm
 *
 * @author Mark Labenski
 */
public class AlgorithmListener extends AbstractListener {
    /**
     * name of the listen event
     */
    private final String EVENT_NAME = "crypt.exec-algorithm";

    /**
     * name of the reaction event
     */
    private final String EMIT_EVENT_NAME = "crypt.exec-algorithm";

    /**
     * constructor with getting client instance and defines event name
     *
     * @param _socketClientInstance
     */
    AlgorithmListener(SocketIPCClient _socketClientInstance) {
        super(_socketClientInstance);

        this.eventName = this.EVENT_NAME;
    }

    /**
     * called on event emit
     *
     * @param objects objects in JSON format
     */
    public void call(Object... objects) {
        try {
            // first parameter is JSONObject of algorithm information and settings
            JSONObject execAlgoObj = (JSONObject) objects[0];

            System.out.println("event emitted");
            System.out.println(execAlgoObj);

            execAlgoObj.getString("algorithm");
            execAlgoObj.getJSONObject("options");

            JSONObject algorithmResult = null;

            // call algorithm method
            if(!execAlgoObj.has("parameters")) {
                algorithmResult = AvailableCryptoMethods.getInstance().executeAlgorithmMethod(
                        execAlgoObj.getString("algorithm"),
                        execAlgoObj.getString("method"),
                        null,
                        execAlgoObj.getJSONObject("options"));
            } else {
                algorithmResult = AvailableCryptoMethods.getInstance().executeAlgorithmMethod(
                        execAlgoObj.getString("algorithm"),
                        execAlgoObj.getString("method"),
                        execAlgoObj.getJSONArray("parameters"),
                        execAlgoObj.getJSONObject("options"));
            }

            // emit reaction event and pass result
            this.socketClientInstance.emitEvent(this.EMIT_EVENT_NAME, algorithmResult);
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

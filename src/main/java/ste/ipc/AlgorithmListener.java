package ste.ipc;

import org.json.JSONObject;
import ste.crypto.AvailableCryptoMethods;

import java.util.Arrays;

/**
 * Created by marklabenski on 24.05.17.
 */
public class AlgorithmListener extends AbstractListener {
    private final String EVENT_NAME = "crypt.exec-algorithm";

    private final String EMIT_EVENT_NAME = "crypt.exec-algorithm";

    public AlgorithmListener(SocketIPCClient _socketClientInstance) {
        super(_socketClientInstance);

        this.eventName = this.EVENT_NAME;
    }

    public void call(Object... objects) {
        try {
            JSONObject execAlgoObj = (JSONObject) objects[0];

            execAlgoObj.getString("algorithm");
            execAlgoObj.getJSONObject("options");
            System.out.println( execAlgoObj.getString("algorithm"));
            System.out.println( execAlgoObj.getJSONObject("options"));

            JSONObject algorithmResult = AvailableCryptoMethods.getInstance().executeAlgorithmMethod(
                    execAlgoObj.getString("algorithm"),
                    execAlgoObj.getString("method"),
                    execAlgoObj.getJSONArray("parameters"),
                    execAlgoObj.getJSONObject("options"));


            this.socketClientInstance.emitEvent(this.EMIT_EVENT_NAME, algorithmResult);
            /*FileEncryptionSettingsTransferObject encryptionSettings = new FileEncryptionSettingsTransferObject(fileToEncryptObj);

            String fileContent = fileToEncryptObj.getString("fileContent");

            CryptoBackend crypt = CryptoBackend.getInstance();
            EncryptedFileTransferObject encryptedFile = crypt.simpleEncrypt(encryptionSettings, fileContent);

            fileToEncryptObj.remove("fileContent");
            fileToEncryptObj.put("secret", encryptedFile.base64EncryptedFileContent);
            fileToEncryptObj.put("encryptionSettings", encryptedFile.fileEncryptionSettings.encryptionSettings);*/

            //this.socketClientInstance.emitEvent(this.EMIT_EVENT_NAME, fileToEncryptObj);
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

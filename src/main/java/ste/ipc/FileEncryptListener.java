package ste.ipc;

import org.json.JSONObject;
import ste.crypto.CryptoBackend;
import ste.crypto.FileEncryptionSettingsTransferObject;

import java.util.Arrays;

/**
 * Created by marklabenski on 24.05.17.
 */
public class FileEncryptListener extends AbstractListener {
    private final String EVENT_NAME = "file.encrypt";

    private final String FILE_ENCRYPTED_EVENT = "file.encrypted";

    public FileEncryptListener (SocketIPCClient _socketClientInstance) {
        super(_socketClientInstance);

        this.eventName = this.EVENT_NAME;
    }

    public void call(Object... objects) {
        try {
            JSONObject fileToEncryptObj = (JSONObject) objects[0];
            FileEncryptionSettingsTransferObject encryptionSettings = new FileEncryptionSettingsTransferObject(fileToEncryptObj);

            String fileContent = fileToEncryptObj.getString("fileContent");

            CryptoBackend crypt = CryptoBackend.getInstance();
            String secret = crypt.simpleEncrypt(encryptionSettings, fileContent);

            fileToEncryptObj.remove("fileContent");
            fileToEncryptObj.put("secret", secret);

            this.socketClientInstance.emitEvent(this.FILE_ENCRYPTED_EVENT, fileToEncryptObj);
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

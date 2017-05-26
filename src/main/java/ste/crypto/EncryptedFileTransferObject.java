package ste.crypto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marklabenski on 24.05.17.
 */
public class EncryptedFileTransferObject {
    public FileEncryptionSettingsTransferObject fileEncryptionSettings;

    public String base64EncryptedFileContent;

    public EncryptedFileTransferObject(String base64FileContent, FileEncryptionSettingsTransferObject fileEncryptionSettings) {
        this.base64EncryptedFileContent = base64FileContent;
        this.fileEncryptionSettings = fileEncryptionSettings;
    }

    public JSONObject getJSONData() {
        JSONObject returnObject = new JSONObject();
        returnObject.put("encryptionSettings", this.fileEncryptionSettings.getJSONData());
        returnObject.put("secret", this.base64EncryptedFileContent);

        return returnObject;
    }
}

package ste.crypto;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marklabenski on 24.05.17.
 */
public class FileEncryptionSettingsTransferObject {
    public JSONObject encryptionSettings;

    public String fileName;

    public String cipherSuite;

    public String keySuite;

    public String key;

    public FileEncryptionSettingsTransferObject(JSONObject inputJSON) {
        enrichSettingsFromInputJSON(inputJSON);
    }

    private void enrichSettingsFromInputJSON(JSONObject inputJSON) {
        this.encryptionSettings = inputJSON.getJSONObject("encryptionSettings");

        this.cipherSuite = this.encryptionSettings.getString("cipherSuite");
        this.fileName = this.encryptionSettings.getString("fileName");
        this.key = this.encryptionSettings.getString("key");
        this.keySuite = this.encryptionSettings.getString("keySuite");
    }

    public JSONObject getJSONData() {
        return encryptionSettings;
    }
}

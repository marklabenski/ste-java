package ste.crypto.settings;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by marklabenski on 28.06.17.
 */
public class CryptoSettings {
    private HashMap<String, String> options;

    public CryptoSettings() {
        options = new HashMap<>();
    }

    public CryptoSettings(JSONObject _options) {
        options = new HashMap<>();
        _options.keys().forEachRemaining((String keyName) -> {
            options.put(keyName, (String) _options.get(keyName));
        });
    }

    public CryptoSettings(HashMap<String, String> options) {
        this.options = options;
    }

    public HashMap<String, String> addOption(String name, String value) {
        options.put(name, value);
        return options;
    }

    public String getStringOption(String name) {
        return options.get(name);
    }

    public JSONObject serialize() {
        return new JSONObject(options);
    }
}

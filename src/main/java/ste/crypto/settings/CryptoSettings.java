package ste.crypto.settings;

import org.json.JSONObject;
import java.util.HashMap;

/**
 * all settings a cryptographic algorithm needs for its methods
 *
 * @author Mark Labenski
 */
public class CryptoSettings {
    /**
     * Hashmap of all options available
     */
    private HashMap<String, String> options;

    /**
     * constructor with empty options to fill in later
     */
    public CryptoSettings() {
        options = new HashMap<>();
    }

    /**
     * constructor from an JSONObject containing options
     *
     * @param _options the options provided
     */
    public CryptoSettings(JSONObject _options) {
        options = new HashMap<>();
        _options.keys().forEachRemaining((String keyName) -> {
            options.put(keyName, (String) _options.get(keyName));
        });
    }

    /**
     * constructor from an HashMap of options
     *
     * @param options HashMap with options
     */
    public CryptoSettings(HashMap<String, String> options) {
        this.options = options;
    }

    /**
     * add an option to the settings
     *
     * @param name option name as needed for algorithm
     * @param value option value
     * @return returns the options as a Hashmap
     */
    public HashMap<String, String> addOption(String name, String value) {
        options.put(name, value);
        return options;
    }

    /**
     * get an option from the Settings
     *
     * @param name option name
     * @return String provided for option name
     */
    public String getStringOption(String name) {
        return options.get(name);
    }

    /**
     * serialize the Settings as a JSONObject
     *
     * @return JSONObject of all Options (k,v)
     */
    public JSONObject serialize() {
        return new JSONObject(options);
    }
}

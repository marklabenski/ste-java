package ste.crypto;

import org.json.JSONArray;
import org.json.JSONObject;
import ste.crypto.abilities.Decryptable;
import ste.crypto.abilities.Encryptable;
import ste.crypto.abilities.Hashable;
import ste.crypto.abilities.KeyPairGeneratable;
import ste.crypto.methods.*;
import ste.crypto.methods.asymmetric.RSA;
import ste.crypto.methods.asymmetric.RSAKeyPair;
import ste.crypto.methods.hash.MD5;
import ste.crypto.methods.hash.SHA1;
import ste.crypto.methods.hash.SHA2;
import ste.crypto.methods.symmetric.*;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Singelton of all available algorithms
 *
 * needed to get information and call the specific algorithms on purpose
 *
 * @author Mark Labenski
 */
public class AvailableCryptoMethods {
    /**
     * all available instances of cryptographic methods
     */
    private HashMap<String, CryptoMethod> cryptoMethodInstances;

    /**
     * the singelton instance of this class
     */
    private static AvailableCryptoMethods instance = null;

    /**
     * create the algorithm instances
     */
    public AvailableCryptoMethods() {
        cryptoMethodInstances = new HashMap<>();

        // symmetric crypto
        cryptoMethodInstances.put("DES", new DES());
        cryptoMethodInstances.put("AES", new AES());
        cryptoMethodInstances.put("PBEWithSHA1And128BitAES_CBC_BC", new SHAAndAES());
        cryptoMethodInstances.put("PBEWithMD5AndDES", new MD5AndDES());
        cryptoMethodInstances.put("PBEWithSHAAnd40BitRC4", new SHAAndRC4());

        // asymmetric crypto
        cryptoMethodInstances.put("RSAKeyPair", new RSAKeyPair());
        cryptoMethodInstances.put("RSA", new RSA());

        // message digest
        cryptoMethodInstances.put("MD5", new MD5());
        cryptoMethodInstances.put("SHA1", new SHA1());
        cryptoMethodInstances.put("SHA2", new SHA2());
    }

    /**
     * get the available methods instance
     *
     * @return the singleton instance
     */
    public static AvailableCryptoMethods getInstance() {
        if(instance == null) {
            instance = new AvailableCryptoMethods();
        }
        return instance;
    }

    /**
     * executes a specific method on a specific algorithm with options and parameters provided as JSONObject
     *
     * @param algorithm algorithm name
     * @param methodName method name
     * @param parameters JSONArray of parameters
     * @param options JSONObject of options
     * @return a JSONObject containing the used and maybe enriched settings and the return value of the called method
     */
    public JSONObject executeAlgorithmMethod(String algorithm, String methodName, JSONArray parameters, JSONObject options) {
        JSONObject result = new JSONObject();

        TransferableCryptoDetails details = null;
        try {
            // call method on actual algorithm
            switch (methodName) {
                case "encrypt":
                    Encryptable encryptable = (Encryptable) cryptoMethodInstances.get(algorithm);
                    details = encryptable.encrypt(new CryptoSettings(options), parameters.getJSONObject(0).getString("plainText"));
                    break;
                case "decrypt":
                    Decryptable decryptable = (Decryptable) cryptoMethodInstances.get(algorithm);
                    details = decryptable.decrypt(new CryptoSettings(options), parameters.getJSONObject(0).getString("cipherText"));
                    break;
                case "hash":
                    Hashable hashable = (Hashable) cryptoMethodInstances.get(algorithm);
                    details = hashable.hash(new CryptoSettings(options), parameters.getJSONObject(0).getString("input"));
                    break;
                case "generateKeyPair":
                    KeyPairGeneratable keyPairGeneratable = (KeyPairGeneratable) cryptoMethodInstances.get(algorithm);
                    details = keyPairGeneratable.generateKeyPair(new CryptoSettings(options));
                    break;
            }
        }
        catch (Exception e) { System.err.println("1");
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
            e.printStackTrace(); }

        // put settings and returnvalue into the returning JSONObject
        if(details != null) {
            result.put("settings", details.settings.serialize());
            result.put("return", details.payload);
        }
        System.out.println(result);

        return result;
    }

    /**
     * get all available algorithms, their methods, parameters and return values
     *
     * @return JSONObject with all information about the algorithms and methods
     */
    public JSONObject getAllAvailableCryptoMethodsJSON() {
        JSONObject json = new JSONObject();
        JSONArray cryptoMethods = new JSONArray();

        // loop over all available algorithms
        cryptoMethodInstances.forEach((String cryptoMethodName, CryptoMethod cryptoMethod) -> {
            JSONObject cryptoMethodJson = new JSONObject();

            // get class name of the algorithm instance through reflections
            String className = cryptoMethod.getClass().toString().replace("class ste.crypto.methods.", "");

            cryptoMethodJson.put("name", className);
            JSONArray algoMethods = new JSONArray();

            // get methods for algorithm
            CryptoMethod.availableCryptoMethods.forEach((String methodName, AlgoMethodDescription methodDetails) -> {
                JSONObject algoMethod = new JSONObject();
                algoMethod.put("name", methodName);
                algoMethod.put("returns", methodDetails.returns);
                algoMethod.put("parameters", methodDetails.parameters);

                algoMethods.put(algoMethod);
            });

            cryptoMethodJson.put("methods", algoMethods);

            JSONArray algoOptions = new JSONArray();

            // get the required settings/options
            CryptoMethod.requiredCryptoSettings.forEach((String optionName, MethodOptionDescription optionDetails) -> {
                JSONObject algoOption = new JSONObject();
                algoOption.put("name", optionName);
                algoOption.put("type", optionDetails.type);
                algoOption.put("possibleValues", optionDetails.possibleValues);

                algoOptions.put(algoOption);
            });

            cryptoMethodJson.put("options", algoOptions);

            cryptoMethods.put(cryptoMethodJson);
        });

        json.put("cryptoMethods", cryptoMethods);

        return json;
    }
}

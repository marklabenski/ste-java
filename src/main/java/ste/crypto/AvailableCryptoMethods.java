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

import java.util.HashMap;


/**
 * Created by marklabenski on 05.07.17.
 */
public class AvailableCryptoMethods {
    private HashMap<String, CryptoMethod> cryptoMethodInstances;

    private static AvailableCryptoMethods instance = null;

    public AvailableCryptoMethods() {
        cryptoMethodInstances = new HashMap<>();

        // symmetric crypto
        cryptoMethodInstances.put("DES", new DES());
        cryptoMethodInstances.put("AES", new AES());
        cryptoMethodInstances.put("PBEWithSHA1And128BitAES-CBC-BC", new SHAAndAES());
        cryptoMethodInstances.put("PBEWithMD5AndDES", new SHAAndDES());
        cryptoMethodInstances.put("PBEWithSHAAnd40BitRC4", new SHAAndRC4());

        // asymmetric crypto
        cryptoMethodInstances.put("RSAKeyPair", new RSAKeyPair());
        cryptoMethodInstances.put("RSA", new RSA());

        // message digest
        cryptoMethodInstances.put("MD5", new MD5());
        cryptoMethodInstances.put("SHA1", new SHA1());
        cryptoMethodInstances.put("SHA2", new SHA2());
    }

    public static AvailableCryptoMethods getInstance() {
        if(instance == null) {
            instance = new AvailableCryptoMethods();
        }
        return instance;
    }

    public HashMap<String, CryptoMethod> getAvailableAlgorithms() {
        return cryptoMethodInstances;
    }

    public JSONObject executeAlgorithmMethod(String algorithm, String methodName, JSONArray parameters, JSONObject options) {
        System.out.println("so you wanna call " + methodName + " on " + algorithm);
        System.out.println("with options: " + options);

        JSONObject result = new JSONObject();
        TransferableCryptoDetails details = null;
        try {
            System.out.println("is the algo there? :" + cryptoMethodInstances
                    .get(algorithm));

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
        catch (Exception e) { System.out.println(e); }

        result.put("settings", details.settings.serialize());
        result.put("return", details.payload);
        return result;
    }

    public JSONObject getAllAvailableCryptoMethodsJSON() {
        JSONObject json = new JSONObject();
        JSONArray cryptoMethods = new JSONArray();

        cryptoMethodInstances.forEach((String cryptoMethodName, CryptoMethod cryptoMethod) -> {
            JSONObject cryptoMethodJson = new JSONObject();

            String className = cryptoMethod.getClass().toString().replace("class ste.crypto.methods.", "");

            cryptoMethodJson.put("name", className);
            JSONArray algoMethods = new JSONArray();

            cryptoMethod.availableCryptoMethods.forEach((String methodName, AlgoMethodDescription methodDetails) -> {
                JSONObject algoMethod = new JSONObject();
                algoMethod.put("name", methodName);
                algoMethod.put("returns", methodDetails.returns);
                algoMethod.put("parameters", methodDetails.parameters);

                algoMethods.put(algoMethod);
            });

            cryptoMethodJson.put("methods", algoMethods);

            JSONArray algoOptions = new JSONArray();
            cryptoMethod.requiredCryptoSettings.forEach((String optionName, MethodOptionDescription optionDetails) -> {
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

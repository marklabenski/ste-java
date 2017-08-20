package ste.crypto.methods;

import java.util.HashMap;

/**
 * generic cryptographic algorithm with the informations of:
 * - required settings/options to call the method
 * - available methods of the algorithm
 *
 * @author Mark Labenski
 */
public class CryptoMethod {
    /**
     * required settings/options to call the methods
     */
    public static HashMap<String, MethodOptionDescription> requiredCryptoSettings;

    /**
     * available methods of the algorithm
     */
    public static HashMap<String, AlgoMethodDescription> availableCryptoMethods;
}

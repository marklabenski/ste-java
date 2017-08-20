package ste.crypto.methods.asymmetric;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.security.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Generation of Key Pair for usage in RSA method
 *
 * @author Mark Labenski
 */
public class RSAKeyPair extends AbstractKeyPairGenerator {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("keyLength", new MethodOptionDescription("Int", Arrays.asList("1024", "4096")));
            }
        };
        CryptoMethod.availableCryptoMethods = new HashMap<String, AlgoMethodDescription>()
        {
            {
                put("generateKeyPair", new AlgoMethodDescription(null, "keyPair"));
            }
        };
    }

    /**
     * Generate the key pair in the layout described in ste.crypto.abilities.KeyPairGeneratable
     *
     * @see ste.crypto.abilities.KeyPairGeneratable
     * @param cryptoSettings
     * @return
     * @throws Exception
     */
    public TransferableCryptoDetails generateKeyPair(CryptoSettings cryptoSettings) throws Exception {
        Integer keyLength = Integer.parseInt(cryptoSettings.getStringOption("keyLength"));

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

        SecureRandom random = generateSecureRandom();
        generator.initialize(keyLength, random);

        KeyPair pair = generator.generateKeyPair();
        Key pubKey = pair.getPublic();
        Key privKey = pair.getPrivate();

        byte[] base64EncodedPubKey = Base64.encode(pubKey.getEncoded());
        byte[] base64EncodedPrivKey = Base64.encode(privKey.getEncoded());

        // concatinate base64 encoded keys to one String
        String keyPair = new String(base64EncodedPubKey, "US-ASCII") + "|" + new String(base64EncodedPrivKey, "US-ASCII");

        return new TransferableCryptoDetails("keyPair", keyPair, cryptoSettings);
    }

    /**
     * generate a SecureRandom Instance for usage with KeyPairs
     * @return
     */
    public static SecureRandom generateSecureRandom() {
        SecureRandom retSecRand = null;
        try {
            // Initialize a secure random number generator
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

            // Method 1 - Calling nextBytes method to generate Random Bytes
            byte[] bytes = new byte[512];
            secureRandom.nextBytes(bytes);
            retSecRand = secureRandom;
        } catch (NoSuchAlgorithmException noSuchAlgo)
        {
            System.out.println(" No Such Algorithm exists " + noSuchAlgo);
        }
        return retSecRand;
    }
}

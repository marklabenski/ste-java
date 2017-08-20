package ste.crypto.methods.hash;

import ste.crypto.methods.AlgoMethodDescription;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;

/**
 * SHA2/SHA-224, SHA-256, SHA-384 and SHA-512 implementation
 *
 * @author Mark Labenski
 */
public class SHA2 extends AbstractMessageDigestMethod {
    static {
        CryptoMethod.requiredCryptoSettings = new HashMap<String, MethodOptionDescription>()
        {
            {
                put("iterationCount", new MethodOptionDescription("Int"));
                put("hashLength", new MethodOptionDescription("String", Arrays.asList("224", "256", "384", "512")));
            }
        };
        CryptoMethod.availableCryptoMethods = new HashMap<String, AlgoMethodDescription>()
        {
            {
                put("hash", new AlgoMethodDescription(Arrays.asList("input"), "hashValue"));
            }
        };
    }

    @Override
    public TransferableCryptoDetails hash(CryptoSettings cryptoSettings, String input) throws Exception {
        String hashLength = cryptoSettings.getStringOption("hashLength");

        // get the according "SHA-" algorithm by hash length options in settings
        MessageDigest md = MessageDigest.getInstance("SHA-" + hashLength);
        Integer iterationCount = Integer.parseInt(cryptoSettings.getStringOption("iterationCount"));

        // hash the input as often as given in "iterationCount" option
        for(Integer i = 0; i < iterationCount; i++) {
            md.update(input.getBytes("UTF-8"));
        }

        return new TransferableCryptoDetails("hashValue", this.convertDigestBytesToBase64String(md.digest()), cryptoSettings);
    }
}

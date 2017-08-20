package ste.crypto.methods.asymmetric;

import ste.crypto.abilities.KeyPairGeneratable;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Key Pair generation for usage in other cryptographic methods
 *
 * @author Mark Labenski
 */
public abstract class AbstractKeyPairGenerator extends CryptoMethod implements KeyPairGeneratable {
    /**
     * @see ste.crypto.abilities.KeyPairGeneratable
     */
    public TransferableCryptoDetails generateKeyPair(CryptoSettings cryptoSettings) throws Exception {
        return null;
    }
}

package ste.crypto.methods.asymmetric;

import ste.crypto.abilities.Decryptable;
import ste.crypto.abilities.Encryptable;
import ste.crypto.abilities.KeyPairGeneratable;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Created by marklabenski on 28.06.17.
 */
public abstract class AbstractKeyPairGenerator extends CryptoMethod implements KeyPairGeneratable {
    public TransferableCryptoDetails generateKeyPair(CryptoSettings cryptoSettings) throws Exception {
        return null;
    };
}

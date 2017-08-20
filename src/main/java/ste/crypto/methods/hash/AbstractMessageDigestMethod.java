package ste.crypto.methods.hash;

import ste.crypto.abilities.Decryptable;
import ste.crypto.abilities.Encryptable;
import ste.crypto.abilities.Hashable;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.security.NoSuchAlgorithmException;

/**
 * Created by marklabenski on 28.06.17.
 */
public abstract class AbstractMessageDigestMethod extends CryptoMethod implements Hashable {
    public TransferableCryptoDetails hash(CryptoSettings cryptoSettings, String input) throws Exception {
        return null;
    }
}

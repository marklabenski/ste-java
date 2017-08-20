package ste.crypto.methods.asymmetric;

import ste.crypto.abilities.Decryptable;
import ste.crypto.abilities.Encryptable;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Created by marklabenski on 28.06.17.
 */
public abstract class AbstractAsymmetricCryptoMethod extends CryptoMethod implements Decryptable, Encryptable {

    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String ciptherText) throws Exception {
        return null;
    }

    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {
        return null;
    }
}

package ste.crypto.methods.asymmetric;

import ste.crypto.abilities.Decryptable;
import ste.crypto.abilities.Encryptable;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Asymmetric cryptography method that can encrypt and decrypt
 *
 * @author Mark Labenski
 */
public abstract class AbstractAsymmetricCryptoMethod extends CryptoMethod implements Decryptable, Encryptable {
    /**
     * @see ste.crypto.abilities.Decryptable
     */
    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String ciptherText) throws Exception {
        return null;
    }

    /**
     * @see ste.crypto.abilities.Encryptable
     */
    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception {
        return null;
    }
}

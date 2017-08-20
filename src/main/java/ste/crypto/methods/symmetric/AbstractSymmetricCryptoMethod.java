package ste.crypto.methods.symmetric;

import ste.crypto.abilities.Decryptable;
import ste.crypto.abilities.Encryptable;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Symmetric cryptography like DES or AES
 *
 * @author marklabenski
 */
public abstract class AbstractSymmetricCryptoMethod extends CryptoMethod implements Decryptable, Encryptable {
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

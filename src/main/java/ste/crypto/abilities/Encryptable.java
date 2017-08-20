package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Created by marklabenski on 28.06.17.
 */
public interface Encryptable {
    public TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception;
}

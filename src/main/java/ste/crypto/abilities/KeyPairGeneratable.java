package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Created by marklabenski on 20.08.17.
 */
public interface KeyPairGeneratable {
    public TransferableCryptoDetails generateKeyPair(CryptoSettings cryptoSettings) throws Exception;
}

package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.security.NoSuchAlgorithmException;

/**
 * Created by marklabenski on 28.06.17.
 */
public interface Hashable {
    public TransferableCryptoDetails hash(CryptoSettings cryptoSettings, String input) throws Exception;
}

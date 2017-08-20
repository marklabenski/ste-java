package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Ability to hash an input string and return the hash value
 *
 * @author Mark Labenski
 */
public interface Hashable {
    /**
     * cryptographic hash method
     *
     * @param cryptoSettings
     * @param input
     * @return
     * @throws Exception
     */
    TransferableCryptoDetails hash(CryptoSettings cryptoSettings, String input) throws Exception;
}

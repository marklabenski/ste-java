package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Ability to encrypt a plaintext and return cryptodetails containing the ciphertext with given settings
 *
 * @author Mark Labenski
 */
public interface Encryptable {
    /**
     * cryptographic encryptoin of a given ciphertext according to settings depending on implementation uses
     *
     * @param cryptoSettings
     * @param plainText
     * @return Settings and ciphertext in payload
     * @throws Exception
     */
    TransferableCryptoDetails encrypt(CryptoSettings cryptoSettings, String plainText) throws Exception;
}

package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Ability to decrypt a ciphertext into a plaintext with given settings
 *
 * @author Mark Labenski
 */
public interface Decryptable {
    /**
     * cryptographic decryption of a given plaintext according to settings depending on implementation uses
     *
     * @param cryptoSettings
     * @param ciptherText
     * @return Settings and plaintext in payload
     * @throws Exception
     */
    TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String ciptherText) throws Exception;
}

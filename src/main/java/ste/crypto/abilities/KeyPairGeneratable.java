package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

/**
 * Ability to generate a keypair suitable for asymmetric cryptographic methods
 *
 * @author Mark Labenski
 */
public interface KeyPairGeneratable {
    /**
     * generate a keypair for asymmetric cryptographic methods
     *
     * @param cryptoSettings
     * @return Settings and key par in payload as base64 String concatinated with a Pipe (f.e. "<PUBLIC_KEY>|<PRIVATE_KEY")
     * @throws Exception
     */
    TransferableCryptoDetails generateKeyPair(CryptoSettings cryptoSettings) throws Exception;
}

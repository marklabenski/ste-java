package ste.crypto.transfer;

import ste.crypto.settings.CryptoSettings;

/**
 * Transfer object for algorithm settings and a payload
 *
 * @author Mark Labenski
 */
public class TransferableCryptoDetails {
    /**
     * settings for the algorithm
     */
    public CryptoSettings settings;

    /**
     * the payload as a String. Used for plaintext, ciphertext, hashvalue etc.
     */
    public String payload;

    /**
     * identifier for the payload
     */
    private String payloadIdentifier;

    /**
     * creates a crypto detail transfer object
     *
     * @param _payloadIdentifier identifier of the only payload
     * @param _payload the actual payload
     * @param _settings settings needed for or set from algorithm
     */
    public TransferableCryptoDetails(String _payloadIdentifier, String _payload, CryptoSettings _settings) {
        this.payload = _payload;
        this.payloadIdentifier = _payloadIdentifier;
        this.settings = _settings;
    }
}

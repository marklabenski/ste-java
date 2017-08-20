package ste.crypto.transfer;

import ste.crypto.CryptoBackend;
import ste.crypto.settings.CryptoSettings;

/**
 * Created by marklabenski on 28.06.17.
 */
public class TransferableCryptoDetails {
    public CryptoSettings settings;

    public String payload;

    private String payloadIdentifier;

    public TransferableCryptoDetails(String _payloadIdentifier, String _payload, CryptoSettings _settings) {
        this.payload = _payload;
        this.payloadIdentifier = _payloadIdentifier;
        this.settings = _settings;
    }
}

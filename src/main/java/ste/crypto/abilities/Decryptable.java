package ste.crypto.abilities;

import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Created by marklabenski on 28.06.17.
 */
public interface Decryptable {
    public TransferableCryptoDetails decrypt(CryptoSettings cryptoSettings, String ciptherText) throws Exception;
}

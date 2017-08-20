package ste.crypto.methods.hash;

import org.bouncycastle.util.encoders.Base64;
import ste.crypto.abilities.Hashable;
import ste.crypto.methods.CryptoMethod;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import javax.xml.bind.DatatypeConverter;

/**
 * Cryptographic Hash functions known as message digests
 *
 * @author Mark Labenski
 */
public abstract class AbstractMessageDigestMethod extends CryptoMethod implements Hashable {
    /**
     *
     * @see ste.crypto.abilities.Hashable
     */
    public TransferableCryptoDetails hash(CryptoSettings cryptoSettings, String input) throws Exception {
        return null;
    }

    /**
     * convert the bytes got from a digest into an comparable String
     *
     * The output of the digest function from JCE returns a hex binary byte array
     * This function lets you save the output as a string and compare to outputs from other sources
     *
     * @param digest
     * @return A base64 encoded String containing the hash value
     * @throws Exception
     */
    public String convertDigestBytesToBase64String(byte[] digest) throws Exception {
        byte[] convertedHexValue = DatatypeConverter.printHexBinary(digest).toLowerCase().getBytes();

        return new String(Base64.encode(convertedHexValue), "UTF-8");
    }
}

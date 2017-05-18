package ste.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Created by marklabenski on 16.05.17.
 */
public class CryptoBackend {
    private static CryptoBackend ourInstance = new CryptoBackend();

    public static CryptoBackend getInstance() {
        return ourInstance;
    }

    private CryptoBackend() {
        Security.addProvider(new BouncyCastleProvider());
    }
}
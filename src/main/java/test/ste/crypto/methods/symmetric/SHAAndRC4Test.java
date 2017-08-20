package test.ste.crypto.methods.symmetric;

import org.bouncycastle.util.encoders.Base64;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ste.crypto.methods.symmetric.AbstractPBCryptoMethod;
import ste.crypto.methods.symmetric.SHAAndDES;
import ste.crypto.methods.symmetric.SHAAndRC4;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/** 
* SHAAndAES Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/ 
public class SHAAndRC4Test {
    AbstractPBCryptoMethod algorithm;
    CryptoSettings cryptoSettings;
    private static final String PASSWORD = "password";
    private static final String SALT = "12345678";
    private static final String PLAIN_TEXT = "hello";
    private static final String CIPHER_TEXT = "KktNGes=";

@Before
public void before() throws Exception {
    algorithm = new SHAAndRC4();
    cryptoSettings = new CryptoSettings();
    cryptoSettings.addOption("password", new String(Base64.encode(PASSWORD.getBytes("UTF-8"))));
    cryptoSettings.addOption("salt", new String(Base64.encode(SALT.getBytes("UTF-8"))));
    cryptoSettings.addOption("iterationCount", "1024");
} 

@After
public void after() throws Exception {

} 

/** 
* 
* Method: decrypt(CryptoSettings cryptoSettings, String cipherText) 
* 
*/ 
@Test
public void testDecrypt() throws Exception {

    TransferableCryptoDetails testDetails = algorithm.decrypt(cryptoSettings, CIPHER_TEXT);
    assertEquals("should be equal", testDetails.payload, PLAIN_TEXT);
} 

/** 
* 
* Method: encrypt(CryptoSettings cryptoSettings, String plainText) 
* 
*/ 
@Test
public void testEncrypt() throws Exception {
    TransferableCryptoDetails testDetails = algorithm.encrypt(cryptoSettings, PLAIN_TEXT);

    System.out.println(testDetails.payload);

    assertNotNull(testDetails.payload);
    assertEquals("should be equal", testDetails.payload, CIPHER_TEXT);
} 

/** 
* 
* Method: generateKey(CryptoSettings cryptoSettings) 
* 
*/ 
@Test
public void testGenerateKey() throws Exception { 
//TODO: Test goes here... 
} 


} 

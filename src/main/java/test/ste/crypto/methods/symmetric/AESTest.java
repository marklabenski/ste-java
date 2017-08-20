package test.ste.crypto.methods.symmetric; 

import junit.framework.TestCase;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import ste.crypto.methods.symmetric.AES;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/** 
* AES Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/ 
public class AESTest {
    CryptoSettings testCryptoSettings;
    AES testAES;
    String generatedKey;

    private static final String PLAIN_TEXT = "hallo";

    @Before
public void before() throws Exception {
    testCryptoSettings = new CryptoSettings();
    testCryptoSettings.addOption("blockMode", "GCM");
    testCryptoSettings.addOption("padding", "NoPadding");
    testCryptoSettings.addOption("keyLength", "128");

    this.testAES = new AES();
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
    testCryptoSettings.addOption("key", "NZBJSRm5BD3PUnz76pcmdQ==");
    String cipherText = "oLQekV6zecBt0IyqbVf7uu9WaWMY";
    TransferableCryptoDetails testDetails = testAES.decrypt(testCryptoSettings, cipherText);

    assertEquals("should be equal", testDetails.payload, PLAIN_TEXT);
} 

/** 
* 
* Method: encrypt(CryptoSettings cryptoSettings, String plainText) 
* 
*/ 
@Test
public void testEncrypt() throws Exception {
    TransferableCryptoDetails testDetails = testAES.encrypt(testCryptoSettings, PLAIN_TEXT);

    assertNotNull(testDetails.settings.getStringOption("key"));
    generatedKey = testDetails.settings.getStringOption("key");
    assertNotNull(testDetails.payload);
} 


/** 
* 
* Method: getCipherSuiteFromCryptoSettings(CryptoSettings settings) 
* 
*/ 
@Test
public void testGetCipherSuiteFromCryptoSettings() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = AES.getClass().getMethod("getCipherSuiteFromCryptoSettings", CryptoSettings.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 

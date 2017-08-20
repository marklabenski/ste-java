package test.ste.crypto.methods.asymmetric; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import ste.crypto.methods.asymmetric.RSA;
import ste.crypto.methods.asymmetric.RSAKeyPair;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.security.SecureRandom;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/** 
* RSAKeyPair Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/ 
public class RSAKeyPairTest { 
    RSAKeyPair generator;
    RSA rsaTest;
    CryptoSettings cryptoSettings;
@Before
public void before() throws Exception {
    generator = new RSAKeyPair();

    cryptoSettings = new CryptoSettings();
    cryptoSettings.addOption("keyLength", "4096");
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: generateKeyPair(CryptoSettings cryptoSettings) 
* 
*/ 
@Test
public void testGenerateKeyPair() throws Exception {
    TransferableCryptoDetails keyPairStuff = generator.generateKeyPair(cryptoSettings);

    String[] keyParts = keyPairStuff.payload.split("\\|");

    assertEquals("keypair should consist of 2 parts", keyParts.length, 2);
} 

/** 
* 
* Method: generateSecureRandom() 
* 
*/ 
@Test
public void testGenerateSecureRandom() throws Exception { 
    SecureRandom testSecRand = RSAKeyPair.generateSecureRandom();
    assertNotNull(testSecRand);
} 


} 

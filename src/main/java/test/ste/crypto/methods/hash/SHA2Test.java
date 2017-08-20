package test.ste.crypto.methods.hash; 

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import ste.crypto.methods.hash.SHA1;
import ste.crypto.methods.hash.SHA2;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import static org.junit.Assert.assertEquals;

/** 
* SHA2 Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/ 
public class SHA2Test {
    private static final String TEST_INPUT = "hallotest123";

    SHA2 sha2Test;
    CryptoSettings testHashSettings;

@Before
public void before() throws Exception {
    this.sha2Test = new SHA2();
    this.testHashSettings = new CryptoSettings();
    this.testHashSettings.addOption("iterationCount", "1");
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: hash(CryptoSettings cryptoSettings, String input) 
* 
*/ 
@Test
public void testHash256() throws Exception {
    String testExpectedHashValue = "81a698df814559f5b7d331aa50ef90ac7a4446374353a0283579328c714b2526";

    this.testHashSettings.addOption("hashLength", "256");
    TransferableCryptoDetails details = this.sha2Test.hash(this.testHashSettings, TEST_INPUT);
    String testHashValue = new String(Base64.decode(details.payload));

    assertEquals("failure - strings are not equal", testExpectedHashValue, testHashValue);
}

/**
 *
 * Method: hash(CryptoSettings cryptoSettings, String input)
 *
 */
@Test
public void testHash512() throws Exception {
    String testExpectedHashValue = "d481dc2b6dc4189b26bc01ffa37ba4c5efecc4523bc7cd3573713662b462e83c99b7aa15b1f9392173343cbed1f3051ddf5eda4ebd6bd41275a94cb28d6afad3";

    this.testHashSettings.addOption("hashLength", "512");
    TransferableCryptoDetails details = this.sha2Test.hash(this.testHashSettings, TEST_INPUT);
    String testHashValue = new String(Base64.decode(details.payload));

    assertEquals("failure - strings are not equal", testExpectedHashValue, testHashValue);
}



} 

package test.ste.crypto.methods.hash; 

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import ste.crypto.methods.hash.MD5;
import ste.crypto.methods.hash.SHA1;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import static org.junit.Assert.assertEquals;

/** 
* SHA1 Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/ 
public class SHA1Test { 

@Before
public void before() throws Exception { 
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
public void testHash() throws Exception {
    CryptoSettings testHashSettings = new CryptoSettings();
    testHashSettings.addOption("iterationCount", "1");

    SHA1 sha1Test = new SHA1();

    String testInput = "hallotest123";
    String testExpectedHashValue = "d2ca2b1dcf861169ec9de254be121387cd759a10";

    TransferableCryptoDetails details = sha1Test.hash(testHashSettings, testInput);
    String testHashValue = new String(Base64.decode(details.payload));

    assertEquals("failure - strings are not equal", testExpectedHashValue, testHashValue);
} 


} 

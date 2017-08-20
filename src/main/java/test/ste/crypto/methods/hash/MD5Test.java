package test.ste.crypto.methods.hash; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import ste.crypto.methods.hash.MD5;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import org.bouncycastle.util.encoders.Base64;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

import static org.junit.Assert.assertEquals;

/** 
* MD5 Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/ 
public class MD5Test { 

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

    MD5 md5Test = new MD5();

    String testInput = "hallotest123";
    String testExpectedHashValue = "fe45b4866ed99251186ce340d4411996";

    TransferableCryptoDetails details = md5Test.hash(testHashSettings, testInput);
    String testHashValue = new String(Base64.decode(details.payload));

    assertEquals("failure - strings are not equal", testExpectedHashValue, testHashValue);
}


} 

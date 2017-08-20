package test.ste.crypto.methods.hash; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import ste.crypto.methods.hash.AbstractMessageDigestMethod;
import ste.crypto.methods.hash.MD5;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.security.MessageDigest;

import static junit.framework.TestCase.assertEquals;

/** 
* AbstractMessageDigestMethod Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/
public class AbstractMessageDigestMethodTest {
    AbstractMessageDigestMethod algorithm;
    private static final String HASH_STRING = "MDk4ZjZiY2Q0NjIxZDM3M2NhZGU0ZTgzMjYyN2I0ZjY=";
@Before
public void before() throws Exception {
    algorithm = new MD5();
} 

@After
public void after() throws Exception { 
}

/** 
* 
* Method: convertDigestBytesToBase64String(byte[] digest) 
* 
*/ 
@Test
public void testConvertDigestBytesToBase64String() throws Exception {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update("test".getBytes());

    String hashString = algorithm.convertDigestBytesToBase64String(md.digest());

    assertEquals("should equals predefined ", hashString, HASH_STRING);
} 


} 

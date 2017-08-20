package test.ste.crypto.methods.asymmetric; 

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import ste.crypto.methods.MethodOptionDescription;
import ste.crypto.methods.asymmetric.AbstractAsymmetricCryptoMethod;
import ste.crypto.methods.asymmetric.RSA;
import ste.crypto.methods.asymmetric.RSAKeyPair;
import ste.crypto.settings.CryptoSettings;
import ste.crypto.transfer.TransferableCryptoDetails;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

/** 
* RSA Tester. 
* 
* @author <Authors name> 
* @since <pre>Aug 20, 2017</pre> 
* @version 1.0 
*/ 
public class RSATest { 
    AbstractAsymmetricCryptoMethod algorithm;
    CryptoSettings cryptoSettings;
    private static final String PLAIN_TEXT = "hallo test";
    private static final String CIPHER_TEXT = "juJaTiRQe+kfPuBziktzoVvuMLj+JfC8PF6CO46eIOz3wrGE7+UGC8q76aIr5J9Wq+Zwd+S2CxON2NBQ/AUdodV4Hw+fyjicGJ2ujkDt46HYNAAcaY+W+vBx9+rNm4zvL9P4nwakd0lGu3TMRszVHc1obtD40Agqi689tm9dLd8=";
    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDAFN3C6Jc8ove5Ft+b08Ztyob5xzpg1eQ8hZChfZRzNzZAPky1BNEb/UnROfg+0cbuzRzkFAcPSndwQk8g0oKUzh6D5t/lyUQFoLTKO8xccbD6PjQ3GCsGQpQEH+HRJreHOBbwxZ2+A36Bnw4BTGQg/sUVa5aHxoSMrg+gqGqOvQIDAQAB";
    private static final String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMAU3cLolzyi97kW35vTxm3KhvnHOmDV5DyFkKF9lHM3NkA+TLUE0Rv9SdE5+D7Rxu7NHOQUBw9Kd3BCTyDSgpTOHoPm3+XJRAWgtMo7zFxxsPo+NDcYKwZClAQf4dEmt4c4FvDFnb4DfoGfDgFMZCD+xRVrlofGhIyuD6Coao69AgMBAAECgYAZdApo9CJqZhwWmlnaT+dpEB/vxH9PariaboaaZ8Y+uE/HlpnamP9OyrM39wsW4/yNE4AjFyZSV+dY6GiM7GhiLXhXGjOf4deNA0nTPeTkTcSvtlzhyxAONsf3NBohUHGSiNbnNhm8EtygCAaxK+ZdgKho2hrCL+rYPn4mbPk/HwJBAPnFaGRlcPDqE1ZpNRDV7ofhIc/jhVDxlNd6mMnwMbo5EC5nbi+MES3gtCVfH7SytcAI++29R9kA0BIjc8ReBJsCQQDE3yfYS9l7u+uS8rTKgd5HKxv6VP5c+eF34ypz/JODOYgjNxd+RG5Hmeoq4eMBOPXzwrPK8c+VKy5ANJrPVfOHAkEAyq47eQSsMoLv8DXtXt0K8+5nnqfVHgr7qiOM6olkAOafT9iUnDbAFgd6h7z6IIrIW62uqYvoSpqTPbTRE27rswJBAJmG/WesHvpjXh7Grz6NcG5HrBXYWlTfeXko6l5xRgg7wXLU77lEXEvyN68hYQn/Ba8G03cchClr9zGS1HeEK9kCQQCQ+xSKAZQB+xS2Vgbj+Yj6GFByyrkzf5bF/CgyfgV6uEorUKtaOMYf2xS3vROo/dJdSxoKem9lS79q0X57WaYb";
@Before
public void before() throws Exception {
    algorithm = new RSA();
    cryptoSettings = new CryptoSettings();
    cryptoSettings.addOption("keyLength", "1024");
    cryptoSettings.addOption("padding", "NoPadding");

    cryptoSettings.addOption("publicKey", PUBLIC_KEY);
    cryptoSettings.addOption("privateKey", PRIVATE_KEY);
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
    String plainText = testDetails.payload;


    assertEquals("plain text should be the same", plainText, PLAIN_TEXT);
} 

/** 
* 
* Method: encrypt(CryptoSettings cryptoSettings, String plainText) 
* 
*/ 
@Test
public void testEncrypt() throws Exception {
    TransferableCryptoDetails testDetails = algorithm.encrypt(cryptoSettings, PLAIN_TEXT);
    String cipherText = testDetails.payload;

    assertEquals("cipher text should be the same", cipherText, CIPHER_TEXT);
} 


} 

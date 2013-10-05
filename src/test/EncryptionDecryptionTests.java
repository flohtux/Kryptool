package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Util.Progresser;


import core.algorithm.rsa.RSAFacade;
import core.algorithm.rsa.RSAStringUtil;
import core.alphabet.Alphabet47;
import core.alphabet.AlphabetBig;
import core.key.KeyPairRSA;
import core.key.PrivateKeyRSA;
import core.key.PublicKeyRSA;
import core.util.PosBigInt;

public class EncryptionDecryptionTests {
	
	Progresser dummyPrg;
	
	@Before
	public void setUp() {
		dummyPrg = new Progresser();
	}
	
	@Test
	public void test() throws Exception {
		String message = "NICEONE";
		String expected = "NICEONE" + "  ";
		String cipher = RSAStringUtil.enCodeString(new Alphabet47(), message, new PublicKeyRSA(PosBigInt.create(228169), PosBigInt.create(127)), 3, 4,dummyPrg);
		assertEquals(expected, RSAStringUtil.deCodeString(new Alphabet47(),cipher,new PrivateKeyRSA(PosBigInt.create(228169), PosBigInt.create(152063)),3,4,dummyPrg));
	}
	
	@Test
	public void test2() throws Exception {
		String message = "NICEONE?*}";
		KeyPairRSA keys = RSAFacade.generateKeys(100);
		System.out.println(keys.getPublicKey() +";" + keys.getPrivateKey());
		String cipher = RSAStringUtil.enCodeString(new AlphabetBig(), message, keys.getPublicKey(), 1, 100,dummyPrg);
		assertEquals(message, RSAStringUtil.deCodeString(new AlphabetBig(),cipher,keys.getPrivateKey(),1, 100,dummyPrg));
	}
	
	@Test
	public void test3() throws Exception {
		String message = "NICEONE?*}";
		KeyPairRSA keys = RSAFacade.generateKeys(100);
		System.out.println(keys.getPublicKey() +";" + keys.getPrivateKey());
		String cipher = RSAStringUtil.enCodeString(new AlphabetBig(), message, keys.getPublicKey(), 5, 100,dummyPrg);
		assertEquals(message, RSAStringUtil.deCodeString(new AlphabetBig(),cipher,keys.getPrivateKey(),5, 100,dummyPrg));
	}

}

package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Util.Progresser;


import core.algorithm.rsa.RSAStringUtil;
import core.alphabet.Alphabet26;
import core.key.PrivateKeyRSA;
import core.util.PosBigInt;

public class DecryptionTests {

	Progresser dummyPrg;
	
	@Before
	public void setUp() {
		dummyPrg = new Progresser();
	}
	
	@Test
	public void testH25a() throws Exception {
		String cipher = "FDNW";
		String message = RSAStringUtil.deCodeString(new Alphabet26(),cipher,new PrivateKeyRSA(PosBigInt.create(228169), PosBigInt.create(152063)),3,4, dummyPrg);
		assertEquals("USA", message);
	}
	
	@Test
	public void testH25b() throws Exception {
		String cipher = "LXTO";
		String message = RSAStringUtil.deCodeString(new Alphabet26(),cipher,new PrivateKeyRSA(PosBigInt.create(228169), PosBigInt.create(152063)),3,4,dummyPrg);
		assertEquals("FBI", message);
	}
	
	@Test
	public void testH25ab() throws Exception {
		String cipher = "LXTOFDNW";
		String message = RSAStringUtil.deCodeString(new Alphabet26(),cipher,new PrivateKeyRSA(PosBigInt.create(228169), PosBigInt.create(152063)),3,4,dummyPrg);
		assertEquals("FBIUSA", message);
	}
	
	
}

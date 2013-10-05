package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Util.Progresser;


import core.algorithm.rsa.RSAFacade;
import core.alphabet.Alphabet26;
import core.key.PublicKeyRSA;
import core.util.PosBigInt;

public class EncryptionTests {
	
	Progresser dummyPrg;
	
	@Before
	public void setUp() {
		dummyPrg = new Progresser();
	}
	
	@Test
	public void testH25a() throws Exception {
		String message = "USA";
		String cipher = RSAFacade.enCode(new Alphabet26(),message,new PublicKeyRSA(PosBigInt.create(228169), PosBigInt.create(127)),3,4,dummyPrg,true);
		assertEquals("FDNW", cipher);
	}
	
	@Test
	public void testH25b() throws Exception {
		String message = "FBI";
		String cipher = RSAFacade.enCode(new Alphabet26(), message, new PublicKeyRSA(PosBigInt.create(228169), PosBigInt.create(127)), 3, 4,dummyPrg,true);
		assertEquals("LXTO", cipher);
	}
	
	@Test
	public void testH25ab1() throws Exception {
		String message = "USAFBI";
		String cipher = RSAFacade.enCode(new Alphabet26(), message, new PublicKeyRSA(PosBigInt.create(228169), PosBigInt.create(127)), 3, 4,dummyPrg,true);
		assertEquals("FDNWLXTO", cipher);
	}
	
	@Test
	public void testH25ab2() throws Exception {
		String message = "FBIUSA";
		String cipher = RSAFacade.enCode(new Alphabet26(), message, new PublicKeyRSA(PosBigInt.create(228169), PosBigInt.create(127)), 3, 4,dummyPrg,true);
		assertEquals("LXTOFDNW", cipher);
	}

}
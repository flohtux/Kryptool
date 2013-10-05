package test;

import static org.junit.Assert.*;

import org.junit.Test;

import core.algorithm.rsa.RSAFacade;
import core.key.KeyPairRSA;
import core.util.PosBigInt;

public class GenerateKeysTests {

	@Test
	public void testEncodeInversDecode() throws Exception {
		for (int i = 0; i < 100; i++) {
			
			KeyPairRSA keys = RSAFacade.generateKeys(10);
			
			System.out.println("decode=" + keys.getPrivateKey().getDecodeExponent() + ";encode=" + keys.getPublicKey().getEncodeExponent());
			assertFalse(keys.getPrivateKey().getDecodeExponent().equals(keys.getPublicKey().getEncodeExponent()));
		}
	}

}

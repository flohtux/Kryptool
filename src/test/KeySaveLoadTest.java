package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import core.algorithm.ecc.EccFormatException;
import core.algorithm.ecc.EllipticCurve;
import core.algorithm.ecc.Field;
import core.algorithm.ecc.NPoint;
import core.algorithm.ecc.PointFormatException;
import core.key.Key;
import core.key.KeyPairECC;
import core.key.PrivateKeyEcc;
import core.key.PublicKeyEcc;

public class KeySaveLoadTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		PrivateKeyEcc pK = new PrivateKeyEcc(BigInteger.valueOf(1337));
		PublicKeyEcc puk = new PublicKeyEcc(new EllipticCurve(new Field(BigInteger.valueOf(42)), BigInteger.ONE, BigInteger.ZERO),
							BigInteger.valueOf(42), 
							new NPoint(BigInteger.valueOf(234), BigInteger.valueOf(234)), 
							new NPoint(BigInteger.valueOf(234), BigInteger.valueOf(234)));
		KeyPairECC keyp = new KeyPairECC(pK, puk);
		try {
			keyp.saveKeys("kryptool.ecc");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			KeyPairECC loaded = Key.loadEccKeys("kryptool.ktk");
			System.out.println(loaded.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (PointFormatException e) {
			e.printStackTrace();
		} catch (EccFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

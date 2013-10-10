package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.Test;

import Util.ByteUtil;

import common.Tuple;

import core.algorithm.ecc.ECC;
import core.algorithm.ecc.EllipticCurve;
import core.algorithm.ecc.Field;
import core.algorithm.ecc.Point;
import core.key.KeyPairECC;

public class ByteSepTest {

	@Test
	public void test() throws Exception {
		ECC e = new ECC();
		
		KeyPairECC kp= e.generateKeys(128);
		BigInteger m1 = BigInteger.valueOf(123);
		BigInteger m2 = BigInteger.valueOf(-345);

		Tuple<Tuple<BigInteger, BigInteger>, Point> res = e.encode(kp.getPublicKey().getE(), kp.getPublicKey().getP(), kp.getPublicKey().getG(), kp.getPublicKey().getY(), m1, m2);
        Tuple<BigInteger, BigInteger> res2 = e.decode(kp.getPublicKey().getE(),kp.getPublicKey().getP(), res.getFirst().getFirst(), res.getFirst().getSecond(), res.getSecond(), kp.getPrivateKey().getX());
        System.out.println(res2);
	}

}

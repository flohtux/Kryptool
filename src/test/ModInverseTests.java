package test;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import core.euklid.EuklidAlgorithm;
import core.euklid.EuklidTripel;
import core.util.PosBigInt;

public class ModInverseTests {

	@Test
	public void testSimpleEuklid() {
		Assert.assertEquals(PosBigInt.create(1), EuklidAlgorithm.simple(PosBigInt.create(3), PosBigInt.create(4)));
		Assert.assertEquals(PosBigInt.create(12), EuklidAlgorithm.simple(PosBigInt.create(12), PosBigInt.create(24)));
		Assert.assertEquals(PosBigInt.create(2), EuklidAlgorithm.simple(PosBigInt.create(26), PosBigInt.create(12)));
		Assert.assertEquals(PosBigInt.ONE, EuklidAlgorithm.simple(PosBigInt.create(26), PosBigInt.create(37)));
	}

	@Test
	public void testExtendedEuklid() {
		Assert.assertEquals(new EuklidTripel(PosBigInt.ONE, BigInteger.valueOf(-7), BigInteger.valueOf(10)),
				EuklidAlgorithm.extended(PosBigInt.create(37), PosBigInt.create(26)));
	}

}

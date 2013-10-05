package test;

import static org.junit.Assert.assertEquals;


import org.junit.Assert;
import org.junit.Test;

import Whistle.WhistleLevel;
import Whistle.Whistleblower;

import core.util.MathUtil;
import core.util.PosBigInt;

public class ExpoCalcTest {

	@Test
	public void BinExpoModtest() {
		Whistleblower.getInstance().setLevel(WhistleLevel.E_LEARN);
		Whistleblower.getInstance().setWriter(System.out);
		Assert.assertEquals(PosBigInt.create(18), MathUtil.powerByBinExpMod(PosBigInt.create(42), PosBigInt.create(99), PosBigInt.create(30)));
		Assert.assertEquals(PosBigInt.create(134), MathUtil.powerByBinExpMod(PosBigInt.create(123), PosBigInt.create(42), PosBigInt.create(1337)));
		Assert.assertEquals(PosBigInt.create(137), MathUtil.powerByBinExpMod(PosBigInt.create(2345), PosBigInt.create(234), PosBigInt.create(242)));
	}

}

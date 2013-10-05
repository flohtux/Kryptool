package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import Whistle.WhistleLevel;
import Whistle.Whistleblower;

import core.util.MathUtil;
import core.util.PosBigInt;

public class PrimeGenerationTests {
	
	@Before
	public void setup(){
		Whistleblower.getInstance().setWriter(System.out);
		Whistleblower.getInstance().setLevel(WhistleLevel.INFO);
		Whistleblower.whistle("Test Start", WhistleLevel.INFO);
	}

	@Test
	public void testBigNumbers() throws InterruptedException {
		int bitlength = 250;
		Set<PosBigInt> generatedNumbers = new HashSet<PosBigInt>();
		for (int i = 0; i < 5; i++) {
			PosBigInt test = MathUtil.generateProbablePrime(bitlength,10);
			
			this.testAlreadyInSet(test, generatedNumbers);
			this.testCanBeAPrime(test);
			this.testBitlengthIsAtLeast(test, bitlength);
			
			generatedNumbers.add(test);
			System.out.println("Primzahl!:" + test);
		}
		assertTrue(true);
	}
	
	private void testBitlengthIsAtLeast(PosBigInt test, int bitlength) {
		if (test.bitLength() < bitlength) {
			fail("Zu kleine Bitlänge bei <" + test +">:" + test.bitLength());
		}
		
	}

	/**
	 * Testet, ob die Zahl bereits in der Menge der gefundenen Elemente
	 * enthalten war.
	 * @param newElement zu testende Zahl
	 * @param previousTestedNumbers vorher gefundene Elemente
	 */
	private void testAlreadyInSet(PosBigInt newElement, Set<PosBigInt> previousTestedNumbers) {
		if (previousTestedNumbers.contains(newElement)) {
			fail("Es wurde eine Primzahl doppelt gefunden: " + newElement);
		}
	}

	private void testCanBeAPrime(PosBigInt test) {
		if (!(MathUtil.canBeAPrime(test, 40))) {
			fail("Wahrscheinlich keine Primzahl: " + test);
		}
	}
	
	private void testCanBeNoPrime(PosBigInt test) {
		if (MathUtil.canBeAPrime(test, 40)) {
			fail("Wahrscheinlich eine Primzahl: " + test);
		}
	}
	
	@Test
	public void testPrimeNumbersLess100() {
		int[] primesLess100 = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97};
		for (int test : primesLess100) {
			PosBigInt testNumber = PosBigInt.create(test);
			
			this.testCanBeAPrime(testNumber);
			
			//System.out.println("Primzahl!:" + testNumber);
		}
		assertTrue(true);
	}
	
	@Test
	public void testNoPrimeNumbersLess100() {
		int[] primesLess100 = {4,6,8,9,10,12,14,15,16,18,20,21,22,24,25,26,27,28,30,32,33,34,35,36,38,39,40,42,44,45,46,48,49,50,51,52,54,55,56,57,58,60,62,63,64,65,66,68,69,70,72,74,75,76,77,78,80,81,82,84,85,86,87,88,90,91,92,93,94,95,96,98,99};
		for (int test : primesLess100) {
			PosBigInt testNumber = PosBigInt.create(test);
			
			this.testCanBeNoPrime(testNumber);
		}
		assertTrue(true);
	}
	
	@Test
	public void testGenerateRandomNumberOfMinBitlength() {
		SecureRandom r = new SecureRandom();
		for (int i = 0; i <1000; i++) {
			PosBigInt test = MathUtil.generateRandomNumberOfMinBitlength(1000,r);
			testBitlengthIsAtLeast(test, 1000);
			//System.out.println(test);
		}
	}

}

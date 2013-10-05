package core.util;

import java.math.BigInteger;
import java.util.Random;

import common.LegendreSymbol;


public class NumberGeneration {

	private static final BigInteger BIGINT_TWO = BigInteger.valueOf(2);
	private static Random random = new Random();


	/**
	 * Returns
	 * @param m
	 * @return
	 */
	public static BigInteger generateProbablePrime(final Integer bitLength, final Integer security) {
		BigInteger p;
		do {
			p = new BigInteger(bitLength, random).setBit(bitLength-1);
			if (p.bitLength() < bitLength) {
				continue;
			}
			System.out.println("new p"+p);
			Integer randomOneOrMinOne = random.nextBoolean() ? 1 : -1;
			p = BigInteger.valueOf(6).multiply(p).add(BigInteger.valueOf(randomOneOrMinOne));
		} while (! isProbablePrime(p, security));
		return p;
	}

	/**
	 * Checks n for being prime.
	 * @param n number >= 3
	 * @param certainty
	 * @return
	 */
	public static boolean isProbablePrime(final BigInteger n, final int certainty) {
		for (int round = 0; round < certainty; round++) {
			BigInteger a = generateA(n);

			if (!doTest(a,n)) {
				return false;
			}
		}
		return true;

	}
	/**
	 * Checks a^d = 1 (mod n) or a^(d*2^r) = -n (mod n) for at least one r
	 * @param a
	 * @param d
	 * @param n
	 * @return
	 */
	private static boolean doTest(final BigInteger a, final BigInteger n) {
		BigInteger[] sd = divideBy2TillEnd(n.subtract(BigInteger.ONE));
		BigInteger s = sd[0];
		BigInteger d = sd[1];
		if (a.modPow(d, n).equals(BigInteger.ONE)) {
			return true;
		}
		BigInteger n_minus_1 = n.subtract(BigInteger.ONE);
		BigInteger a_pow_d = a.modPow(d, n);
		BigInteger a_squared = a.multiply(a);
		for (BigInteger r = BigInteger.ZERO; r.compareTo(s) < 0; r = r.add(BigInteger.ONE)) {
			if (a_pow_d.equals(n_minus_1)) {
				return true;
			}
			a_pow_d = a_pow_d.multiply(a_squared).mod(n);
		}
		return false;


//		BigInteger n_minus_1 = n.subtract(BigInteger.ONE);
//		for (BigInteger r = BigInteger.ZERO; r.compareTo(s) < 0; r = r.add(BigInteger.ONE)) {
//			// TODO nen richtiges equals schreiben also "kongruent modulo n"
//			if (a.modPow(d.multiply(BIGINT_TWO.modPow(r, n)), n).equals(n_minus_1)) {
//				return true;
//			}
//		}
//		return false;
	}

	/**
	 * Calculates the display of num as 'num = 2^s * d'.
	 * @param num number >= 1
	 * @return {s,d}
	 */
	public static BigInteger[] divideBy2TillEnd(final BigInteger num) {
//		BigInteger s = BigInteger.ZERO;
//		while (true) {
//			if (!num.testBit(0)) { 		// is even?
//				num = num.shiftRight(1);// divide by 2
//				s = s.add(BigInteger.ONE);
//			} else {
//				return new BigInteger[] {s, num};
//			}
//		}
		int s = num.getLowestSetBit();
		return new BigInteger[] {BigInteger.valueOf(s), num.shiftRight(s)};
	}

	public static BigInteger generateA(final BigInteger n) {
		BigInteger a;
		do {
			a = generateRandomNumberBelow(n.subtract(BigInteger.valueOf(1)));
		} while (haveCommonDivisors(a, n));
		return a;
	}

	private static boolean haveCommonDivisors(final BigInteger a, final BigInteger n) {
		// TODO eigener gcd
		return ! a.gcd(n).equals(BigInteger.ONE);
	}

	/**
	 * Generates random number between 2 <= result < n.
	 * @param n
	 * @return
	 */
	public static BigInteger generateRandomNumberBelow(final BigInteger n) {
        BigInteger result;
		do {
            result = new BigInteger(n.bitLength(), random);
        } while (result.compareTo(BigInteger.ONE) <= 0 || result.compareTo(n) >= 0);
		return result;
	}


	public static boolean isPrimitiveRootModP(final BigInteger a, final BigInteger p) {
		return !LegendreSymbol.isQuadraticResidue(a, p);
	}

	public static BigInteger generateProbablePrimeAbove(BigInteger lowerBound) {
		while (!lowerBound.isProbablePrime(110)) {
			lowerBound = lowerBound.add(BigInteger.ONE);
		}
		return lowerBound;
	}

	public static BigInteger generatePrimitiveRootModPAbove(BigInteger lowerBound, final BigInteger p) {
		while (!isPrimitiveRootModP(lowerBound, p)) {
			lowerBound = lowerBound.add(BigInteger.ONE);
		}
		return lowerBound;
	}

	public static BigInteger generateFieldPrime(final int bitLength, final int security) {
		BigInteger a;
		do {
//			 a = generateProbablePrime(bitLength, security);
			a = BigInteger.probablePrime(bitLength, new Random());
		} while (!a.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5)));
		return a;
	}
}

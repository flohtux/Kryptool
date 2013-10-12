package core.util;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import common.LegendreSymbol;


public class NumberGeneration {

	private static final BigInteger BIG_INT_MINUS_ONE = BigInteger.valueOf(-1);
	private static final BigInteger BIG_INT_SIX = BigInteger.valueOf(6);
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
//			BigInteger randomOneOrMinOne = random.nextBoolean() ? BigInteger.ONE : BIG_INT_MINUS_ONE;
//			p = BIG_INT_SIX.multiply(p).add(randomOneOrMinOne);
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
		BigInteger n_minus_1 = n.subtract(BigInteger.ONE);
		int s = n_minus_1.getLowestSetBit();
		BigInteger d = n_minus_1.shiftRight(s);
		Set<BigInteger> usedAs = new HashSet<>();
		for (int round = 0; round < certainty; round++) {
			BigInteger a;
			do {
				a = generateRandomNumberBelow(n_minus_1);
			} while (haveCommonDivisors(a, n) && !usedAs.add(a));
			if (!doTest(a,n,n_minus_1, s,d)) {
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
	private static boolean doTest(BigInteger a, BigInteger n, BigInteger n_minus_1, int s, BigInteger d) {
		BigInteger a_pow_d = a.modPow(d, n);
		if (a_pow_d.equals(BigInteger.ONE)) {
			return true;
		}
		BigInteger a_squared = a.multiply(a);
		for (int r=0; r < s; r++) {
			if (a_pow_d.equals(n_minus_1)) {
				return true;
			}
			a_pow_d = a_pow_d.multiply(a_squared);//.mod(n);
		}
		return false;
	}

	/**
	 * Calculates the display of num as 'num = 2^s * d'.
	 * @param num number >= 1
	 * @return {s,d}
	 */
	public static Tuple<Integer, BigInteger> divideBy2TillEnd(final BigInteger num) {
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
		return new Tuple<Integer, BigInteger>(s, num.shiftRight(s));
	}

	public static BigInteger generateA(final BigInteger n) {
		BigInteger a;
		do {
			a = generateRandomNumberBelow(n.subtract(BigInteger.valueOf(1)));
		} while (haveCommonDivisors(a, n));
		return a;
	}

	private static boolean haveCommonDivisors(final BigInteger a, final BigInteger n) {
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
		Set<BigInteger> factors = NumberGeneration.generatePrimeFactors(p.subtract(BigInteger.ONE));
		Iterator<BigInteger> i = factors.iterator();
		while (i.hasNext()) {
			BigInteger q = i.next();
			if (a.modPow(p.subtract(BigInteger.ONE).divide(q), p).equals(BigInteger.ONE)) {
				return false;
			}
		}
		return true;
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
	
	public static Tuple<BigInteger, BigInteger> generateSecurePrimeAndQ(BigInteger lowerBound) {
		BigInteger p = null;
		BigInteger q = null;
		do {
			p = NumberGeneration.generateProbablePrimeAbove(lowerBound);
			q = BIGINT_TWO.multiply(p).add(BigInteger.ONE);
		} while(!(NumberGeneration.isProbablePrime(p, 100)) );
		return new Tuple<BigInteger, BigInteger>(p, q);
	}
	
	public static BigInteger generatePrimitiveRootModPAbove(BigInteger lowerBound, final BigInteger p, final BigInteger q) {
		BigInteger g = null;
		do {
			g = generateRandomNumberBelow(p.subtract(BIGINT_TWO)).add(BigInteger.ONE);
		} while (g.modPow(q, p).equals(p.subtract(BigInteger.ONE)));
		return g;
	}

	public static BigInteger generateFieldPrime(final int bitLength, final int security) {
		BigInteger a;
		do {
			 a = generateProbablePrime(bitLength, security);
//			a = BigInteger.probablePrime(bitLength, new Random());
		} while (!a.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5)));
		return a;
	}
	
	public static Set<BigInteger> generatePrimeFactors(BigInteger number) {
		Set<BigInteger> result = new HashSet<>();
		BigInteger i = BIGINT_TWO;
		BigInteger sqrtNumber = core.shanks.ShanksAlgorithm.sqrtCeil(number);
		while (i.compareTo(sqrtNumber) <= 0) {
			BigInteger[] divAndRemain = number.divideAndRemainder(i);
			if (divAndRemain[1].equals(BigInteger.ZERO)) {
				result.add(i);
				number = divAndRemain[0];
				sqrtNumber = core.shanks.ShanksAlgorithm.sqrtCeil(number);
				continue;

			}
//			if (number.isProbablePrime(100)) { // riskant...
//				result.add(number);
//				return result;
//			}
//			System.out.println("n="+number+";i="+i+";res="+result);

			if (i.equals(BIGINT_TWO)) {
				i = i.add(BigInteger.ONE);
			} else {
				i = i.add(BIGINT_TWO);
			}
		}
		result.add(number);
		return result;
	}
}

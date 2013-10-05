package core.complex;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import common.LegendreSymbol;
import common.Tuple;

public class TwoSquares {

	private static final String ONE_CONGRUENT_FOUR_MESSAGE = "P has to be kongruent 1 mod 4";

	/**
	 * Provides a collection of tuples (x,y), where x²+y²=p. The collection contains four elements.
	 * The absolute value of each x and each y is the same. Only the sign changes.
	 */
	public static Collection<Tuple<BigInteger, BigInteger>> getSumOfSquares(final BigInteger p) {
		if (!p.mod(BigInteger.valueOf(4)).equals(BigInteger.ONE)) {
			System.out.println(p);
			throw new NumberFormatException(ONE_CONGRUENT_FOUR_MESSAGE);
		}
		Collection<Tuple<BigInteger, BigInteger>> result = new Vector<Tuple<BigInteger,BigInteger>>();
		BigInteger w = findZ(p).modPow(p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
		Collection<ComplexNumber> preResult = ComplexNumber.getGcds(ComplexNumber.create(p), ComplexNumber.create(w, BigInteger.ONE));
		Iterator<ComplexNumber> preIterator = preResult.iterator();
		while (preIterator.hasNext()) {
			ComplexNumber current =  preIterator.next();
			BigInteger x = current.getReal().getInteger();
			if (x.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
				result.add(new Tuple<BigInteger, BigInteger>(x, current.getImaginary().getInteger()));
			} else {
				result.add(new Tuple<BigInteger, BigInteger>(current.getImaginary().getInteger(), x));
			}
		}
		return result;
	}

	/**
	 *	Finds a number between 2 and p exlusive which is no quadratic Residue to p.
	 */
	private static BigInteger findZ(final BigInteger p) {
		BigInteger random = new BigInteger(p.bitLength(), new Random());
		if (random.equals(BigInteger.ONE) || random.equals(BigInteger.ZERO) || random.equals(p)) {
			return findZ(p);
		}
		if (random.compareTo(p)>0) {
			random = random.mod(p);
		}
		if (LegendreSymbol.isQuadraticResidue(random, p))	 {
			return findZ(p);
		}
		return random;
	}

}

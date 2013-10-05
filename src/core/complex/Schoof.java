package core.complex;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;

import common.CommonConstants;
import common.LegendreSymbol;
import common.Tuple;


public class Schoof {

	/**
	 * Gets the number of integer points for an elliptic curve E:= y²=x³-n²*x² in Z(p)
	 */
	public static BigInteger getOrder(final BigInteger prime, final BigInteger n) {
		BigInteger h = getH(prime, n);
		return prime.add(BigInteger.ONE).subtract(h);
	}

	/**
	 *	Provides the last summand for the calculation of the Order of the elliptic curve
	 */
	private static BigInteger getH(final BigInteger prime, final BigInteger n) {
		Collection<Tuple<BigInteger, BigInteger>> squares = TwoSquares.getSumOfSquares(prime);
		BigInteger x = squares.iterator().next().getFirst();
		BigInteger y = squares.iterator().next().getSecond();
		if (n.equals(BigInteger.ONE)) {
			if (x.mod(CommonConstants.BIG_INT_FOUR).equals(BigInteger.ZERO) && y.mod(CommonConstants.BIG_INT_FOUR).equals(CommonConstants.BIG_INT_THREE)) {
				return y.multiply(CommonConstants.BIG_INT_MINUS_TWO);
			}
			if (x.mod(CommonConstants.BIG_INT_FOUR).equals(CommonConstants.BIG_INTEGER_TWO) && y.mod(CommonConstants.BIG_INT_FOUR).equals(BigInteger.ONE)) {
				return y.multiply(CommonConstants.BIG_INT_MINUS_TWO);
			}
			if (x.mod(CommonConstants.BIG_INT_FOUR).equals(BigInteger.ZERO) && y.mod(CommonConstants.BIG_INT_FOUR).equals(BigInteger.ONE)) {
				return y.multiply(CommonConstants.BIG_INTEGER_TWO);
			}
			if (x.mod(CommonConstants.BIG_INT_FOUR).equals(CommonConstants.BIG_INTEGER_TWO) && y.mod(CommonConstants.BIG_INT_FOUR).equals(CommonConstants.BIG_INT_THREE)) {
				return y.multiply(CommonConstants.BIG_INTEGER_TWO);
			}
		}
		ComplexNumber legendre = LegendreSymbol.isQuadraticResidue(n, prime) ? CommonConstants.COMPLEX_1 : CommonConstants.COMPLEX_MINUS_ONE;
		Iterator<Tuple<BigInteger, BigInteger>> i = squares.iterator();
		while (i.hasNext()) {
			Tuple<java.math.BigInteger, java.math.BigInteger> current =  i.next();
			if (ComplexNumber.create(current.getSecond(), current.getFirst()).sub(legendre).div(CommonConstants.COMPLEX_TWO_PLUS_TWO_I).isNotComplex()) {
				return current.getSecond().multiply(CommonConstants.BIG_INTEGER_TWO);
			}
		}
		throw new Error();
	}
}

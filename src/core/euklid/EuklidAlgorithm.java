/**
 * 
 */
package core.euklid;

import java.math.BigInteger;

import core.util.PosBigInt;

/**
 * 
 * A class to hold all the static mathematical euclidean division algorithms
 * 
 * @author webminz
 * 
 */
public class EuklidAlgorithm {

	/**
	 * The initial value for the coefficient of a two steps before.
	 */
	private static final BigInteger START_FOR_X_TWO_AGO = BigInteger.ONE;

	/**
	 * The initial value for the coefficient of a one step before.
	 */
	private static final BigInteger START_FOR_X_ONE_AGO = BigInteger.ZERO;

	/**
	 * The initial value for the coefficient of b two steps before.
	 */
	private static final BigInteger START_FOR_Y_TWO_AGO = BigInteger.ZERO;

	/**
	 * The initial value for the coefficient of b one steo before.
	 */
	private static final BigInteger START_FOR_Y_ONE_AGO = BigInteger.ONE;

	/**
	 * Represents the value of minus one as a Big Integer. Used for inverting.
	 */
	private static final BigInteger MINUS_ONE = BigInteger.valueOf(-1);

	/**
	 * This method represents the extended euclidean division alorithm, which returns the result of a diophantine
	 * equation:
	 * 
	 * <pre>
	 * 		a * x + b * y = gcd(a,b)
	 * </pre>
	 * 
	 * @param a
	 *            A positive integral number of any size.
	 * @param b
	 *            A positive integral number of any size.
	 * @return returns a tripel (g, x, y) where g is the greatest common divisor of a and b and x und y are the
	 *         coefficients of a and b in the diophantine equation, mentioned above.
	 */
	public static EuklidTripel extended(final PosBigInt a, final PosBigInt b) {
		PosBigInt remainderOneAgo = b;
		PosBigInt remainderTwoAgo = a;
		BigInteger yOneAgo = EuklidAlgorithm.START_FOR_Y_ONE_AGO;
		BigInteger yTwoAgo = EuklidAlgorithm.START_FOR_Y_TWO_AGO;
		BigInteger xOneAgo = EuklidAlgorithm.START_FOR_X_ONE_AGO;
		BigInteger xTwoAgo = EuklidAlgorithm.START_FOR_X_TWO_AGO;
		while (!remainderOneAgo.isZero()) {
			final BigInteger q = remainderTwoAgo.divide(remainderOneAgo).asBigInt();
			final PosBigInt saveVariablePI = remainderOneAgo;
			remainderOneAgo = remainderTwoAgo.mod(remainderOneAgo);
			remainderTwoAgo = saveVariablePI;
			BigInteger saveVariableI = xOneAgo;
			xOneAgo = EuklidAlgorithm.calculateCoefficents(xOneAgo, xTwoAgo, q);
			xTwoAgo = saveVariableI;
			saveVariableI = yOneAgo;
			yOneAgo = EuklidAlgorithm.calculateCoefficents(yOneAgo, yTwoAgo, q);
			yTwoAgo = saveVariableI;
		}
		return new EuklidTripel(remainderTwoAgo, xTwoAgo, yTwoAgo);
	}

	/**
	 * This private method calculates the new coefficient of a or b. It inverts the last result multiplies it with the
	 * divisor of a and b and adds the result of the last but one coefficent calculation.
	 * 
	 * @param oneAgo
	 *            the resulting coefficient of given a or b in the last calculation
	 * @param twoAgo
	 *            the resulting coefficient of given a or b in the last but one calculation.
	 * @param factor
	 *            the divisor of a and b.
	 * @return The new coefficent of a or b.
	 */
	private static BigInteger calculateCoefficents(final BigInteger oneAgo, final BigInteger twoAgo,
			final BigInteger factor) {
		return twoAgo.add(EuklidAlgorithm.MINUS_ONE.multiply(factor).multiply(oneAgo));
	}

	/**
	 * This method represents the simple euclidean division algorithm.
	 * 
	 * @param a
	 *            A positive integral number of any size.4
	 * @param b
	 *            A positive integral number of any size.
	 * @return return the greatest common divisor of the two numbers a and b.
	 */
	public static PosBigInt simple(final PosBigInt a, final PosBigInt b) {
		if (b.isZero())
			return a;
		else
			return EuklidAlgorithm.simple(b, a.mod(b));
	}

}

package common;

import java.math.BigInteger;

import core.complex.ComplexNumber;

/**
 * A class wgich provides Constants shared by multiple algorithms.
 *
 */
public class CommonConstants {

	/**
	 * The representation of the number 2 as an instance for the class Big Integer.
	 */
	public static final BigInteger BIG_INTEGER_TWO = BigInteger.valueOf(2);
	public static final BigInteger BIG_INT_MINUS_TWO = BigInteger.valueOf(-2);
	public static final BigInteger BIG_INT_THREE = BigInteger.valueOf(3);
	public static final BigInteger BIG_INT_FOUR = BigInteger.valueOf(4);
	public static final ComplexNumber COMPLEX_TWO_PLUS_TWO_I = ComplexNumber.create(2, 2);
	public static final ComplexNumber COMPLEX_MINUS_ONE = ComplexNumber.create(-1);
	public static final ComplexNumber COMPLEX_1 = ComplexNumber.create(1);

}

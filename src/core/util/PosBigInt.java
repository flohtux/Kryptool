package core.util;

import java.math.BigInteger;
import java.security.SecureRandom;

import core.euklid.EuklidAlgorithm;
import core.exception.NotInvertibleException;

public class PosBigInt {

	private static final int NEGATIVE_SIGNUM = -1;
	/*------- Fields -------*/
	private BigInteger value;
	public static PosBigInt ZERO = PosBigInt.create(BigInteger.ZERO);
	public static PosBigInt ONE = PosBigInt.create(BigInteger.ONE);
	public static PosBigInt TWO = PosBigInt.create(BigInteger.valueOf(2));
	public static PosBigInt TEN = PosBigInt.create(BigInteger.TEN);

	/*------- Constructors ------*/
	private PosBigInt(final String value) {
		this.setValue(new BigInteger(value));
	}

	private PosBigInt(final long value) {
		this.setValue(BigInteger.valueOf(value));
	}

	private PosBigInt(final BigInteger value) {
		this.setValue(value);
	}

	public static PosBigInt create(final String value) {
		return new PosBigInt(value);
	}

	public PosBigInt create(final long value) {
		return new PosBigInt(value);
	}

	public static PosBigInt create(final int value) {
		return new PosBigInt(value);
	}

	public static PosBigInt create(final BigInteger value) {
		return new PosBigInt(value);
	}

	/*--- Getter and Setter ----*/
	/**
	 * @return the value
	 */
	private BigInteger getValue() {
		return this.value;
	}

	public BigInteger asBigInt() {
		return this.getValue();
	}

	/**
	 * @param value
	 *            the value to set
	 */
	private void setValue(final BigInteger value) {
		if (value.signum() < 0)
			throw new NumberFormatException("PosBigInt can't be negativ");
		this.value = value;
	}

	/*----- Math Operations --------------*/

	public PosBigInt add(final PosBigInt x) {
		return PosBigInt.create(this.getValue().add(x.getValue()));
	}

	public PosBigInt subtract(final PosBigInt x) {
		return PosBigInt.create(this.getValue().subtract(x.getValue()));
	}

	public PosBigInt multiply(final PosBigInt x) {
		return PosBigInt.create(this.getValue().multiply(x.getValue()));
	}

	public PosBigInt divide(final PosBigInt x) {
		return PosBigInt.create(this.getValue().divide(x.getValue()));
	}

	public PosBigInt mod(final PosBigInt x) {
		return PosBigInt.create(this.getValue().mod(x.getValue()));
	}

	public PosBigInt gcd(final PosBigInt x) {
		return EuklidAlgorithm.simple(this, x);
	}

	public boolean less(final PosBigInt x) {
		if (this.getValue().compareTo(x.getValue()) < 0)
			return true;
		return false;
	}

	public boolean greater(final PosBigInt x) {
		return !this.less(x) && !this.equals(x);
	}

	public boolean lessEquals(final PosBigInt x) {
		return !this.greater(x);
	}

	public boolean greaterEquals(final PosBigInt x) {
		return !this.less(x);
	}

	public boolean equals(final PosBigInt x) {
		return this.getValue().equals(x.getValue());
	}

	@Override
	public boolean equals(final Object x) {
		if (x instanceof PosBigInt) {
			final PosBigInt value = (PosBigInt) x;
			return this.getValue().equals(value.getValue());
		}
		return false;
	}

	public PosBigInt pow(final int e) {
		return PosBigInt.create(this.getValue().pow(e));
	}

	public int intValue() {
		if (this.greater(PosBigInt.create(Integer.MAX_VALUE)))
			throw new NumberFormatException();
		return this.getValue().intValue();
	}

	public static PosBigInt create(final byte[] bytes) {
		return new PosBigInt(new BigInteger(bytes));
	}

	public static PosBigInt create(final int bitlenght, final SecureRandom r) {
		return new PosBigInt(new BigInteger(bitlenght, r));
	}

	public int bitLength() {
		return this.getValue().bitLength();
	}

	public PosBigInt[] divideAndRemainder(final PosBigInt x) {
		final BigInteger[] bigPair = this.getValue().divideAndRemainder(x.getValue());
		final PosBigInt[] result = new PosBigInt[2];
		result[0] = PosBigInt.create(bigPair[0]);
		result[1] = PosBigInt.create(bigPair[1]);
		return result;
	}

	public PosBigInt getInverse(final PosBigInt m) throws NotInvertibleException {
		BigInteger result = EuklidAlgorithm.extended(this, m).getInverseOfA();
		while (result.signum() == (PosBigInt.NEGATIVE_SIGNUM)) {
			result = result.add(m.asBigInt());
		}
		return PosBigInt.create(result);
	}

	public boolean isZero() {
		return this.equals(PosBigInt.ZERO);
	}

	public String toString(final int i) {
		return this.getValue().toString(i);
	}

	@Override
	public String toString() {
		return this.getValue().toString();
	}
}

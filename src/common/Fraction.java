package common;

import java.math.BigInteger;

/**
 * 	A class which represents rational Numbers.
 *
 */
public class Fraction {

	private static final String DENOMINATOR_MUST_NOT_BE_ZERO = "Denominator must not be zero!";
	private static final BigInteger BIZero = new BigInteger("0");
	private static final BigInteger BIOne = new BigInteger("1");
	private static final BigInteger BIMinusOne = new BigInteger("-1");
	public static final Fraction Null = new Fraction(BIZero, BIOne);
	private static final String FractionStroke = "/";

	public static Fraction parse(final String fraction) {
		if (fraction == null || fraction.length()==0) {
			return new Fraction(BIZero, BIOne);
		}

		BigInteger denominator = BIOne;
		BigInteger enumarator = BIOne;
		try {
			int fractionStrokePosition = fraction.indexOf(FractionStroke);
			String enumaratorText = fraction.substring(0,fractionStrokePosition);
			if (enumaratorText.length() > 0) {
				enumarator = new BigInteger(enumaratorText);
			}
			String denominatorText = fraction.substring(fractionStrokePosition + FractionStroke.length(), fraction.length());
			if (denominatorText.length() > 0) {
				denominator = new BigInteger(denominatorText);
			}
		} catch (IndexOutOfBoundsException iobe){
			enumarator = new BigInteger(fraction);
		}
		if (denominator.equals(BIZero)) {
			throw new NumberFormatException(DENOMINATOR_MUST_NOT_BE_ZERO);
		}
		return new Fraction(enumarator,denominator);
	}

	private final BigInteger enumerator;
	public BigInteger getEnumerator() {
		return enumerator;
	}
	public BigInteger getDenominator() {
		return denominator;
	}

	private final BigInteger denominator;
	public static final Fraction FRACTION_ONE = parse("1");
	public static final Fraction FRACTION_MINUS_ONE = parse("-1");
	public static final Fraction FRACTION_ZERO = parse("0");

	public Fraction(final BigInteger enumarator, final BigInteger denominator) {
		BigInteger gcd = enumarator.gcd(denominator);
		boolean negativeDenominator = denominator.compareTo(BIZero) < 0;
		this.enumerator = enumarator.divide(gcd).multiply(negativeDenominator ? BIMinusOne : BIOne);
		this.denominator = denominator.divide(gcd).multiply(negativeDenominator ? BIMinusOne : BIOne);
		if (denominator.equals(BIZero)) {
			throw new NumberFormatException(DENOMINATOR_MUST_NOT_BE_ZERO);
		}
	}
	@Override
	public String toString(){
		return this.getEnumerator().toString() + (this.getDenominator().equals(BIOne) ? "" : FractionStroke + this.getDenominator().toString());
	}

	public Fraction add(final Fraction summand) {
		return new Fraction(getEnumerator().multiply(summand.getDenominator()).add(summand.getEnumerator().multiply(getDenominator())),

				getDenominator().multiply(summand.getDenominator()))
		;
	}

	public Fraction sub(final Fraction minuend)	 {
		return new Fraction(getEnumerator().multiply(minuend.getDenominator()).subtract(minuend.getEnumerator().multiply(getDenominator())),

				getDenominator().multiply(minuend.getDenominator()))
		;
	}

	public Fraction mul(final Fraction factor) {
		return new Fraction(getEnumerator().multiply(factor.getEnumerator()), getDenominator().multiply(factor.getDenominator()));
	}

	public Fraction div(final Fraction divisor) {
		return new Fraction(getEnumerator().multiply(divisor.getDenominator()), getDenominator().multiply(divisor.getEnumerator()));
	}

	public boolean lessEq(final Fraction other) {
		return getEnumerator().multiply(other.getDenominator()).compareTo(getDenominator().multiply(other.getEnumerator())) <= 0;
	}

	public BigInteger floor() {
		if (FRACTION_ZERO.lessEq(this)) {
		return getEnumerator().divide(getDenominator());
		} else {
			return getEnumerator().divide(getDenominator()).add(BIOne);
		}
	}

	public BigInteger ceiling()	 {
		if (FRACTION_ZERO.lessEq(this)) {
		return getEnumerator().divide(getDenominator()).add(BigInteger.ONE);
		} else {
			return getEnumerator().divide(getDenominator());
		}
	}

	public boolean isInteger() {
		return getEnumerator().divide(getDenominator()).multiply(getDenominator()).equals(getEnumerator());
	}

	public BigInteger getInteger() {
		if (isInteger()) {
			return getEnumerator().divide(getDenominator());
		}
		throw new NumberFormatException();
	}


	@Override
	public boolean equals(final Object argument){
		if (argument instanceof Fraction){
			Fraction argumentAsFraction = (Fraction) argument;
			return this.getEnumerator().equals(argumentAsFraction.getEnumerator()) && this.getDenominator().equals(argumentAsFraction.getDenominator());
		} else {
			return false;
		}
	}
	@Override
	public int hashCode(){
		return this.getEnumerator().multiply(this.getDenominator()).hashCode();
	}

	public static Fraction create(final int enumerator, final int denominator) {
		return new Fraction(BigInteger.valueOf(enumerator), BigInteger.valueOf(denominator));
	}

	public static Fraction create(final int enumerator)	 {
		return new Fraction(BigInteger.valueOf(enumerator), BIOne);
	}


}

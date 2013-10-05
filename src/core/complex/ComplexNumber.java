package core.complex;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Vector;

import common.Fraction;

/**
 *	A class which represents complex numbers.
 *
 */
public class ComplexNumber {

	private ComplexNumber(final Fraction real, final Fraction imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 *	Adds two Complex Numbers and creates a new Complex Number as result.
	 */
	public ComplexNumber add(final ComplexNumber summand) {
		return new ComplexNumber(getReal().add(summand.getReal()), getImaginary().add(summand.getImaginary()));
	}

	/**
	 * Subtracts two Complex Numbers and created a new Complex Number as Result.
	 */
	public ComplexNumber sub(final ComplexNumber subtrahend) {
		return new ComplexNumber(getReal().sub(subtrahend.getReal()), getImaginary().sub(subtrahend.getImaginary()));
	}

	/**
	 * Multiplies two Complex Numbers and creates a new Complex Number as Result.
	 */
	public ComplexNumber mul(final ComplexNumber factor) {
		return new ComplexNumber(getReal().mul(factor.getReal()).sub(getImaginary().mul(factor.getImaginary())), getReal().mul(factor.getImaginary()).add(getImaginary().mul(factor.getReal())));
	}

	/**
	 *	Divides two Complex Numbers and created a new Complex Number as Result.
	 */
	public ComplexNumber div(final ComplexNumber divisor) {
		ComplexNumber cj = divisor.getComplexConjugate();
		ComplexNumber preResult = this.mul(cj);
		Fraction div = divisor.absolute();
		return new ComplexNumber(preResult.getReal().div(div), preResult.getImaginary().div(div));
	}

	/**
	 *	Provides the complex Conjugate of this Complex Number.
	 *	Invariant: this mul this.getComplexConjugate == this.absolute
	 */
	public ComplexNumber getComplexConjugate() {
		return new ComplexNumber(getReal(), getImaginary().mul(Fraction.FRACTION_MINUS_ONE));
	}

	/**
	 *	Provides the absolute of this Complex Number.
	 */
	public Fraction absolute() {
		ComplexNumber result = this.mul(this.getComplexConjugate());
		if (result.getImaginary().equals(Fraction.FRACTION_ZERO)) {
			return result.getReal();
		}
		throw new NumberFormatException();
	}

	private final Fraction real;

	private final Fraction imaginary;

	public Fraction getReal() {
		return real;
	}

	public Fraction getImaginary() {
		return imaginary;
	}

	/**
	 *	Provides the next bigger Complex Number which consists of integer numbers in the real and imaginary part.
	 */
	public ComplexNumber ceiling() {
		return ComplexNumber.create(getReal().sub(Fraction.create(1, 2)).ceiling(), getImaginary().sub(Fraction.create(1, 2)).ceiling());
	}


	public static ComplexNumber create(final BigInteger real, final BigInteger imaginary) {
		return new ComplexNumber(new Fraction(real, BigInteger.ONE), new Fraction(imaginary, BigInteger.ONE));
	}

	public static ComplexNumber create(final int real, final int imaginary) {
		return new ComplexNumber(Fraction.create(real), Fraction.create(imaginary));
	}

	public static ComplexNumber create(final BigInteger real) {
		return new ComplexNumber(new Fraction(real, BigInteger.ONE), Fraction.FRACTION_ZERO);
	}

	public static ComplexNumber create(final int real) {
		return new ComplexNumber(Fraction.create(real), Fraction.FRACTION_ZERO);
	}

	public static ComplexNumber create(final Fraction real) {
		return new ComplexNumber(real, Fraction.FRACTION_ZERO);
	}

	public static ComplexNumber create(final Fraction real, final Fraction imaginary) {
		return new ComplexNumber(real, imaginary);
	}
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof ComplexNumber) {
			return  equals((ComplexNumber)obj);
		}
		return false;
	}

	private boolean equals(final ComplexNumber other) {
		return this.real.equals(other.real) && this.imaginary.equals(other.imaginary);
	}

	/**
	 *	Implementation of the algorithm of euclid implemented in the complexx numbers.
	 *	Provides only one Complex Number.
	 */
	private static ComplexNumber gcdPrivate(final ComplexNumber a, final ComplexNumber b) {
		if (!b.absolute().lessEq(a.absolute()))	 {
			return gcdPrivate(b,a);
		}
		if (b.equals(ComplexNumber.create(0))) {
			return a;
		} else {
			return gcdPrivate(b, a.sub(b.mul(a.div(b).ceiling())));
		}
	}

	/**
	 * Provides true if the imaginary part of this number is equal to zero.
	 */
	public boolean isNotComplex() {
		return getImaginary().equals(Fraction.FRACTION_ZERO);
	}

	/**
	 * Provides all four common divisors of the complex Numbers a and b.
	 */
	public static Collection<ComplexNumber> getGcds(final ComplexNumber a, final ComplexNumber b) {
		ComplexNumber oneResult = gcdPrivate(a, b);
		Collection<ComplexNumber> result = new Vector<ComplexNumber>();
		result.add(oneResult);
		result.add(new ComplexNumber(oneResult.getReal().mul(Fraction.FRACTION_MINUS_ONE), oneResult.getImaginary()));
		result.add(new ComplexNumber(oneResult.getReal(), oneResult.getImaginary().mul(Fraction.FRACTION_MINUS_ONE)));
		result.add(new ComplexNumber(oneResult.getReal().mul(Fraction.FRACTION_MINUS_ONE), oneResult.getImaginary().mul(Fraction.FRACTION_MINUS_ONE)));
		return result;
	}

	@Override
	public String toString() {
		if (Fraction.FRACTION_ZERO.lessEq(getImaginary())) {
		return getReal().toString() + "+" + getImaginary()+"i";
		} else {
			return getReal().toString() + getImaginary()+"i";
		}
	}


}

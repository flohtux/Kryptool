package common;

import java.math.BigInteger;

public class LegendreSymbol {

	private final BigInteger a;
	private final BigInteger p;
	
	private LegendreSymbol(BigInteger a, BigInteger p) {
		this.a = a;
		this.p = p;
	}
	
	private BigInteger calculate() {
		return this.getA().modPow(this.getP().subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), this.getP());
	}
	
	private BigInteger getP() {
		return p;
	}

	private BigInteger getA() {
		return a;
	}
	
	/**
	 * Calculates the legendre symbol.
	 * Calculates whether a is a quadratic residue on p.
	 * @param c relatively prime to p!
	 * @param p uneven prime
	 * @return true if and only if an int x exists with x² = a (mod p)
	 */
	public static Boolean isQuadraticResidue(BigInteger c, BigInteger p) {
		return new LegendreSymbol(c, p).calculate().equals(BigInteger.ONE);
	}
}

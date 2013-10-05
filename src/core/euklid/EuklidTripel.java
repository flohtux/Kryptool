/**
 * 
 */
package core.euklid;

import java.math.BigInteger;

import core.util.PosBigInt;

/**
 * A class to represent to result of the extended euclidean division algorithm, which is a tripel. As the extended
 * euklidian algorithm returns the results of a Diophantine equation:
 * 
 * <pre>
 * 		a * x + b * y = gcd(a,b)
 * </pre>
 * 
 * this tripel stores the gcd of A and B, the inverse x of A and the inverse y of B.
 * 
 * @author webminz
 */
public class EuklidTripel {

	/**
	 * Provides the gratest common divisor as a result of the extended eclidean division algorithm
	 */
	private final PosBigInt gcd;

	/**
	 * Provides the number x, which is member of the linear dipphantine equation ax + bx = (a,b)
	 */
	private final BigInteger inverseOfA;

	/**
	 * Provides the number y, which is a member of the linear diophantine equation ax+by= (a,b)
	 */
	private final BigInteger inverseOfB;

	/**
	 * Regular construtor for a euclid triple.
	 * 
	 * @param gcd
	 *            The greatest common divisor as a result of the algotithm
	 * @param inverseOfA
	 *            x as a result of the algorithm
	 * @param inverseOfB
	 *            y as a result of the algorithm
	 */
	public EuklidTripel(final PosBigInt gcd, final BigInteger inverseOfA, final BigInteger inverseOfB) {
		super();
		this.gcd = gcd;
		this.inverseOfA = inverseOfA;
		this.inverseOfB = inverseOfB;
	}

	/**
	 * standard getter
	 * 
	 * @return the greatest common divisor
	 */
	public PosBigInt getGcd() {
		return this.gcd;
	}

	/**
	 * standard getter
	 * 
	 * @return the number with which a multiplied is a solution of the equation of bezout.
	 */
	public BigInteger getInverseOfA() {
		return this.inverseOfA;
	}

	/**
	 * standard getter
	 * 
	 * @return the number with which b is multiplied is a solution of the equation of bezout.
	 */
	public BigInteger getInverseOfB() {
		return this.inverseOfB;
	}

	@Override
	public boolean equals(final Object obj) {
		return obj instanceof EuklidTripel && this.equals((EuklidTripel) obj);
	}

	/**
	 * Equals. Provides true if two instances of a euclid triple are equal. They are the same if gcd, x and y are
	 * similar.
	 * 
	 * @param ob
	 *            another instance of an euklid triple
	 * @return true if this is equal to ob
	 */
	private boolean equals(final EuklidTripel ob) {
		return this.getGcd().equals(ob.getGcd()) && this.getInverseOfA().equals(ob.getInverseOfA())
				&& this.getInverseOfB().equals(ob.getInverseOfB());
	}

	@Override
	public String toString() {
		return "{" + this.getGcd() + ", " + this.getInverseOfA() + ", " + this.getInverseOfB() + "}";
	}
}

package core.algorithm.ecc;

import java.math.BigInteger;

import common.LegendreSymbol;
import common.Tuple;

import core.complex.Schoof;
import core.key.KeyPairECC;
import core.key.PrivateKeyEcc;
import core.key.PublicKeyEcc;
import core.util.NumberGeneration;
public class ECC {


	private static final BigInteger BIG_INT_TWO = BigInteger.valueOf(2);
	private static final BigInteger BIG_INT_THREE = BigInteger.valueOf(3);
	private static final BigInteger BIG_INT_MINUS_ONE = BigInteger.valueOf(-1);
	private static final BigInteger BIG_INT_FOUR = BigInteger.valueOf(4);
	private static final BigInteger BIG_INT_NINE = BigInteger.valueOf(9);
	private static final BigInteger BIG_INT_EIGHT = BigInteger.valueOf(8);

	private static final BigInteger SMALL_N = BigInteger.ONE;


	/**
	 * This methods creates a Key Pair for the elliptic curve cryptogrphy algorithm
	 * @param primebitlenght The bit length of the random  Prime number used for generation
	 * @param security The amount of checks performed in the Miller-Rabin primality Testing.
	 * @return A Key Pair with the private and the public key fot the  elliptic curve cryptogrphy algorithm
	 * @throws Exception An exception might be thrown if the multiplication on the elliptic curve fails.
	 */
	 public KeyPairECC generateKeys(final int primebitlenght, final int security) throws Exception {
		System.out.println("[Starting generation of Keys](params: bitLength->"+ primebitlenght+",primeSecurityChecks->"+security+")");
		BigInteger n = SMALL_N;
		BigInteger N;
		BigInteger p;
		do {
			System.out.print('|');
			if(Thread.currentThread().isInterrupted()) {
				System.err.println("Abbruch");
				throw new InterruptedException(); // Wozu ?
			}
			p = NumberGeneration.generateFieldPrime(primebitlenght, security);
			N = Schoof.getOrder(p, n);
		} while (!NumberGeneration.isProbablePrime( N.divide(BIG_INT_EIGHT), security));
		EllipticCurve E = new EllipticCurve(new Field(p), n.multiply(n), BigInteger.ZERO);
		NPoint g = calculateG(E, p);
		BigInteger privateX= NumberGeneration.generateRandomNumberBelow(N.divide(BIG_INT_EIGHT));
		System.out.println();
		System.out.println("[Key Pair generated]");
		Point publicY = E.mulFast(g, privateX);
		return new KeyPairECC(new PrivateKeyEcc(privateX), new PublicKeyEcc(E, p, g, publicY));
	}

	/**
	 * This method calculates a random point g on the elliptic curve  for further uses in the generation of the keys.
	 * @param e The elliptic curve
	 * @param p The random prime used for the field
	 * @return Returns a point g on the elliptic curve.
	 * @throws Exception An exception might be thrown if the calculation on the elliptic curve fails.
	 */
	private NPoint calculateG(final EllipticCurve e, final BigInteger p) throws Exception {
		Point px;
		NPoint g;
		do  {
			System.out.print('|');
			core.util.Tuple<BigInteger, BigInteger> xAndY = calculateXandY(e, p);
			 g = new NPoint(xAndY.getFirst(), xAndY.getSecond());
			 px = e.mulFast(g, BIG_INT_NINE);
		} while (e.isInfinitePoint(px));
		return g;
	}

	/**
	 * Calculates the coordinates X and Y for a random point g on the elliptic curve e.
	 * @param e The elliptic curvel
	 * @param p The field prime.
	 * @return
	 */
	private core.util.Tuple<BigInteger, BigInteger> calculateXandY(final EllipticCurve e, final BigInteger p) {
		BigInteger x, y;
		BigInteger r;
		boolean f1,f2;
		do {
			System.out.print('|');
			x = NumberGeneration.generateRandomNumberBelow(p);
			r = e.resolveRightSide(x);
			f1 = !LegendreSymbol.isQuadraticResidue(r, p);
			f2 = !r.modPow(p.subtract(BigInteger.ONE).divide(BIG_INT_FOUR), p).equals(BigInteger.ONE) || r.modPow(p.subtract(BigInteger.ONE).divide(BIG_INT_FOUR), p).equals(BIG_INT_MINUS_ONE);
		} while (f1 || f2);
		y = this.calculateY(r, p);
		return new core.util.Tuple<BigInteger, BigInteger>(x, y);
	}

	/**
	 * Calculated square root.
	 * @param p
	 * @param p2
	 * @return (x,r)
	 */
	private BigInteger calculateY(final BigInteger r, final BigInteger p) {
		if (r.modPow(p.subtract(BigInteger.ONE).divide(BIG_INT_FOUR), p).equals(BigInteger.ONE)) {
			return r.modPow(p.add(BIG_INT_THREE).divide(BIG_INT_EIGHT), p);
		}
		else {
			return BIG_INT_TWO.modInverse(p).multiply(BIG_INT_FOUR.multiply(r).modPow(p.add(BIG_INT_THREE).divide(BIG_INT_EIGHT), p));
		}

	}

	/**
	 * Encodes two two number blocks m1 m2 with the ECC-Algorithm
	 * @param e The Elliptic curve used.
	 * @param p The field prime used.
	 * @param g The point g on the elliptic curve.
	 * @param y The point y on the elliptiv curve.
	 * @param m1 The first message block: a number
	 * @param m2 The second message block: a number.
	 * @return A Tuple where the first part is a tuple of the two encrypted blocks and the secon component is the Point a on the elliptic curve e.
	 * @throws Exception exception might be thrown if the multiplication on the elliptic curve fails.
	 */
	public Tuple<Tuple<BigInteger, BigInteger>, Point> encode(final EllipticCurve e, final BigInteger p, final Point g, final Point y, final BigInteger m1, final BigInteger m2) throws Exception {
		BigInteger k;
		Point ky;
		NPoint kyn  = new NPoint(BigInteger.ONE, BigInteger.ONE);
		boolean isNotOk;
		do {
			k = NumberGeneration.generateRandomNumberBelow(p);
			ky = e.mulFast(y, k);
			isNotOk = e.isInfinitePoint(ky);
			if (!isNotOk) {
				kyn = (NPoint)ky;
				isNotOk = kyn.getX().equals(BigInteger.ZERO) || kyn.getY().equals(BigInteger.ZERO);
			}
		} while (isNotOk);
		Point a = e.mulFast(g, k);
		BigInteger b1 = kyn.getX().multiply(m1).mod(p);
		BigInteger b2 = kyn.getY().multiply(m2).mod(p);
		return new Tuple<Tuple<BigInteger,BigInteger>, Point>(new Tuple<BigInteger , BigInteger>(b1, b2), a);
	}

	/**
	 * Decodes a given pair of encrypted blocks with the ECC-Algorithm
	 * @param e The elliptic curve
	 * @param p The field prime p.
	 * @param b1 The first enrypted block
	 * @param b2 The second encrypted block
	 * @param a The Point a on the elliptic curve, which was product of the encryption.
	 * @param x The private key x.
	 * @return A tuple containing the two decrypted blocks m1 and m2.
	 * @throws Exception exception might be thrown if the multiplication on the elliptic curve fails.
	 */
	public Tuple<BigInteger, BigInteger> decode(final EllipticCurve e, final BigInteger p,final BigInteger b1, final BigInteger b2, final Point a, final BigInteger x) throws Exception {
		Point xa = e.mulFast(a, x);
		NPoint xan;
		if (!e.isInfinitePoint(xa)) {
			xan = (NPoint)xa;
		} else {
			throw new Exception();
		}
		BigInteger m1 = b1.multiply(xan.getX().modInverse(p)).mod(p);
		BigInteger m2 = b2.multiply(xan.getY().modInverse(p)).mod(p);
		return new Tuple<BigInteger, BigInteger>(m1, m2);
	}



}


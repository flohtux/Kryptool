package core.algorithm.ecc;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import core.util.MathUtil;
import core.util.PosBigInt;

/**
 * y^2=x^3+ax+b
 * @author tajoa
 *
 */
public class EllipticCurve {

	private final Field field;
	private final BigInteger a;
	private final BigInteger b;

	public EllipticCurve(final Field field, final BigInteger a, final BigInteger b){
		//if(field.getP() muss Prim sein mit Miller überprüfen)
		this.field = field;
		this.a = a;
		this.b = b;
	}

	public Field getField() {
		return field;
	}

	public BigInteger getA() {
		return a;
	}

	public BigInteger getB() {
		return b;
	}

	public Point mul(final Point p,  BigInteger times) throws Exception{
		Point current = p;
		while(!times.equals(BigInteger.ZERO)){
			current = this.add(current, p);
			times = times.subtract(BigInteger.ONE);
		}
		return current;
	}
	
	public Point mulFast(final Point p, final BigInteger times) throws Exception{
		final LinkedList<Integer> binOfExp = this.decimalToBinAsList(times);
		Collections.reverse(binOfExp);
		Point currentResult = p;
		Point currentsquareResult = p;
		Iterator<Integer> i = binOfExp.iterator();
		while(i.hasNext()){
			Integer currentValue = i.next();
			if(currentValue == 1){
				currentResult = this.add(currentResult,currentsquareResult);
			}
			currentsquareResult = this.add(currentsquareResult, currentsquareResult);
		}
		return currentResult;
	}

	private LinkedList<Integer> decimalToBinAsList(BigInteger dec) {
		final LinkedList<Integer> result = new LinkedList<Integer>();
		if (dec.equals(BigInteger.ZERO))
			result.add(0);
		while (dec.compareTo(BigInteger.ZERO) > 0) {
			final BigInteger x = dec.mod(BigInteger.valueOf(2));
			result.addFirst(x.intValue());
			dec = dec.subtract(x).divide(BigInteger.valueOf(2));
		}
		return result;
	}	

	public Point add(final Point p1, final Point p2) throws Exception{
		if(!(this.checkPoint(p1) && this.checkPoint(p2))) {
			throw new Exception();
		}
		if(this.isInfinitePoint(p1)) {
			return p2;
		}
		if(this.isInfinitePoint(p2)) {
			return p1;
		}
		NPoint p1n = (NPoint)p1;
		NPoint p2n = (NPoint)p2;
		BigInteger m = BigInteger.ZERO;
		if(!p1n.getX().equals(p2n.getX())){
			m = p2n.getY().subtract(p1n.getY()).multiply(p2n.getX().subtract(p1n.getX()).modInverse(this.getField().getP())).mod(this.getField().getP());
		} else if (!p1n.getY().add(p2n.getY()).mod(this.getField().getP()).equals(BigInteger.ZERO)){
			BigInteger a = BigInteger.valueOf(3).multiply(p1n.getX().modPow(BigInteger.valueOf(2), this.getField().getP())).add(this.getA()).mod(this.getField().getP());
			BigInteger b = BigInteger.valueOf(2).multiply(p1n.getY()).mod(this.getField().getP());
			m = a.multiply(b.modInverse(this.getField().getP())).mod(this.getField().getP());
		} else {
			return new InfinitePoint();
		}
		BigInteger x = m.modPow(BigInteger.valueOf(2), this.getField().getP()).subtract(p1n.getX()).subtract(p2n.getX()).mod(this.getField().getP());
		BigInteger y = m.negate().multiply(x.subtract(p1n.getX())).mod(this.getField().getP()).subtract(p1n.getY()).mod(this.getField().getP());
		return new NPoint(x,y);
	}
	
	private boolean checkPoint(final Point p){
		return p.accept(new PointVisitor() {
			@Override
			public boolean handleNPoint(final NPoint p) {
				return p.getY().modPow(BigInteger.valueOf(2), field.getP()).equals(
						p.getX().modPow(BigInteger.valueOf(3), EllipticCurve.this.getField().getP()).add(
								EllipticCurve.this.getA().multiply(p.getX())).add(
										EllipticCurve.this.getB()).mod(EllipticCurve.this.getField().getP()));
			}
			@Override
			public boolean handleInfinitePoint(final InfinitePoint p) {
				return true;
			}
		});
	}
	public BigInteger resolveRightSide(final BigInteger x) {
		return x.modPow(BigInteger.valueOf(3), EllipticCurve.this.getField().getP()).add(
						EllipticCurve.this.getA().multiply(x)).add(
								EllipticCurve.this.getB()).mod(EllipticCurve.this.getField().getP());
	}

	public boolean isInfinitePoint(final Point p){
		return p.accept(new PointVisitor() {
			@Override
			public boolean handleNPoint(final NPoint p) {
				return false;
			}
			@Override
			public boolean handleInfinitePoint(final InfinitePoint p) {
				return true;
			}
		});
	}
	
	public String toString(){
		return "y^2=x^3+"+this.getA()+"x+"+this.getB() + "Z"+this.getField().getP();
	}
	
	public static EllipticCurve parse(String s) throws EccFormatException{
		String[] zSplit = s.split("Z");
		if(zSplit.length != 2) throw new EccFormatException();
		BigInteger p = new BigInteger(zSplit[1].trim());
		String[] plusSplit = zSplit[0].split("\\+");
		if(plusSplit.length != 3) throw new EccFormatException();
		BigInteger a = new BigInteger(plusSplit[1].substring(0, plusSplit[1].length() - 1));
		BigInteger b = new BigInteger(plusSplit[2]);
		return new EllipticCurve(new Field(p), a, b);
	}
}
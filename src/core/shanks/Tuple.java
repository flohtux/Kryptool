package core.shanks;

import java.math.BigInteger;

public class Tuple implements Comparable<Tuple>{
	
	private final BigInteger b;
	private final BigInteger a;

	Tuple(BigInteger a, BigInteger b) {
		this.a = a;
		this.b = b;
	}

	public BigInteger getB() {
		return b;
	}

	public BigInteger getA() {
		return a;
	}

	@Override
	public int compareTo(Tuple o) {
		return this.getB().compareTo(o.getB());
	}
	
	@Override
	public String toString() {
		return "("+getA()+","+ getB()+")";
	}
}
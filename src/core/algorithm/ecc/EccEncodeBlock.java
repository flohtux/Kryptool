package core.algorithm.ecc;

import java.math.BigInteger;

public class EccEncodeBlock {

	private final BigInteger m1;
	private final BigInteger m2;
	
	public EccEncodeBlock(BigInteger m1,BigInteger m2){
		this.m1 = m1;
		this.m2 = m2;
	}
	public BigInteger getM1() {
		return m1;
	}
	public BigInteger getM2() {
		return m2;
	}
}

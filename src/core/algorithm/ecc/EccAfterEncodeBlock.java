package core.algorithm.ecc;

import java.math.BigInteger;

public class EccAfterEncodeBlock extends EccEncodeBlock {
	
	private final Point a;
	
	public EccAfterEncodeBlock(BigInteger m1, BigInteger m2, Point a) {
		super(m1, m2);
		this.a = a;
	}

	public Point getA() {
		return a;
	}

}

package core.algorithm.ecc;
import java.math.BigInteger;

import common.Tuple;


public class NPoint implements Point{
	
	private final BigInteger x;
	private final BigInteger y;
	
	public NPoint(BigInteger x, BigInteger y){
		this.x = x;
		this.y = y;
	}

	public BigInteger getX() {
		return x;
	}

	public BigInteger getY() {
		return y;
	}

	@Override
	public boolean accept(PointVisitor v) {
		return v.handleNPoint(this);
	}
	
	public String toString(){
		return "("+this.getX()+","+this.getY()+")";
	}

	@Override
	public Tuple<byte[], byte[]> toBytes() {
		byte[] x = this.getX().toByteArray();
		byte[] y = this.getY().toByteArray();
		int xx = x.length;
		int yy= y.length;
		return new Tuple<byte[], byte[]>(x, y);
	}

}

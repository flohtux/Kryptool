package core.algorithm.ecc;
import java.math.BigInteger;


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
	public byte[] toBytes() {
		byte[] xA = this.getX().toByteArray();
		byte[] yA = this.getY().toByteArray();
		byte[] b = new byte[xA.length + yA.length];
		System.arraycopy(xA, 0, b, 0, xA.length);
		System.arraycopy(yA, 0, b, xA.length, yA.length);
		return b;
	}

}

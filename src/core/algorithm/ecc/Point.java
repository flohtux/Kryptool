package core.algorithm.ecc;
public interface Point {

	public boolean accept(PointVisitor v);

	public byte[] toBytes();
		
}

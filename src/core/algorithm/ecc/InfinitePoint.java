package core.algorithm.ecc;
public class InfinitePoint implements Point{

	@Override
	public boolean accept(PointVisitor v) {
		return v.handleInfinitePoint(this);
	}
	
	public String toString(){
		return "(Infinite Point)";
	}

	@Override
	public byte[] toBytes() {
		byte[] b = {0};
		return b;
	}

}

package core.algorithm.ecc;

import common.Tuple;

public class InfinitePoint implements Point{

	@Override
	public boolean accept(PointVisitor v) {
		return v.handleInfinitePoint(this);
	}
	
	public String toString(){
		return "(Infinite Point)";
	}

	@Override
	public Tuple<byte[], byte[]> toBytes() {
		byte[] x = {-1};
		byte[] y = {-1};
		return new Tuple<byte[], byte[]>(x, y);
	}

}

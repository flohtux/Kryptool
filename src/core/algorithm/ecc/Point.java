package core.algorithm.ecc;

import common.Tuple;

public interface Point {

	public boolean accept(PointVisitor v);

	public Tuple<byte[], byte[]> toBytes();	
		
}

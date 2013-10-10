package core.algorithm.ecc;

import java.math.BigInteger;

import common.Tuple;

public class PointUtil {

	public static Point parse(String s) throws PointFormatException{
		if(s.equals("(Infinite Point)")){
			return new InfinitePoint();
		}else{
			String[] komSplit = s.split(",");
			if(komSplit.length != 2) throw new PointFormatException();
			BigInteger x = new BigInteger(komSplit[0].substring(1));
			BigInteger y = new BigInteger(komSplit[1].substring(0,komSplit[1].length() - 1));
			return new NPoint(x, y);
		}
	}
	
	public static Point fromByteTuple(Tuple<byte[], byte[]> input){
		BigInteger x = new BigInteger(input.getFirst());
		BigInteger y = new BigInteger(input.getSecond());
		if(x.equals(BigInteger.valueOf(-1)) && y.equals(BigInteger.valueOf(-1))) return new InfinitePoint();
		return new NPoint(x, y);
	}
}

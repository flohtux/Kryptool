package core.algorithm.ecc;

import java.math.BigInteger;

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
}

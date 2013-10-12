package core.shanks;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import org.junit.experimental.theories.internal.ParameterizedAssertionError;

public class ShanksAlgorithm {

	/**
	 * Calculates the discrete Logarithm log_g(b) in Z_p
	 * == solves g^x = b (mod p).
	 * @param g primitive root of Z_p
	 * @param b element from Z_p*
	 * @param p prime, group definer
	 * @return log_g(b)
	 */
	public static Triple<BigInteger, List<Tuple>, List<Tuple>> discreteLog(BigInteger g, BigInteger b, BigInteger p) {
		BigInteger m = sqrtCeil(p.subtract(BigInteger.ONE));
		List<Tuple> l1 = new ArrayList<>();
		for (BigInteger j = BigInteger.ZERO; j.compareTo(m) < 0; j = j.add(BigInteger.ONE)) { // j in {0 .. m-1}
			l1.add(new Tuple(j, g.modPow(m.multiply(j), p))); 	// add (j, g^(mj) mod p)
		}
//		Collections.sort(l1); // TODO Shanks - wozu sortieren?
		
		List<Tuple> l2 = new ArrayList<>();
		for (BigInteger i = BigInteger.ZERO; i.compareTo(m) < 0; i = i.add(BigInteger.ONE)) {  // i in {0 .. m-1}
			l2.add(new Tuple(i, b.multiply(g.modPow(p.subtract(BigInteger.ONE).subtract(i), p)).mod(p))); // add (i, bg^(p-1-i) mod p)
		}
//		Collections.sort(l2);
		
		for (Tuple elem1 : l1) {
			for (Tuple elem2 : l2) {
				if (elem1.getB().compareTo(elem2.getB()) == 0) { // y == y
					return new Triple<> (m.multiply(elem1.getA()).add(elem2.getA()).mod(p.subtract(BigInteger.ONE)), l1, l2);  // m*j+i (mod p-1)
				}
			}
		}
		
		throw new InvalidParameterException("Der Shanks-Algorithmus funktioniert nur bei geeigneten Parametern! siehe Kommentar");
	}
	/**
	 * Calculates ceil(√n).
	 * @param n
	 * @return ceil(√n)
	 */
	public static BigInteger sqrtCeil(BigInteger n) {
		BigInteger a = BigInteger.ONE;
		BigInteger b = n;
		while(b.compareTo(a) >= 0) {				 // while: b >= a
			BigInteger mid = a.add(b).shiftRight(1); //  mid = (a+b)/2
			if(mid.multiply(mid).compareTo(n) >= 0)  //  if: mid² >= n
				b = mid.subtract(BigInteger.ONE);    //   then: b = mid - 1
			else a = mid.add(BigInteger.ONE);	     //   else: a = mid + 1
		}
		  		return a;
	}
	
	public static BigInteger parseStringToBigInteger(String text) throws java.text.ParseException {
		try {
			BigInteger result;
			if (text.contains("^")){
				int index = text.indexOf("^");
				result = new BigInteger(text.substring(0, index)).pow(Integer.parseInt(text.substring(index+1)));
			}else {
				result = new BigInteger(text);
			}
			return result;
		} catch (NumberFormatException e) {
			throw new ParseException(e.getMessage(), 0);
		}
	}
	
	
}




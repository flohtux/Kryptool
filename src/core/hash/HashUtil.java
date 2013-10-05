package core.hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import core.util.PosBigInt;
//TODO Problematik negativer Hash und getByte alternative
public class HashUtil {
	
	public static PosBigInt hashByMD5(final String text) throws NoSuchAlgorithmException{
		final MessageDigest md5 = MessageDigest.getInstance("md5");
		final byte[] byteTextHased = md5.digest(text.getBytes());
		BigInteger result = new BigInteger(byteTextHased);
		return PosBigInt.create(result.abs());
	}
	
	public static PosBigInt hashBysha(final String text) throws NoSuchAlgorithmException{
		final MessageDigest md5 = MessageDigest.getInstance("sha");
		final byte[] byteTextHased = md5.digest(text.getBytes());
		BigInteger result = new BigInteger(byteTextHased);
		return PosBigInt.create(result.abs());
	}
	
}

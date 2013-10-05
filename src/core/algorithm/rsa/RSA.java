package core.algorithm.rsa;

import java.util.Random;

import Whistle.ELearnConsts;
import Whistle.WhistleLevel;
import Whistle.Whistleblower;

import core.exception.NotInvertibleException;
import core.key.BigPair;
import core.util.MathUtil;
import core.util.PosBigInt;

/**
 * Class for all RSA specific functions
 * @author Jonas Tangermann
 *
 */
public class RSA {

	
	/*--------------------------------------------------- Key generation ----------------------------------------------*/
	
	/**
	 * Generates MainModul
	 * @param primeOne
	 * @param primeTwo
	 * @return
	 */
	public static PosBigInt generateMainModul(final PosBigInt primeOne, final PosBigInt primeTwo){
		PosBigInt result = primeOne.multiply(primeTwo);
		Whistleblower.whistle(ELearnConsts.MAINMODUL_GEN + result, WhistleLevel.E_LEARN);
		return result;

	}

	/**
	 * Returns the paramodul for the given mainmodul.
	 * @param mailModul
	 * @return
	 * @throws PrimeFactorisationException
	 */
	public static PosBigInt generateParaModul(final PosBigInt primeOne, final PosBigInt primeTwo){
		PosBigInt result = (primeOne.subtract(PosBigInt.ONE)).multiply(primeTwo.subtract(PosBigInt.ONE));
		Whistleblower.whistle(ELearnConsts.PHI_CALC + result, WhistleLevel.E_LEARN);
		return result;
	}

	/**
	 * Returns the decoding exponent for the given paramodul and encoding exponent.
	 * @param paraModul
	 * @param encodeExponent
	 * @return
	 * @throws NotInvertibleException 
	 * @throws Exception 
	 */
	private static PosBigInt generateDecodeExponent(final PosBigInt paraModul, final PosBigInt encodeExponent) throws NotInvertibleException{
		PosBigInt result = encodeExponent.getInverse(paraModul);
		Whistleblower.whistle(ELearnConsts.PRIVATEKEY_GEN + result, WhistleLevel.E_LEARN);
		return result;

	}
	
	/**
	 * Generates the encode exponent.
	 * @param paraModul
	 * @return
	 * @throws Exception
	 */
	private static PosBigInt generateEnCodeExponent(final PosBigInt paraModul){
		final Random r = new Random();
		int num = r.nextInt() % 100;
		if(num < 0) num = num * -1;
		while(!paraModul.gcd(PosBigInt.create(num)).equals(PosBigInt.ONE)){
			num++;
		}
		Whistleblower.whistle(ELearnConsts.PUBLICKEY_GEN + num, WhistleLevel.E_LEARN);
		return PosBigInt.create(num);
	}
	
	/**
	 * Generates decode and encode exponents.
	 * @param mainModul
	 * @param paraModul
	 * @return
	 * @throws NotInvertibleException 
	 */
	public static BigPair generateEnCodeAndDecodeExponents(final PosBigInt mainModul, final PosBigInt paraModul) throws NotInvertibleException{
		final PosBigInt encode = RSA.generateEnCodeExponent(paraModul);
		final PosBigInt decode = RSA.generateDecodeExponent(paraModul, encode);
		return new BigPair(encode,decode);
	}
	
	/*------------------------------------------------------------------- Encode and Decode ----------------------------------------------------*/
	
	/**
	 * Encodes {@link PosBigInt} with RSA.
	 * @param cr
	 * @param mainModul
	 * @param enCodeExponent
	 * @return
	 */
	public static PosBigInt enCode(final PosBigInt number, final PosBigInt mainModul, final PosBigInt enCodeExponent){
		PosBigInt result = MathUtil.powerByBinExpMod(number, enCodeExponent, mainModul);
		Whistleblower.whistle(ELearnConsts.ENCODE + result, WhistleLevel.E_LEARN);
		return result;

	}
	
	/**
	 * Decodes a {@link PosBigInt} with RSA.
	 * @param cr
	 * @param mainModul
	 * @param deCodeExponent
	 * @return
	 */
	public static PosBigInt deCode(final PosBigInt number, final PosBigInt mainModul, final PosBigInt deCodeExponent){
		PosBigInt result = MathUtil.powerByBinExpMod(number, deCodeExponent, mainModul);
		Whistleblower.whistle(ELearnConsts.DECODE + result, WhistleLevel.E_LEARN);
		return result;
	}
	
}
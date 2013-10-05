package core.algorithm.rsa;

import java.math.BigInteger;
import java.util.Vector;

import Util.Progresser;
import Whistle.ELearnConsts;
import Whistle.InfoConsts;
import Whistle.WhistleLevel;
import Whistle.Whistleblower;


import core.alphabet.Alphabet;
import core.key.BigPair;
import core.key.KeyPairRSA;
import core.key.PrivateKeyRSA;
import core.key.PublicKeyRSA;
import core.util.MathUtil;
import core.util.PosBigInt;

/**
 * This class is to be used for all RSA calls.
 * @author Jonas Tangermann
 *
 */
public class RSAFacade {
	
	/**
	 * Returns a KeyPair which contains the public and private key.
	 * The keys are randomly generatet with two primes that match nearly the bit length of <code>level</code>.
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public static KeyPairRSA generateKeys(final int level) throws Exception{
		PosBigInt primeOne = MathUtil.generateProbablePrime(level,10);
		PosBigInt primeTwo = MathUtil.generateProbablePrime(level,10);
		while(primeOne.equals(primeTwo)){
			primeOne = MathUtil.generateProbablePrime(level,10);
			primeTwo = MathUtil.generateProbablePrime(level,10);
			Whistleblower.whistle(InfoConsts.NEW_PRIME_CHOOSE, WhistleLevel.INFO);
		}
		Whistleblower.whistle(ELearnConsts.TWO_PRIMES + primeOne+", "+ primeTwo +"; bitlenght: "+primeOne.bitLength(), WhistleLevel.E_LEARN);
		final PosBigInt mainModul = RSA.generateMainModul(primeOne, primeTwo);
		final PosBigInt paraModul = RSA.generateParaModul(primeOne, primeTwo);
		final BigPair bp = RSA.generateEnCodeAndDecodeExponents(mainModul, paraModul);
		final PrivateKeyRSA prk = new PrivateKeyRSA(mainModul, bp.getSecond());
		final PublicKeyRSA puk = new PublicKeyRSA(mainModul, bp.getFirst());
		final KeyPairRSA kp = new KeyPairRSA(puk, prk);
		return kp;
	}

	/**
	 * This Operation is used to print a {@link KeyPairRSA}.
	 * @param kp
	 * @return
	 */
	public static Vector<String> keysToView(final KeyPairRSA kp) {
		final Vector<String> result = new Vector<String>();
		result.add(kp.getPublicKey().enCodeExponentView());
		result.add(kp.getPrivateKey().deCodeExponentView());
		return result;
	}

	/**
	 * Operation to encode with RSA.
	 * @param text cleartext to be encoded
	 * @param puk public-key
	 * @param messageBlockSize size of composite chars to be encoded together.
	 * @param prg {@link Progresser} to monitor the progress.
	 * @param asciiCipher if true, the output is also blockciphered.
	 * @return the ciphertext
	 * @throws Exception
	 */
	public static String enCode(Alphabet alphabet, String text, PublicKeyRSA puk, int messageBlockSize, int cipherBlockSize, Progresser prg, boolean asciiCipher) throws Exception {
		if (asciiCipher) {
			return RSAStringUtil.enCodeString(alphabet, text, puk, messageBlockSize, cipherBlockSize, prg);
		} else {
			return RSAStringUtil.enCodeStringWithoutCipher(alphabet, text, puk, messageBlockSize, cipherBlockSize, prg);
		}
		
	}
	
	public static PosBigInt sign(final PosBigInt value, final PrivateKeyRSA prk) throws Exception{
		if(value.greater(prk.getMainModul())) throw new Exception("Mainmodul muss grš§er sein als Hashwert!");
		final PosBigInt resultInt = RSA.deCode(value, prk.getMainModul(), prk.getDecodeExponent());
		return resultInt;
	}
	
	public static boolean verify(final PosBigInt message, final PosBigInt sign, final PublicKeyRSA puk){
		final PosBigInt signMessage = RSA.enCode(sign, puk.getMainModul(), puk.getEncodeExponent());
		return message.equals(signMessage);
	}

	/**
	 * Operation to decode with RSA.
	 * @param alphabet
	 * @param text, ciphertext to be decoded.
	 * @param prk private Key
	 * @param messageBlockSize
	 * @param cipherBlockSize
	 * @param prg to monitor progress
	 * @return
	 * @throws Exception
	 */
	public static String deCode(Alphabet alphabet, String text, PrivateKeyRSA prk,
			int messageBlockSize, int cipherBlockSize, Progresser prg) throws Exception {
		return RSAStringUtil.deCodeString(alphabet, text, prk, messageBlockSize, cipherBlockSize, prg);
	}
	
	/**
	 * Calculates the optimal blocksize for the chiffre text.
	 * @param mainmodul
	 * @param alphabet
	 * @return
	 */
	public static int getOptimalChiffreBlocksize(PosBigInt mainmodul, Alphabet alphabet){
		int result = 2;
		while(alphabet.getMaxValue().pow(result).less(mainmodul)){
			result++;
		}
		return result;
	}
	
	/**
	 * Calculates the max blocksize
	 * @param mainmodul
	 * @param alphabet
	 * @return
	 */
	public static int getMaxCleartextBlocksize(PosBigInt mainmodul, Alphabet alphabet){
		int result = getOptimalChiffreBlocksize(mainmodul, alphabet);
		while(alphabet.getMaxValue().pow(result).greater(mainmodul)){
			result--;
		}
		return result;
	}

}

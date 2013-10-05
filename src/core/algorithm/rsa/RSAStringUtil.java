package core.algorithm.rsa;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Util.Progresser;


import core.alphabet.Alphabet;
import core.key.PrivateKeyRSA;
import core.key.PublicKeyRSA;
import core.util.Gšdelizer;
import core.util.PosBigInt;
import core.util.StringUtil;

/**
 * Interface for handling strings with the RSA.
 *
 */
public class RSAStringUtil {

	private static List<PosBigInt> enCodeStringToNumbers(Alphabet alphabet, String messageText,
			PublicKeyRSA puk, int messageBlockSize, int cipherBlockSize, Progresser prg) throws Exception {
		List<PosBigInt> result = new LinkedList<PosBigInt>();
		Gšdelizer gšdel = new Gšdelizer(alphabet);
		
		List<String> charBlocks = StringUtil.stringIntoBlocks(messageText, messageBlockSize, alphabet);
		List<PosBigInt> numberBlocks = gšdel.multipleCharBlocksToInt(charBlocks, messageBlockSize);
		
		Iterator<PosBigInt> i = numberBlocks.iterator();
		while (i.hasNext()) {
			PosBigInt current = i.next();

			if(current.greater(puk.getMainModul())) throw new EnCodeException(RSAConsts.NUMBER_HIGHER_MAINMODUL);
			PosBigInt currentResult = RSA.enCode(current, puk.getMainModul(), puk.getEncodeExponent());

			result.add(currentResult);
			prg.step(numberBlocks.size());
			if(Thread.currentThread().isInterrupted()) throw new InterruptedException();
		}
		
		return result;
	}
	/**
	 * Encodes the given messageText with RSA and returns the block-
	 * ciphered output.
	 * @param alphabet
	 * @param messageText
	 * @param puk
	 * @param messageBlockSize
	 * @param cipherBlockSize
	 * @return
	 * @throws Exception if blockSize is zero
	 */
	public static String enCodeString(Alphabet alphabet, String messageText,PublicKeyRSA puk, int messageBlockSize, int cipherBlockSize, Progresser prg) throws Exception {
		String result = "";
		Gšdelizer gšdel = new Gšdelizer(alphabet);
		List<PosBigInt> cipherNumbers = enCodeStringToNumbers(alphabet, messageText, puk, messageBlockSize, cipherBlockSize, prg);
		List<String> cipherText = gšdel.multipleIntToCharBlocks(cipherNumbers, cipherBlockSize);
		for (String current : cipherText) {
			result += current;
		}

		return result;
	}
	/**
	 * Encodes the given messageText with RSA, and displays the
	 * output as raw numbers.
	 * @param alphabet
	 * @param messageText
	 * @param puk
	 * @param messageBlockSize
	 * @param cipherBlockSize
	 * @return
	 * @throws Exception if blockSize is zero
	 */
	public static String enCodeStringWithoutCipher(Alphabet alphabet, String messageText, PublicKeyRSA puk, int messageBlockSize, int cipherBlockSize, Progresser prg) throws Exception {
		String result = "";
		List<PosBigInt> cipherNumbers = enCodeStringToNumbers(alphabet, messageText, puk, messageBlockSize, cipherBlockSize, prg);
		for (PosBigInt current : cipherNumbers) {
			result += current + " ";
		}
		
		return result;
	}
	
	public static String deCodeNumberString(Alphabet alphabet, String cipherText, PrivateKeyRSA prk, int messageBlockSize, int cipherBlockSize, Progresser prg) throws InterruptedException {
		String result = "";	
		Gšdelizer gšdel = new Gšdelizer(alphabet);
		List<PosBigInt> cipherNumberBlocks = StringUtil.parsePosIntString(cipherText, ' ');
		Iterator<PosBigInt> i = cipherNumberBlocks.iterator();
		while (i.hasNext()) {
			PosBigInt current = i.next();
			PosBigInt currentMessage = RSA.deCode(current, prk.getMainModul(), prk.getDecodeExponent());	
			String currentMessageString = gšdel.singleIntToCharBlock(currentMessage, messageBlockSize);
			result += currentMessageString;
			prg.step(cipherNumberBlocks.size());
			if(Thread.currentThread().isInterrupted()) throw new InterruptedException();
		}
		
		return result;
	}
	/**
	 * Decodes the given cipherText with RSA
	 * @param alphabet
	 * @param cipherText
	 * @param prk
	 * @param messageBlockSize
	 * @param cipherBlockSize
	 * @return
	 * @throws Exception
	 */
	public static String deCodeString(Alphabet alphabet, String cipherText,
			PrivateKeyRSA prk, int messageBlockSize, int cipherBlockSize, Progresser prg) throws Exception {

		PublicKeyRSA encodeExp = new PublicKeyRSA(prk.getMainModul(), prk.getDecodeExponent());
		
		return enCodeString(alphabet, cipherText, encodeExp, cipherBlockSize, messageBlockSize, prg);
	}

}

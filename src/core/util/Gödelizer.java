package core.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import core.alphabet.Alphabet;
import core.exception.TooSmallBlockSizeException;



/**
 * Klasse k√ºmmert sich um die Blockchiffrierung.
 *
 */
public class Gödelizer {

	private final Alphabet alphabet;	
	
	public Gödelizer(Alphabet alphabet) {
		this.alphabet = alphabet;
	}
	
	/**
	 * Returns the given list with all entry's converted to numbers.
	 * Expects blocks of exact length of "blockSize".
	 * @param textBlocks
	 * @return list of bijective numbers.
	 */
	public List<PosBigInt> multipleCharBlocksToInt(List<String> textBlocks, int blockSize){
		List<PosBigInt> result = new LinkedList<PosBigInt>();
		Iterator<String> i = textBlocks.iterator();
		while (i.hasNext()) {
			String current = i.next();
			
			PosBigInt bijectionNumber = this.singleCharBlockToInt(current, blockSize);
			result.add(bijectionNumber);
		}
		return result;
	}
	
	/**
	 * Returns the bijective number of the character block in the alphabet.
	 * Expects blocks of exact length of "blockSize".
	 * @param charBlock
	 * @return correspondent number value
	 */
	public PosBigInt singleCharBlockToInt(String charBlock, int blockSize) {
		PosBigInt result = PosBigInt.ZERO;
		
		for (int i = 0; i < blockSize; i++) {
			Character currentChar = charBlock.charAt(i);
			PosBigInt currentInt = PosBigInt.create(this.alphabet.singleCharToInt(currentChar));
			
			Integer exponent = blockSize - i - 1;
			PosBigInt factor = this.alphabet.getMaxValue().pow(exponent);
			result = result.add(currentInt.multiply(factor));		
		}
		
		return result;
	}
	
	/**
	 * Returns the given list with all entry's converted to text blocks.
	 * Expects blocks of exact length of "blockSize".
	 * @param intValues
	 * @return list of bijective numbers.
	 */
	public List<String> multipleIntToCharBlocks(List<PosBigInt> intValues, int blockSize){
		List<String> result = new LinkedList<String>();
		Iterator<PosBigInt> i = intValues.iterator();
		while (i.hasNext()) {
			PosBigInt current = i.next();
			
			String bijectionText = this.singleIntToCharBlock(current, blockSize);
			result.add(bijectionText);
		}
		return result;
	}
	
	/**
	 * Returns the bijective text of the number in the given alphabet.
	 * Expects blocks of exact length of "blockSize".
	 * @param current
	 * @return correspondent text block
	 */
	public String singleIntToCharBlock(PosBigInt current, int blockSize) throws TooSmallBlockSizeException {
		PosBigInt currentNumber = current;
		String result = "";
		
		for (int i = 0; i < blockSize; i++) {
			PosBigInt[] remainder_and_value = currentNumber.divideAndRemainder(this.alphabet.getMaxValue());
			currentNumber = remainder_and_value[0];		  // result of (currentNumber / NUMBERSYSTEM)
			PosBigInt remainder = remainder_and_value[1];// result of (currentNumber % NUMBERSYSTEM)
			result = this.alphabet.singleIntToChar(remainder.intValue()) + result;		
		}
		if (!currentNumber.isZero()) {
			throw new TooSmallBlockSizeException(blockSize, current);
		}
		
		return result;
	}

}
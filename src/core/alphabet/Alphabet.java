package core.alphabet;

import core.util.PosBigInt;

/**
 * An alphabet is a bijective mapping of character <--> number.
 * @author florian
 *
 */
public interface Alphabet {

	/**
	 * Returns the maximum value, which can be represented by
	 * a character in the given alphabet.
	 * @return maximum character value
	 */
	public abstract PosBigInt getMaxValue();
	
	/**
	 * Returns the correspondent number for the given letter.
	 * @param letter letter to be converted
	 * @return the correspondent (positive) number
	 */
	public abstract int singleCharToInt(char letter);
	
	/**
	 * Returns the correspondent letter for the given number.
	 * @param currentLetterNumber number to be converted
	 * @return the correspondent letter
	 */
	public abstract Character singleIntToChar(int currentLetterNumber);
	
	/**
	 * Returns the character to be used as "fill-character" at
	 * the end of the last block.
	 */
	public abstract Character fillCharacter();
}

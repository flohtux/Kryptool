package core.alphabet;

import java.util.HashMap;

import core.exception.UnrecognizedCharacterException;
import core.util.PosBigInt;


/**
 * This alphabet contains the 26 capital letters of the common
 * alphabet plus several special characters and represents its
 * number mapping from 0..46.
 * @author florian
 *
 */
public class Alphabet47 implements Alphabet {

	/** Holds the mapping character --> number. */
	private final HashMap<Character, Integer> bijectionCharToInt = new HashMap<Character, Integer>();
	/** Holds the mapping number --> character. */
	private final HashMap<Integer, Character> bijectionIntZuChar = new HashMap<Integer, Character>();
	
	public Alphabet47() {
		initBijection();
	}
	
	/**
	 * Initializes the HashMaps, which represent the alphabet.
	 */
	private void initBijection() {
		Integer currentIndex = 0;
		for (Character c : "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.,:;-!?\"() ".toCharArray()) {
			bijectionCharToInt.put(c, currentIndex);
			bijectionIntZuChar.put(currentIndex, c);
			currentIndex ++;
		}
	}

	@Override
	public Character singleIntToChar(int currentLetterNumber) throws UnrecognizedCharacterException {
		if (bijectionIntZuChar.containsKey(currentLetterNumber)) {
			return bijectionIntZuChar.get(currentLetterNumber);
		} else {
			throw new UnrecognizedCharacterException(currentLetterNumber);
		}
	}
	
	@Override
	public int singleCharToInt(char letter) throws UnrecognizedCharacterException {
		if (bijectionCharToInt.containsKey(letter)) {
			return bijectionCharToInt.get(letter);
		} else {
			throw new UnrecognizedCharacterException(letter);
		}
	}

	@Override
	public PosBigInt getMaxValue() {
		return PosBigInt.create(47);
	}

	@Override
	public Character fillCharacter() {
		return ' ';
	}

}

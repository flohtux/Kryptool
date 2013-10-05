package core.alphabet;

import core.util.PosBigInt;


public class AlphabetBig implements Alphabet {

	@Override
	public PosBigInt getMaxValue() {
		return PosBigInt.create(Character.MAX_VALUE);
	}

	@Override
	public int singleCharToInt(char letter) {
		return Integer.valueOf(letter);
	}

	@Override
	public Character singleIntToChar(int currentLetterNumber) {
		Character result = (char) currentLetterNumber;
//		if (result.equals(this.fillCharacter())) {
//			return "";
//		} else {
//			return result.toString();
//		}
		return result;
	}

	@Override
	public Character fillCharacter() {
		return (char) 0;
	}

}

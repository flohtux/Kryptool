package core.alphabet;

import core.exception.UnrecognizedCharacterException;
import core.util.PosBigInt;

public class AlphabetBasicLatin implements Alphabet {

	private final PosBigInt maxValue = PosBigInt.create(255);
	private final Character fill = (char) 0;
	
	@Override
	public PosBigInt getMaxValue() {
		return this.maxValue;
	}

	@Override
	public int singleCharToInt(char letter) {
		switch (letter) {
		case '�':
			return 2;
		case '�':
			return 3;
		case '�':
			return 4;
		case '�':
			return 5;
		case '�':
			return 6;
		default:
			if(letter > this.maxValue.intValue()) throw new UnrecognizedCharacterException(letter);
			return (int)letter;
		}
	}

	@Override
	public Character singleIntToChar(int currentLetterNumber) {
		switch (currentLetterNumber) {
		case 2:
			return '�';
		case 3:
			return '�';
		case 4:
			return '�';
		case 5:
			return '�';
		case 6:
			return '�';
		default:
			if(currentLetterNumber > this.maxValue.intValue()) throw new UnrecognizedCharacterException((char)currentLetterNumber);
			return (char)currentLetterNumber;
		}
	}

	@Override
	public Character fillCharacter() {
		return this.fill;
	}

}

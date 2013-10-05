package core.exception;

/**
 * This exception is thrown, if character or number, which cannot
 * be represented in the current alphabet is being converted.
 * @author florian
 *
 */
public class UnrecognizedCharacterException extends RuntimeException {

	public UnrecognizedCharacterException(char letter) {
		super("Der Character <"+letter+"> kann nicht dargestellt werden!");
	}
	
	public UnrecognizedCharacterException(int number) {
		super("Der Character <"+number+"> kann nicht dargestellt werden!");
	}
}

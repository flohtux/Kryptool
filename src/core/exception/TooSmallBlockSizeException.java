package core.exception;

import core.util.PosBigInt;

/**
 * This exception is thrown if the blocksize is not big enough to
 * display the full number.
 * @author florian
 *
 */
public class TooSmallBlockSizeException extends RuntimeException {

	public TooSmallBlockSizeException(int blockSize, PosBigInt number) {
		super("Die gewählte Blockgröße <"+blockSize+"> reicht nicht aus, um die Zahl <" + number + "> darzustellen.");
	}
}

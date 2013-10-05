package core.key;

import core.util.PosBigInt;


/**
 * Composite of to {@link PosBigInt}.
 * @author Jonas Tangermann
 *
 */
public class BigPair {
	
	private final PosBigInt first;
	private final PosBigInt second;
	
	/**
	 * Creates a LongPair of <code>first</code> and <code>second</code>.
	 * @param first
	 * @param second
	 */
	public BigPair(final PosBigInt first, final PosBigInt second){
		this.first = first;
		this.second = second;
	}

	/**
	 * @return the first
	 */
	public PosBigInt getFirst() {
		return this.first;
	}

	/**
	 * @return the second
	 */
	public PosBigInt getSecond() {
		return this.second;
	}
}

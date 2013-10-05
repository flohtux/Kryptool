package common;

/**
 * A Class which provides generic Tuples
 */
public class Tuple<X, Y> {

	private final X first;

	private final Y second;

	public Tuple(final X first, final Y second) {
		super();
		this.first = first;
		this.second = second;
	}

	public X getFirst() {
		return first;
	}

	public Y getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "(" + getFirst().toString() + ", " + getSecond().toString() + ")";
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Tuple) {
			return equals((Tuple<X, Y>)obj);
		}
		return false;
	}

	private boolean equals(final Tuple<X, Y> other) {
		return getFirst().equals(other.getFirst()) && getSecond().equals(other.getSecond());
	}

}

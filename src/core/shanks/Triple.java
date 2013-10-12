package core.shanks;

public class Triple<A,B,C> {
	
	private final B b;
	private final A a;
	private final C c;

	Triple(A a, B b, C c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public B getB() {
		return b;
	}

	public A getA() {
		return a;
	}
	
	@Override
	public String toString() {
		return "("+getA()+","+ getB()+","+getC()+")";
	}

	public C getC() {
		return c;
	}
}
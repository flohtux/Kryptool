package core.algorithm.ecc;
import java.math.BigInteger;


public class Field {
	
	private final BigInteger p;
	
	public Field(BigInteger p){
		this.p = p;
	}

	public BigInteger getP() {
		return p;
	}

}

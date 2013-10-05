package core.key;

import java.math.BigInteger;
import java.util.Vector;

import Util.Tuple;

public class PrivateKeyEcc extends Key{
	
	private final BigInteger x;
	
	public PrivateKeyEcc(BigInteger x){
		this.x = x;
	}

	public BigInteger getX(){
		return this.x;
	}

	@Override
	public Vector<Tuple<String, String>> getKeyValuePairs() {
		Vector<Tuple<String, String>> res = new Vector<Tuple<String,String>>();
		res.add(new Tuple<String, String>("x", this.getX().toString()));
		return res;
	}
	
	public String toString(){
		return "PrivateKey: x = "+this.getX().toString();
	}
}

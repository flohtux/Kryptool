package core.key;

import java.math.BigInteger;
import java.util.Vector;

import Util.Tuple;

import core.algorithm.ecc.EllipticCurve;
import core.algorithm.ecc.Point;

public class PublicKeyEcc extends Key{
	
	private final EllipticCurve E;
	private final BigInteger p;
	private final Point g;
	private final Point y;
	
	public PublicKeyEcc(EllipticCurve E,BigInteger p, Point g, Point y){
		this.E = E;
		this.p = p;
		this.g = g;
		this.y = y;
	}

	public EllipticCurve getE() {
		return this.E;
	}

	public BigInteger getP() {
		return this.p;
	}

	public Point getG() {
		return this.g;
	}

	public Point getY() {
		return this.y;
	}

	@Override
	public Vector<Tuple<String, String>> getKeyValuePairs() {
		Vector<Tuple<String, String>> res = new Vector<Tuple<String,String>>();
		res.add(new Tuple<String, String>("E", this.getE().toString()));
		res.add(new Tuple<String, String>("p", this.getP().toString()));
		res.add(new Tuple<String, String>("g", this.getG().toString()));
		res.add(new Tuple<String, String>("y", this.getY().toString()));
		return res;
	}
	
	public String toString(){
		return "PublicKey: E: "+this.getE() + " p=" +this.getP() +"g="+this.getG() +"y="+this.getY();
	}

}

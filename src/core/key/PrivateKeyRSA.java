package core.key;

import core.util.PosBigInt;


public class PrivateKeyRSA extends BigPair{

	public PrivateKeyRSA(final PosBigInt mainModul, final PosBigInt deCodeExponent) {
		super(mainModul, deCodeExponent);
	}
	public PosBigInt getMainModul(){
		return this.getFirst();
	}
	
	public PosBigInt getDecodeExponent(){
		return this.getSecond();
	}
	public String deCodeExponentView() {
		return "Decode exponent = " + this.getDecodeExponent();
	}
	//TODO unsauber weil nur rsa 
	@Override
	public String toString(){
		return this.deCodeExponentView();
	}
}

package core.key;

import core.util.PosBigInt;


public class PublicKeyRSA extends BigPair {

	public PublicKeyRSA(final PosBigInt mainModul, final PosBigInt enCodeExponent) {
		super(mainModul, enCodeExponent);
	}

	public PosBigInt getMainModul(){
		return this.getFirst();
	}
	
	public PosBigInt getEncodeExponent(){
		return this.getSecond();
	}

	public String enCodeExponentView() {
		return "Encode exponent: " + this.getEncodeExponent();
	}
	
	public String mainModulView() {
		return " Main modul: " + this.getMainModul() + " Bitlegth: " + this.getMainModul().bitLength();
	}
	//TODO unsauber weil nur rsa 

	@Override
	public String toString(){
		return this.enCodeExponentView() + this.mainModulView();
	}
}

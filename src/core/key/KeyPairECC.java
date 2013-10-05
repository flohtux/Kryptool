package core.key;

import java.util.Vector;

import Util.Tuple;

public class KeyPairECC extends Key{

	public final PrivateKeyEcc privateKey;
	public final PublicKeyEcc publicKey;

	public KeyPairECC(PrivateKeyEcc privateKey, PublicKeyEcc publicKey){
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public PrivateKeyEcc getPrivateKey() {
		return privateKey;
	}

	public PublicKeyEcc getPublicKey() {
		return publicKey;
	}

	@Override
	public Vector<Tuple<String, String>> getKeyValuePairs() {
		Vector<Tuple<String, String>> res = new Vector<Tuple<String,String>>();
		res.addAll(this.getPublicKey().getKeyValuePairs());
		res.addAll(this.getPrivateKey().getKeyValuePairs());
		return res;
	}
	
	public String toString(){
		return this.getPrivateKey().toString() + this.getPublicKey().toString();
	}
}

package core.key;


public class KeyPairRSA {
	
	private final PublicKeyRSA publicKey;
	private final PrivateKeyRSA privateKey;

	public KeyPairRSA(final PublicKeyRSA publicKey, final PrivateKeyRSA privateKey){
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public PrivateKeyRSA getPrivateKey() {
		return this.privateKey;
	}

	public PublicKeyRSA getPublicKey() {
		return this.publicKey;
	}
}

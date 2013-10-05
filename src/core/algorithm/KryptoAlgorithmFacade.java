package core.algorithm;

import java.security.NoSuchAlgorithmException;

import core.exception.NotInvertibleException;
import core.key.KeyPairRSA;
import core.util.PosBigInt;

public interface KryptoAlgorithmFacade {
	
	public String encode(String text) throws Exception;
	
	public String decode(String cipher) throws Exception;
	
	public PosBigInt sign(String text) throws NoSuchAlgorithmException;
	
	public boolean verify(String text, PosBigInt sign);
	
	public KeyPairRSA generateKeys() throws InterruptedException, NotInvertibleException;

}

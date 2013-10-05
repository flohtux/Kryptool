package core;

import java.security.NoSuchAlgorithmException;

import Util.Progresser;


import core.algorithm.EnumAlgorithms;
import core.algorithm.ecc.ECCFacade;
import core.algorithm.rsa.RSAFacade;
import core.alphabet.Alphabet;
import core.hash.EnumHashFunktions;
import core.hash.HashUtil;
import core.key.KeyPairECC;
import core.key.PrivateKeyRSA;
import core.key.PublicKeyEcc;
import core.key.PublicKeyRSA;
import core.util.PosBigInt;

public class KryptoFacade {
	 
	public static String enCodeWithRSA(Alphabet alphabet, String text, PublicKeyRSA puk, int messageBlockSize, int cipherBlockSize, Progresser prg, boolean asciiCipher) throws Exception {
		return RSAFacade.enCode(alphabet, text, puk, messageBlockSize, cipherBlockSize, prg, asciiCipher);
	}
	
	public static String deCodeWithRSA(Alphabet alphabet, String text, PrivateKeyRSA prk, int messageBlockSize, int cipherBlockSize, Progresser prg) throws Exception {
		return RSAFacade.deCode(alphabet, text, prk, messageBlockSize, cipherBlockSize, prg);
	}
	
	public static PosBigInt signWithRSA_MD5Hash(final String text,final PrivateKeyRSA prk) throws Exception{
		final PosBigInt hash = HashUtil.hashByMD5(text);
		return RSAFacade.sign(hash, prk);
	}
	
	public static boolean verifyRSA_MD5Hash(final String message,final PosBigInt sign, final PublicKeyRSA puk) throws NoSuchAlgorithmException{
		return RSAFacade.verify(HashUtil.hashByMD5(message), sign, puk);
	}

	public static EnumAlgorithms[] getAlgorithms() {
		return EnumAlgorithms.values();
	}

	public static EnumHashFunktions[] getHashFunktions() {
		return EnumHashFunktions.values();
	}

	public static boolean verifyRSA_SHAHash(final String text, final PosBigInt sign, final PublicKeyRSA publicKey) throws NoSuchAlgorithmException {
		return RSAFacade.verify(HashUtil.hashBysha(text), sign, publicKey);
	}

	public static PosBigInt signWithRSA_shaHash(final String text, final PrivateKeyRSA privateKey) throws Exception {
		final PosBigInt hash = HashUtil.hashBysha(text);
		return RSAFacade.sign(hash, privateKey);
	}

	public static String deCodeWithECC(Alphabet alphabet, String cliper, KeyPairECC eccKeys, Progresser prg) {
		return ECCFacade.deCode(alphabet,cliper,eccKeys,prg);
	}

	public static String enCodeWithECC(Alphabet alphabet, String text,PublicKeyEcc publicKey, Progresser prg, boolean useAsciiCliper) {
		return ECCFacade.enCode(alphabet,text,publicKey,prg, useAsciiCliper);
	}
}
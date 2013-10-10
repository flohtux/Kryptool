package gui;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import Util.Progresser;

//import overhead.distribute.ServerAddress;


import core.KryptoFacade;
import core.algorithm.EnumAlgorithms;
import core.algorithm.ecc.ECC;
import core.algorithm.ecc.ECCFileUtil;
import core.algorithm.rsa.RSAFacade;
import core.algorithm.rsa.RSAStringUtil;
import core.alphabet.Alphabet;
import core.alphabet.Alphabet26;
import core.alphabet.Alphabet47;
import core.alphabet.AlphabetBasicLatin;
import core.alphabet.AlphabetBig;
import core.alphabet.EnumAlphabet;
import core.hash.EnumHashFunktions;
import core.key.Key;
import core.key.KeyPairECC;
import core.key.KeyPairRSA;
import core.key.PrivateKeyRSA;
import core.key.PublicKeyRSA;
import core.util.PosBigInt;

public class GuiManager {

	//private final Vector<ServerAddress> servers;
	private boolean actAsServer = false;
	private boolean useDistribution = false;
	private int primeTests = 100;
	private int primeBitLenght = 250;
	private int serverPort;
	private EnumAlgorithms currentAlgo = EnumAlgorithms.RSA;
	private EnumHashFunktions currentHashFunk = EnumHashFunktions.SHA;
	private boolean useAsciiCliper = true;
	private final PosBigInt startPuKy = PosBigInt.create("53");
	private final PosBigInt startPrKy = PosBigInt.create("1352452836208806756792216100561953121004063723882589090646193077068298088845984303281856940888051108160783432428611353314894601614616605518044778580989852569820005617");
	private final PosBigInt startMainmodul = PosBigInt.create("2172121221789901760908710706963136830703496283811430963765097972261206021479914184188134242605667450152173058340324585941038968989732279696385279310170058203250398201");
	private KeyPairRSA kp = new KeyPairRSA(new PublicKeyRSA(startMainmodul, startPuKy), new PrivateKeyRSA(startMainmodul, startPrKy));
	private Alphabet alphabet = new AlphabetBig();
	private int chiffreBlocksize = RSAFacade.getOptimalChiffreBlocksize(startMainmodul, alphabet);
	private int blocksize = RSAFacade.getMaxCleartextBlocksize(startMainmodul, alphabet);

	private KeyPairECC eccKeys;

	public KeyPairECC getEccKeys() {
		return eccKeys;
	}

	public void setEccKeys(KeyPairECC eccKeys) {
		this.eccKeys = eccKeys;
	}

	private static GuiManager instance;

	public static GuiManager getInstance(){
		if(GuiManager.instance == null) GuiManager.instance = new GuiManager();
		return GuiManager.instance;
	}

	private GuiManager(){
		//this.servers = new Vector<ServerAddress>();
	}

	public static EnumAlgorithms[] getAlgorithms(){
		return KryptoFacade.getAlgorithms();
	}

	public static EnumAlphabet[] getAlphabets(){
		return EnumAlphabet.values();
	}

	public static EnumHashFunktions[] getHashFunktions() {
		return KryptoFacade.getHashFunktions();
	}

	/**
	 * @return the primeBitLenght
	 */
	public int getPrimeBitLenght() {
		return this.primeBitLenght;
	}

	/**
	 * @param primeBitLenght the primeBitLenght to set
	 */
	public void setPrimeBitLenght(final int primeBitLenght) {
		this.primeBitLenght = primeBitLenght;
	}

	/**
	 * @return the blocksize
	 */
	public int getBlocksize() {
		return this.blocksize;
	}

	/**
	 * @param blocksize the blocksize to set
	 */
	public void setBlocksize(final int blocksize) {
		this.blocksize = blocksize;
		this.chiffreBlocksize = RSAFacade.getOptimalChiffreBlocksize(this.getKp().getPrivateKey().getMainModul(), this.getAlphabet());
	}

	/**
	 * @return the primeTests
	 */
	public int getPrimeTests() {
		return this.primeTests;
	}

	/**
	 * @param primeTests the primeTests to set
	 */
	public void setPrimeTests(final int primeTests) {
		this.primeTests = primeTests;
	}

	/**
	 * @return the useDistribution
	 */
	public boolean isUseDistribution() {
		return this.useDistribution;
	}

	/**
	 * @param useDistribution the useDistribution to set
	 */
	public void setUseDistribution(final boolean useDistribution) {
		this.useDistribution = useDistribution;
	}

	/**
	 * @return the actAsServer
	 */
	public boolean isActAsServer() {
		return this.actAsServer;
	}

	/**
	 * @param actAsServer the actAsServer to set
	 */
	public void setActAsServer(final boolean actAsServer) {
		this.actAsServer = actAsServer;
	}

	/**
	 * @return the servers
	 */
	/*	public Vector<ServerAddress> getServers() {
		return this.servers;
	}

	public void addServer(final ServerAddress ad){
		this.getServers().add(ad);
	}

	public boolean removeServer(final String ip){
		final Iterator<ServerAddress> i = this.getServers().iterator();
		while(i.hasNext()){
			final ServerAddress current = i.next();
			if(current.getIp().equals(ip)) {
				i.remove();
				return true;
			}
		}
		return false;
	}
	 */
	public void setServerPort(final int parseInt) {
		this.serverPort = parseInt;
	}

	public int getServerPort(){
		return this.serverPort;
	}

	/**
	 * @return the currentAlgo
	 */
	public EnumAlgorithms getCurrentAlgo() {
		return this.currentAlgo;
	}

	/**
	 * @param currentAlgo the currentAlgo to set
	 */
	public void setCurrentAlgo(final EnumAlgorithms currentAlgo) {
		this.currentAlgo = currentAlgo;
	}

	/**
	 * @return the currentHashFunk
	 */
	public EnumHashFunktions getCurrentHashFunk() {
		return this.currentHashFunk;
	}

	/**
	 * @param currentHashFunk the currentHashFunk to set
	 */
	public void setCurrentHashFunk(final EnumHashFunktions currentHashFunk) {
		this.currentHashFunk = currentHashFunk;
	}

	/**
	 * @return the useAsciiCliper
	 */
	public boolean isUseAsciiCliper() {
		return this.useAsciiCliper;
	}

	/**
	 * @param useAsciiCliper the useAsciiCliper to set
	 */
	public void setUseAsciiCliper(final boolean useAsciiCliper) {
		this.useAsciiCliper = useAsciiCliper;
	}

	public String doDeCode(final String cliper,final Progresser prg) {
		String result = "";
		try{
			switch(this.getCurrentAlgo()){
			case RSA:
				if(this.isUseAsciiCliper()){
					result = KryptoFacade.deCodeWithRSA(this.getAlphabet(), cliper, this.getKp().getPrivateKey(), this.getBlocksize(), this.getChiffreBlocksize(), prg);
				} else {
					result = RSAStringUtil.deCodeNumberString(this.getAlphabet(), cliper,this.getKp().getPrivateKey(), this.getBlocksize(), this.getChiffreBlocksize(), prg);
				}
				break;
			case ECC:
				if(this.isUseAsciiCliper()){
					result = KryptoFacade.deCodeWithECC(this.getAlphabet(),cliper, this.getEccKeys() , prg);
				} else {
					result = KryptoFacade.deCodeWithECC(this.getAlphabet(),cliper, this.getEccKeys() , prg);
				}
			default:
				ErrorDialog.getInstance().show("Fehler bei Algorithmuswahl");
				break;
			}
		} catch (InterruptedException e) {
			System.err.println("Abbruch");
		}catch(final Exception e){
			ErrorDialog.getInstance().show(e.getMessage());
		}
		return result;
	}

	public String doEnCode(final String text, final Progresser prg) {
		String result = "";
		try{
			switch (this.getCurrentAlgo()) {
			case RSA:
				result = KryptoFacade.enCodeWithRSA(this.getAlphabet(), text, this.getKp().getPublicKey(), this.getBlocksize(), this.getChiffreBlocksize(), prg, this.isUseAsciiCliper());
				break;
			case ECC:
				result = KryptoFacade.enCodeWithECC(this.getAlphabet(), text, this.getEccKeys().getPublicKey(), prg, this.isUseAsciiCliper());
			default:
				ErrorDialog.getInstance().show("Fehler bei Algorithmuswahl");
				break;
			}
		} catch (InterruptedException e) {
			System.err.println("Abbruch");
		}catch(final Exception e){
			ErrorDialog.getInstance().show(e.getMessage());
		}
		return result;
	}

	private Alphabet getAlphabet() {
		return this.alphabet;
	}

	private void setAlphabet(Alphabet a){
		this.alphabet = a;
		this.chiffreBlocksize = RSAFacade.getOptimalChiffreBlocksize(this.getKp().getPrivateKey().getMainModul(), this.getAlphabet());
	}

	private int getChiffreBlocksize() {
		return this.chiffreBlocksize;
	}

	public boolean doVerify(final String text, final PosBigInt sign) {
		boolean result = false;
		try{
			switch (this.getCurrentAlgo()) {
			case RSA:
				switch (this.getCurrentHashFunk()) {
				case MD5:
					result = KryptoFacade.verifyRSA_MD5Hash(text, sign, this.getKp().getPublicKey());
					break;
				case SHA:
					result = KryptoFacade.verifyRSA_SHAHash(text, sign, this.getKp().getPublicKey());
					break;
				default:
					ErrorDialog.getInstance().show("Fehler bei Hashfunktionswahl");
					break;
				}
				break;
			default:
				ErrorDialog.getInstance().show("Fehler bei Algorithmuswahl");
				break;
			}
		}catch(final Exception e){
			ErrorDialog.getInstance().show(e.getMessage());
		}
		return result;
	}

	public String doSign(final String text) {
		PosBigInt result = PosBigInt.ZERO;
		try{
			switch (this.getCurrentAlgo()) {
			case RSA:
				switch (this.getCurrentHashFunk()) {
				case MD5:
					result = KryptoFacade.signWithRSA_MD5Hash(text, this.getKp().getPrivateKey());
					break;
				case SHA:
					result = KryptoFacade.signWithRSA_shaHash(text,this.getKp().getPrivateKey());
					break;
				default:
					ErrorDialog.getInstance().show("Fehler bei Hashfunktionswahl");
					break;
				}
				break;
			default:
				ErrorDialog.getInstance().show("Fehler bei Algorithmuswahl");
				break;
			}
		}catch(final Exception e){
			ErrorDialog.getInstance().show(e.getMessage());
		}
		return result.toString();
	}

	public Vector<String> generateKeys() {
		switch (this.getCurrentAlgo()) {
		case RSA:
			try {
				this.setKp(RSAFacade.generateKeys(this.getPrimeBitLenght()));
				Vector<String> res = this.getKeyStringVector();
				res.add(0, "Schlüssel berechent");
				return res;
			} catch (InterruptedException e) {
				System.err.println("Abbruch");
			} catch (final Exception e) {
				ErrorDialog.getInstance().show(e.getMessage());
			}
			break;
		case ECC:
			try {
				ECC ecc = new ECC();
				this.setEccKeys(ecc.generateKeys(this.getPrimeBitLenght()));
				Vector<String> res = this.getKeyStringVector();
				res.add(0, "Schlüssel berechent");
				return res;
			} catch (InterruptedException e) {
				Vector<String> res = new Vector<String>();
				res.add("Abbruch!");
				return res;
			} catch (Exception e) {
				ErrorDialog.getInstance().show(e.getMessage());
			}
			break;
		default:
			ErrorDialog.getInstance().show("Fehler bei der Schlüsseleingabe.");
			break;
		}
		Vector<String> res = new Vector<String>();
		res.add("Fehler!");
		return res;
	}

	public Vector<String> getKeyStringVector(){
		final Vector<String> result = new Vector<String>();
		result.add(this.getKp().getPrivateKey().toString());
		result.add(this.getKp().getPublicKey().toString());
		return result;
	}

	/**
	 * @return the kp
	 */
	public KeyPairRSA getKp() {
		return this.kp;
	}

	/**
	 * @param kp the kp to set
	 */
	public void setKp(final KeyPairRSA kp) {
		this.kp = kp;
		this.setBlocksize(RSAFacade.getMaxCleartextBlocksize(kp.getPrivateKey().getMainModul(), this.getAlphabet()));
	}

	public void showKeyEnterDialog(final CallHelper<Vector<String>> caller) {
		switch (this.getCurrentAlgo()) {
		case RSA:
			final KeyInputRSA dia = new KeyInputRSA(new CallHelper<KeyPairRSA>() {

				@Override
				public void callBack(final KeyPairRSA callVal) {
					GuiManager.this.setKp(callVal);
					caller.callBack(GuiManager.this.getKeyStringVector());
				}
			});
			dia.setVisible(true);
			break;
		default:
			ErrorDialog.getInstance().show("Fehler bei der SchlÔøΩsseleingabe.");
			break;
		}
	}

	public void setCurrentAlphabet(EnumAlphabet current) {
		switch(current){
		case JAVA_CHAR:
			this.setAlphabet(new AlphabetBig());
			break;
		case DE_ALPHA_26:
			this.setAlphabet(new Alphabet26());
			break;
		case DE_ALPHA_47:
			this.setAlphabet(new Alphabet47());
			break;
		case Basic_Latin:
			this.setAlphabet(new AlphabetBasicLatin());
			break;
		default:
			this.setAlphabet(new AlphabetBig());
			break;
		}
		this.setBlocksize(RSAFacade.getMaxCleartextBlocksize(kp.getPrivateKey().getMainModul(), this.getAlphabet()));
	}

	public void loadKeys(String absolutePath) throws Exception{
		if(absolutePath.endsWith(".ecc")){
			this.setEccKeys(Key.loadEccKeys(absolutePath));
		} else if(absolutePath.endsWith(".rsa")){
			//TODO
		} else {
			throw new Exception("Falsches Dateiformat");
		}
	}

	private String opfilePath;

	public void setOpFile(String absolutePath) {
		this.setOpfilePath(absolutePath);
	}

	public String getOpfilePath() {
		return opfilePath;
	}

	public void setOpfilePath(String opfilePath) {
		this.opfilePath = opfilePath;
	}

	public String doEnCodeFile(Progresser prg) {
		System.out.println("test");
		switch (this.getCurrentAlgo()) {
		case ECC:
			try {
				ECCFileUtil.enCodeFile(this.getOpfilePath(), this.getEccKeys().getPublicKey(),prg);
			} catch (Exception e) {
				e.printStackTrace();
				ErrorDialog.getInstance().show(e.getMessage());
			}
			break;
		case RSA:
				System.err.println("rsa");
			break;
		default:
			break;
		}
		return "Datei Verschlüsselt DUMMI";
	}

	public String doDeCodeFile(Progresser prg) {
		System.out.println("test");
		switch (this.getCurrentAlgo()) {
		case ECC:
			try {
				ECCFileUtil.deCodeFile(this.getOpfilePath(), this.getEccKeys(),prg);
			} catch (Exception e) {
				e.printStackTrace();
				ErrorDialog.getInstance().show(e.getMessage());
			}
			break;
		case RSA:
				System.err.println("rsa");
			break;
		default:
			break;
		}
		return "Datei Entschlüsselt DUMMI";
	}

	public void saveKeys() {
		try {
			this.getEccKeys().saveKeys("schluessel.ecc");
		} catch (IOException e) {
			ErrorDialog.getInstance().show(e.getMessage());
		}
	}

	public void setSecLevel(SecLevel valueOf) {
		switch (valueOf) {
		case niedrig:
			this.setPrimeBitLenght(128);
			break;
		case mittel:
			this.setPrimeBitLenght(1024);
			break;
		case hoch:
			this.setPrimeBitLenght(2048);
			break;
		default:
			this.setPrimeBitLenght(1024);
			break;
		}
	}	
}

package core.key;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Properties;
import java.util.Vector;

import core.algorithm.ecc.EccFormatException;
import core.algorithm.ecc.EllipticCurve;
import core.algorithm.ecc.Point;
import core.algorithm.ecc.PointFormatException;
import core.algorithm.ecc.PointUtil;

import Util.Tuple;

public abstract class Key {
	
	public final Properties properties;
	
	public Key(){
		this.properties = new Properties();
	}
	
	public Properties getProperties() {
		return properties;
	}

	public void saveKeys(String filePath) throws IOException{
		for(Tuple<String, String> v : this.getKeyValuePairs()){
			this.getProperties().setProperty(v.getFirst(), v.getSecond());
		}
		File f = new File(filePath);
		if(!f.exists()){
			f.createNewFile();
		}
		this.getProperties().store(new FileOutputStream(f), "KeyFile from Kryptool");
	}
	public abstract Vector<Tuple<String, String>> getKeyValuePairs();
	
	public static KeyPairECC loadEccKeys(String filepath) throws NumberFormatException, PointFormatException, EccFormatException, FileNotFoundException, IOException{
		Properties pro = new Properties();
		pro.load(new FileInputStream(new File(filepath)));
		BigInteger x = new BigInteger(pro.getProperty("x"));
		BigInteger p = new BigInteger(pro.getProperty("p"));
		EllipticCurve E = EllipticCurve.parse(pro.getProperty("E"));
		Point g = PointUtil.parse(pro.getProperty("g"));
		Point y = PointUtil.parse(pro.getProperty("y"));
		return new KeyPairECC(new PrivateKeyEcc(x), new PublicKeyEcc(E, p, g, y));
	}
}

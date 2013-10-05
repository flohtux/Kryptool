package core.algorithm.ecc;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;

import common.Tuple;

import Util.Progresser;
import core.key.PublicKeyEcc;

public class ECCFileUtil {

	public static void enCodeFile(String opfilePath, PublicKeyEcc publicKey,Progresser prg) throws Exception {
		ECC ecc = new ECC();
		File f = new File(opfilePath);
		int len =  (publicKey.getP().bitLength() / 8) - 1;
		LinkedList<Tuple<BigInteger, BigInteger>> l = ECCFileUtil.getM1AndM2List(new File(opfilePath),BigInteger.valueOf(len));
		prg.step(l.size());
		LinkedList<Tuple<Tuple<BigInteger,BigInteger>, Point>> data = new LinkedList<Tuple<Tuple<BigInteger,BigInteger>,Point>>();
		for(Tuple<BigInteger, BigInteger> t : l){
			Tuple<Tuple<BigInteger, BigInteger>, Point> x = ecc.encode(publicKey.getE(), publicKey.getP(), publicKey.getG(), publicKey.getY(), t.getFirst(), t.getSecond());
			data.add(x);
			prg.step();
		}
		ECCFileUtil.writeEncodedFile(opfilePath, data);
	}
	
	private static void writeEncodedFile(String sourceFile,LinkedList<Tuple<Tuple<BigInteger,BigInteger>, Point>> data) throws IOException{
		File file = new File(sourceFile.concat(".encoded"));
		if(!file.exists()) file.createNewFile();
		FileOutputStream stream = new FileOutputStream(file);
		for(Tuple<Tuple<BigInteger,BigInteger>, Point> t : data){
			byte[] b1 = t.getFirst().getFirst().toByteArray();
			System.out.println(b1.length);
			stream.write((byte)b1.length);
			stream.write(b1);
			byte[] b2 = t.getFirst().getSecond().toByteArray();
			System.out.println(b2.length);
			stream.write((byte)b2.length);
			stream.write(b2);
			//byte[] p = t.getSecond().toBytes();
		}
		stream.flush();
		stream.close();
	}
	
	private static byte[] readFileToByteArray(File file) throws IOException{
		byte [] fileData = new byte[(int)file.length()];
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		dis.readFully(fileData);
		dis.close();
		return fileData;
	}
	
	/**
	 * Only accurate up to max int size
	 * @param file
	 * @param lenght
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToByteArrayAtBlockSizeMultiple(File file, BigInteger lenght) throws IOException {
		byte[]  intermediateResult = ECCFileUtil.readFileToByteArray(file);
		BigInteger len = BigInteger.valueOf(intermediateResult.length);
		BigInteger r = len.remainder(lenght);
		int reminder = lenght.intValue() - r.abs().intValue();
		byte[] result;
		if(reminder != 0){
			result =  new byte[intermediateResult.length + reminder];
			for(int i = 0; i < intermediateResult.length;i++){
				result[i] = intermediateResult[i];
			}
			for(int i = 0; i < reminder; i++){
				result[intermediateResult.length + i] = (byte)reminder;
			}
		} else {
			result =  new byte[intermediateResult.length + lenght.intValue()];
			for(int i = 0; i < intermediateResult.length;i++){
				result[i] = intermediateResult[i];
			}
			for(int i = 0; i < lenght.intValue(); i++){
				result[intermediateResult.length + i] = (byte)lenght.intValue();
			}
		}
		return result;
	}
	
	public static byte[] toEvenSize(byte[] b, BigInteger lenght){
		if((b.length / lenght.intValue()) % 2 == 1){
			byte[] res = new byte[b.length + lenght.intValue()];
			for(int i = 0; i < b.length;i++){
				res[i] = b[i];
			}
			for(int i = 0; i < lenght.intValue(); i++){
				res[b.length + i] = 0;
			}
			return res;
		}
		return b;
	}
	
	public static LinkedList<Tuple<BigInteger, BigInteger>> getM1AndM2List(File f, BigInteger lenght) throws IOException{
		LinkedList<Tuple<BigInteger, BigInteger>> result = new LinkedList<Tuple<BigInteger,BigInteger>>();
		byte[] intermediateResult =  ECCFileUtil.readFileToByteArrayAtBlockSizeMultiple(f, lenght);
		byte[] evenIntermediateResult = ECCFileUtil.toEvenSize(intermediateResult, lenght);
		for(int i = 0; i < evenIntermediateResult.length - lenght.intValue(); i = i + (lenght.intValue() * 2)){
			byte[] m1 = new byte[lenght.intValue()];
			byte[] m2 = new byte[lenght.intValue()];
			System.arraycopy(evenIntermediateResult, i, m1, 0, lenght.intValue());
			System.arraycopy(evenIntermediateResult, i + lenght.intValue(), m2, 0, lenght.intValue());
			result.add(new Tuple<BigInteger, BigInteger>(new BigInteger(m1), (new BigInteger(m2))));
		}
		return result;
	}
}

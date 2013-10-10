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

import Util.ByteUtil;
import Util.Progresser;
import core.key.KeyPairECC;
import core.key.PublicKeyEcc;

public class ECCFileUtil {

	public static void enCodeFile(String opfilePath, PublicKeyEcc publicKey,Progresser prg) throws Exception {
		ECC ecc = new ECC();
		File f = new File(opfilePath);
		int len =  (publicKey.getP().bitLength() / 8) - 1;
		System.out.println("p.bitlen/8"+(len+1));
		LinkedList<EccEncodeBlock> l = ECCFileUtil.getM1AndM2List(new File(opfilePath),BigInteger.valueOf(len));
		prg.step(l.size());
		LinkedList<EccAfterEncodeBlock> data = new LinkedList<EccAfterEncodeBlock>();
		for(EccEncodeBlock  t : l){
			Tuple<Tuple<BigInteger, BigInteger>, Point> x = ecc.encode(publicKey.getE(), publicKey.getP(), publicKey.getG(), publicKey.getY(), t.getM1(), t.getM2());
			data.add(new EccAfterEncodeBlock(x.getFirst().getFirst(), x.getFirst().getSecond(), x.getSecond()));
			prg.step();
		}
		ECCFileUtil.writeEncodedFile(opfilePath, data);
	}
	
	public static void deCodeFile(String opfilePath, KeyPairECC keys, Progresser prg) throws Exception{
		ECC ecc = new ECC();
		File f = new File(opfilePath);
		LinkedList<byte[]> sourceData = ByteUtil.read(opfilePath);
		LinkedList<EccAfterEncodeBlock> resData = new LinkedList<EccAfterEncodeBlock>();
		prg.step(sourceData.size() / 4);
		for (int i = 0; i<sourceData.size(); i=i+4) {
			BigInteger b1 = new BigInteger(sourceData.get(i));
			BigInteger b2 = new BigInteger(sourceData.get(i + 1));
			Point a = PointUtil.fromByteTuple(new Tuple<byte[], byte[]>(sourceData.get(i + 2), sourceData.get(i + 3)));
			Tuple<BigInteger, BigInteger> resT = ecc.decode(keys.getPublicKey().getE(), keys.getPublicKey().getP(), b1, b2, a, keys.getPrivateKey().getX());
			resData.add(new EccAfterEncodeBlock(resT.getFirst(), resT.getSecond(), a));
			prg.step();
		}
		File fr = new File(opfilePath.substring(0, opfilePath.length() - 7));
		if(!f.exists()) f.createNewFile();
		FileOutputStream stream = new FileOutputStream(fr);
		EccAfterEncodeBlock last = resData.getLast();
		resData.removeLast();
		int bl =  (keys.getPublicKey().getP().bitLength() / 8) - 1;
		for (EccAfterEncodeBlock aE : resData) {
			byte[] m1S = aE.getM1().toByteArray();
			byte[] m2S = aE.getM2().toByteArray();
			stream.write(ECCFileUtil.coppyToBlockSize(m1S, bl));
			stream.write(ECCFileUtil.coppyToBlockSize(m2S, bl));
		}
		byte[] m1S = last.getM1().toByteArray();
		byte[] m2S = last.getM2().toByteArray();
		byte[] m1 = ECCFileUtil.coppyToBlockSize(m1S, bl);
		byte[] m2 = ECCFileUtil.coppyToBlockSize(m2S, bl);
		stream.write(ECCFileUtil.removeBufferOfLastElement(new Tuple<byte[], byte[]>(m1, m2)));
		stream.flush();
		stream.close();
	}
	
	private static byte[] coppyToBlockSize(byte[] source, int blockSize){
		byte[] rs = new byte[blockSize];
		int startPos = rs.length - (source.length);
		int len = source.length;
		int sourceStart = 0;
		if(source[0] == 0){
			startPos = startPos + 1;
			len = len - 1;
			sourceStart = sourceStart + 1;
		}
		System.arraycopy(source, sourceStart, rs,startPos , len);
		return rs;
	}
	
	private static byte[] removeBufferOfLastElement(Tuple<byte[], byte[]> last) {
		if(new BigInteger(last.getSecond()).equals(BigInteger.ZERO)){
			return ECCFileUtil.removeBufferOfLastElementIntern(last.getFirst());
		} else{
			byte[] partS = ECCFileUtil.removeBufferOfLastElementIntern(last.getSecond());
			byte[] partF = last.getFirst();
			byte[] res = new byte[partS.length + partF.length];
			System.arraycopy(partF, 0, res, 0, partF.length);
			System.arraycopy(partS, 0, res, partF.length, partS.length);
			return res;
		}
	}
	private static byte[] removeBufferOfLastElementIntern(byte[] last){
		int toRemove = last[last.length - 1];
		byte[] res = new byte[last.length - toRemove];
		System.arraycopy(last, 0, res, 0, res.length);
		return res;
	}

	private static void writeEncodedFile(String sourceFile,LinkedList<EccAfterEncodeBlock> data) throws IOException{
		LinkedList<byte[]> dataResult = new LinkedList<byte[]>();
		for (EccAfterEncodeBlock t : data) {
			dataResult.add(t.getM1().toByteArray());
			dataResult.add(t.getM2().toByteArray());
			Tuple<byte[], byte[]> bt = t.getA().toBytes();
			dataResult.add(bt.getFirst());
			dataResult.add(bt.getSecond());
		}
		ByteUtil.write(sourceFile.concat(".encode"), dataResult);
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
	
	public static LinkedList<EccEncodeBlock> getM1AndM2List(File f, BigInteger lenght) throws IOException{
		LinkedList<EccEncodeBlock> result = new LinkedList<EccEncodeBlock>();
		byte[] intermediateResult =  ECCFileUtil.readFileToByteArrayAtBlockSizeMultiple(f, lenght);
		byte[] evenIntermediateResult = ECCFileUtil.toEvenSize(intermediateResult, lenght);
		for(int i = 0; i < evenIntermediateResult.length - lenght.intValue(); i = i + (lenght.intValue() * 2)){
			byte[] m1 = new byte[lenght.intValue()];
			byte[] m2 = new byte[lenght.intValue()];
			System.arraycopy(evenIntermediateResult, i, m1, 0, lenght.intValue());
			System.arraycopy(evenIntermediateResult, i + lenght.intValue(), m2, 0, lenght.intValue());
			BigInteger m1b  = new BigInteger(1,m1);
			BigInteger m2b  = new BigInteger(1,m2);
			result.add(new EccEncodeBlock(m1b, m2b));
		}
		return result;
	}
}

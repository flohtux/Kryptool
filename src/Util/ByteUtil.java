package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;

public class ByteUtil {

	public static void write(String filePath,LinkedList<byte[]> data) throws IOException{
		int maxSize = ByteUtil.getMaxSize(data);
		int prefixSize = ByteUtil.getPrefixSize(maxSize);
		if(prefixSize > 256) throw new IOException("To Long Data element(prefix size overflow)");
		File resultFile = new File(filePath);
		if(!resultFile.exists()) resultFile.createNewFile();
		FileOutputStream stream = new FileOutputStream(resultFile);
		try {
			stream.write((byte)prefixSize);
			for (byte[] bs : data) {
				byte[] prefix = BigInteger.valueOf(prefixSize).toByteArray();
				if(prefix.length != prefixSize) throw new IOException("calc prefix != realprefix");
				stream.write(prefix);
				stream.write(bs);
			}
			stream.flush();
		} finally {
			stream.close();
		}
	}

	private static int getMaxSize(LinkedList<byte[]> data){
		int maxSize = 0;
		for (byte[] bs : data) {
			if(bs.length > maxSize) maxSize = bs.length;
		}
		return maxSize;
	}

	private static int oneByte = 8;
	private static int getPrefixSize(int maxSize){
		int high = ByteUtil.oneByte;
		int prefixSize = 2^high;
		while(maxSize > prefixSize){
			high = high + ByteUtil.oneByte;
			prefixSize = 2^high;
		}
		return high/8;
	}

	public static LinkedList<byte[]> read(String filePath) throws IOException{
		File f = new File(filePath);
		FileInputStream stream = new FileInputStream(f);
		LinkedList<byte[]> result = new LinkedList<byte[]>();
		try {
			int pos = stream.read();
			if(pos != -1){
				byte[] first = new byte[1];
				pos = stream.read(first, 0, 1);
				int prefixSize = new BigInteger(first).intValue();
				while(pos != -1){
					byte[] prefix = new byte[prefixSize];
					pos = stream.read(prefix, 0, prefixSize);
					int dataSize = new BigInteger(prefix).intValue();
					byte[] data = new byte[dataSize];
					pos = stream.read(data, 0, dataSize);
					result.add(data);
				}
			}
		} finally {
			stream.close();
		}
		return result;
	}

}

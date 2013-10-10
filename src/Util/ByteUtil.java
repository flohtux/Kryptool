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
		byte[] first = BigInteger.valueOf(prefixSize).toByteArray();
		if(first.length > 1) throw new IOException("To Long Data element(prefix size overflow)");
		File resultFile = new File(filePath);
		if(!resultFile.exists()) resultFile.createNewFile();
		FileOutputStream stream = new FileOutputStream(resultFile);
		try {
			stream.write(first);
			for (byte[] bs : data) {
				byte[] prefix = ByteUtil.getPrefix(bs.length,prefixSize);
				stream.write(prefix);
				System.out.println("Prefix sagt: "+new BigInteger(prefix) );
				System.out.println("bs ist: "+bs.length );
				if(bs[0] == 0) System.out.println("erster kann weg");
				stream.write(bs);
			}
			stream.flush();
		} finally {
			stream.close();
		}
	}

	public static byte[] getPrefix(int length, int prefixSize) throws IOException {
		byte[] res = new byte[prefixSize];
		byte[] inter = BigInteger.valueOf(length).toByteArray();
		if(inter.length > prefixSize) throw new IOException("Nop");
		System.arraycopy(inter, 0, res, res.length - inter.length, inter.length);
		return res;
	}

	private static int getMaxSize(LinkedList<byte[]> data){
		int maxSize = 0;
		for (byte[] bs : data) {
			if(bs.length > maxSize) maxSize = bs.length;
		}
		return maxSize;
	}

	private static int getPrefixSize(int maxSize){
		return BigInteger.valueOf(maxSize).toByteArray().length;
	}

	public static LinkedList<byte[]> read(String filePath) throws IOException{
		File f = new File(filePath);
		FileInputStream stream = new FileInputStream(f);
		LinkedList<byte[]> result = new LinkedList<byte[]>();
		try {
			int pos = 0;
			if(pos != -1){
				byte[] first = new byte[1];
				pos = stream.read(first, 0, 1);
				int prefixSize = new BigInteger(first).intValue();
				while(pos > 0){
					byte[] prefix = new byte[prefixSize];
					pos = stream.read(prefix, 0, prefixSize);
					if(pos < 0) break;
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

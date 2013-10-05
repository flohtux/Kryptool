package core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import core.alphabet.Alphabet;


public class StringUtil {
	
	public static List<String> stringIntoBlocks(String str, final int blockSize, Alphabet alphabet) throws RuntimeException{
		if(blockSize < 1) throw new RuntimeException("Blocksize must be two min");
		final List<String> result = new LinkedList<String>();
		while(!str.isEmpty()){
			if(str.length() >= blockSize){
				result.add(str.substring(0, blockSize));
				str = str.substring(blockSize);
			} else {
				str = str + alphabet.fillCharacter();
			}
		}
		return result;
	}
	
	public static LinkedList<PosBigInt> parsePosIntString(String numbers, char del){
		LinkedList<PosBigInt> result = new LinkedList<PosBigInt>();
		String[] numberStrings = numbers.split(" ");
		for(int i = 0; i< numberStrings.length; i++){
			result.add(PosBigInt.create(numberStrings[i]));
		}
		return result;
	}
	
	public static LinkedList<String> stringIntoBlocksAtDel(String str, final int blocksize, final char del){
		final LinkedList<String> result = new LinkedList<String>();
		while(!str.isEmpty()){
			int pos = str.indexOf(del, blocksize);
			if(pos < 0) pos = str.length();
			result.add(str.substring(0, pos));
			str = str.substring(pos);
		}
		return result;
	}

	public static String readFile(final File file) throws IOException {
		final FileReader reader = new FileReader(file);
		final BufferedReader bReader = new BufferedReader(reader);
		final StringBuffer result = new StringBuffer();
		String current = bReader.readLine();
		while(current != null){
			result.append(current).append(System.getProperty("line.separator"));
			current = bReader.readLine();
		}
		return result.toString();
	}
}

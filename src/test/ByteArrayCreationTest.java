package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import common.Tuple;

import core.algorithm.ecc.ECCFileUtil;

public class ByteArrayCreationTest {
	/*
	@Test
	public void test() {
		File testFile = new File("test.png");
		try {
			byte[] b = ECCFileUtil.readFileToByteArrayAtBlockSizeMultiple(testFile, BigInteger.valueOf(127));
			for(byte by : b){
				System.out.println(by);
			}
			System.out.println("File bytes: " + testFile.length());
			System.out.println("Gewünschte blocklänge " + 109);
			System.out.println("result bytes: " + b.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test2() {
		File testFile = new File("test.png");
		try {
			LinkedList<Tuple<BigInteger, BigInteger>> r = ECCFileUtil.getM1AndM2List(testFile, BigInteger.valueOf(127));
			for(Tuple<BigInteger, BigInteger> b: r) {
				System.out.println(b);
			}
			System.out.println(r.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
	@Test
	public void test2() {
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			System.err.println(new BigInteger(100, r).bitLength());
		}
	}
	
}

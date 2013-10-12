package test.shanks;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

import core.util.NumberGeneration;
import core.shanks.ShanksAlgorithm;

import org.junit.Test;


public class ShanksTests {

	@Test
	public void testFactorization() {
		System.out.println(NumberGeneration.generatePrimeFactors(BigInteger.valueOf(2*17L*19*23*31)));
//		System.out.println(NumberGeneration.generatePrimeFactors(BigInteger.valueOf(5003L*5003*5003)));
//		System.out.println(NumberGeneration.generatePrimeFactors(BigInteger.valueOf(844354649435046759L)));
//		System.out.println(NumberGeneration.generatePrimeFactors(BigInteger.valueOf(1073741826L)));
		
	}
	
	
	@Test
	public void testSqrt() {
		for (long i = 3; i < 100000; i++) {
			BigInteger r = BigInteger.valueOf(i);
			BigInteger result = ShanksAlgorithm.sqrtCeil(r);
			
			assertTrue(result.multiply(result).compareTo(r) >= 0);
			assertTrue(result.subtract(BigInteger.ONE).multiply(result.subtract(BigInteger.ONE)).compareTo(r) < 0);
		}
		
	}
	
	@Test
	public void test1() {
		BigInteger g = BigInteger.valueOf(8);
		BigInteger b = BigInteger.valueOf(555);
		BigInteger p = BigInteger.valueOf(677);
		
		BigInteger x = ShanksAlgorithm.discreteLog(g, b, p).getA();
		System.out.println(g+"^x="+b+" (mod "+p+")  ==> x="+x);
		
		assertEquals(b, g.modPow(x, p));
		
	}
	
	@Test
	public void test2() {
		BigInteger g = BigInteger.valueOf(11);
		BigInteger b = BigInteger.valueOf(3);
		BigInteger p = BigInteger.valueOf(29);
		
		BigInteger x = ShanksAlgorithm.discreteLog(g, b, p).getA();
		System.out.println(g+"^x="+b+" (mod "+p+")  ==> x="+x);
		
		assertEquals(b, g.modPow(x, p));
		
	}
	
	@Test
	public void test3() {
		BigInteger g = BigInteger.valueOf(3);
		BigInteger b = BigInteger.valueOf(13);
		BigInteger p = BigInteger.valueOf(17);
		
		BigInteger x = ShanksAlgorithm.discreteLog(g, b, p).getA();
		System.out.println(g+"^x="+b+" (mod "+p+")  ==> x="+x);
		
		assertEquals(b, g.modPow(x, p));
	}
	
	
	@Test
	public void test4() {
		BigInteger g = BigInteger.valueOf(8);

		BigInteger p = BigInteger.valueOf(677);
		
		for (int bb = 1; bb< 677; bb++) {
			BigInteger b = BigInteger.valueOf(bb);
			
			BigInteger x = ShanksAlgorithm.discreteLog(g, b, p).getA();
			System.out.println(g+"^x="+b+" (mod "+p+")  ==> x="+x);
			
			assertEquals(b, g.modPow(x, p));
		}
	}
	
	@Test
	public void testPrimitiveRoot () {
		Collection<Integer> l = Arrays.asList(2, 7, 11, 12, 13, 18, 20, 28, 31, 32, 34, 41, 44, 46, 48, 50, 51, 57, 61, 63);
		for (int i = 1; i <67; i++) {
			System.out.println("-------i"+i);
			assertEquals(l.contains(i), NumberGeneration.isPrimitiveRootModP(BigInteger.valueOf(i), BigInteger.valueOf(67)));
		}
	}
	
	
	@Test
	public void testPerformance() {
		BigInteger g_low = BigInteger.valueOf(2);

		BigInteger e = BigInteger.valueOf(99);
		
		for (int bb = 10; bb< 18; bb++) {
			BigInteger p = NumberGeneration.generateProbablePrimeAbove(BigInteger.valueOf(2).pow(bb));
			System.out.println("prim"+p);
			BigInteger g = NumberGeneration.generatePrimitiveRootModPAbove(g_low, p);
			System.out.println("erzeugt"+g);
			
			BigInteger x = ShanksAlgorithm.discreteLog(g, e, p).getA();
			System.out.println(g+"^x="+e+" (mod "+p+")  ==> x="+x);
			
		}
	}
	
//	@Test
	public void testInteractive() {
		Scanner in = new Scanner(System.in);
		System.out.print("Untere Schranke fŸr p ==> ");
		String numStr = in.next();
		BigInteger num;
		try {
			num = core.shanks.ShanksAlgorithm.parseStringToBigInteger(numStr);
		} catch (java.text.ParseException e1) {
			e1.printStackTrace();
			in.close();
			return;
		}
		
		BigInteger p = NumberGeneration.generateProbablePrimeAbove(num);
		
		System.out.print("Untere Schranke fŸr g ==> ");
		num = in.nextBigInteger();
		BigInteger g = NumberGeneration.generatePrimitiveRootModPAbove(num, p);
		
		System.out.print("Zu logarithmierender Wert e ==> ");
		BigInteger e = in.nextBigInteger();
		
		System.out.println("=====================");		
		System.out.println("p = " + p);
		System.out.println("g = " + g);
		System.out.println("e = " + e);
		BigInteger x = ShanksAlgorithm.discreteLog(g, e, p).getA();
		System.out.println(g+"^x="+e+" (mod "+p+")  ==> x="+x);
		in.close();
		
	}

}


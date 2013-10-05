package test;
import java.math.BigInteger;

import core.algorithm.ecc.EllipticCurve;
import core.algorithm.ecc.Field;
import core.algorithm.ecc.NPoint;
import core.algorithm.ecc.Point;


import junit.framework.TestCase;


public class ADDTest extends TestCase {

	public void test() throws Exception{
		Field f = new Field(BigInteger.valueOf(11));
		EllipticCurve e = new EllipticCurve(f, BigInteger.valueOf(3), BigInteger.valueOf(9));

		NPoint p1 = new NPoint(BigInteger.valueOf(2), BigInteger.ONE);
		Point n = p1;
		for(int i = 0 ; i < 11 ; i++){
			n = e.mulFast(p1, BigInteger.valueOf(i));
			System.out.println(n);
		}
	}
	/*
	public void test2() throws Exception{
		System.out.println("....................");
		Field f = new Field(BigInteger.valueOf(23));
		EllipticCurve e = new EllipticCurve(f, BigInteger.valueOf(1), BigInteger.valueOf(0));

		NPoint p1 = new NPoint(BigInteger.valueOf(11), BigInteger.valueOf(10));
		Point n = p1;
		System.out.println(p1);
		for(int i = 0 ; i < 24 ; i++){
			n = e.add(n,p1);
			System.out.println(n);
		}
	}
	*/
}

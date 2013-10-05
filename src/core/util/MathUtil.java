package core.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import Whistle.ELearnConsts;
import Whistle.WhistleLevel;
import Whistle.Whistleblower;

/**
 * Mathematical operations for kryptoTool
 * 
 * @author Jonas Tangermann
 * 
 */
public class MathUtil {

	/**
	 * Returns the result of base^exponent mod m.
	 * <pre>
	 * 1.) The binary representation of <code>exponent</code> will be calculated.
	 * 2.) <code>base</code> will be squared till the highest 2^x in <code>exponent</code>, with saving the single values.
	 * 3.) The result will now be calculated by multiplying every value from 2.) were the binary representation is one.
	 * </pre>
	 * @param base
	 * @param exponent
	 * @param m
	 */	
	public static PosBigInt powerByBinExpMod(final PosBigInt base, final PosBigInt exponent,final PosBigInt m){
		final LinkedList<Integer> binOfExp = MathUtil.decimalToBinAsList(exponent);
		//Whistleblower.whistle(ELearnConsts.NUMBER_AS_BINARY + exponent +" = "+Printer.stringPrintList(binOfExp), WhistleLevel.E_LEARN);
		Collections.reverse(binOfExp);
		int twoHighx = 0;
		PosBigInt currentResult = PosBigInt.ONE;
		PosBigInt currentsquareResult = base.mod(m);
		Iterator<Integer> i = binOfExp.iterator();
		while(i.hasNext()){
			Integer currentValue = i.next();
			Whistleblower.whistle("("+base+ "^2)^"+twoHighx+" mod "+m+" = "+currentsquareResult, WhistleLevel.E_LEARN);
			if(currentValue == 1){
				currentResult = currentResult.multiply(currentsquareResult).mod(m);
				Whistleblower.whistle(ELearnConsts.RESULTCANGE +"old result * ("+ base + "^2)^"+twoHighx+" mod "+m+" to: " + currentResult, WhistleLevel.E_LEARN);
			}
			currentsquareResult = currentsquareResult.multiply(currentsquareResult).mod(m);
			twoHighx++;
		}
		Whistleblower.whistle(ELearnConsts.FINALRESULT +base+"^"+ exponent + " mod "+m+" = "+currentResult, WhistleLevel.E_LEARN);
		return currentResult;
	}

	/**
	 * Returns the binary representation of dec as list
	 * <pre>
	 * 		dec = 99 <=> list of elements 1,1,0,0,0,1,1
	 * </pre>
	 * @param dec
	 * @return
	 */
	private static LinkedList<Integer> decimalToBinAsList(PosBigInt dec) {
		final LinkedList<Integer> result = new LinkedList<Integer>();
		if (dec.equals(PosBigInt.ZERO))
			result.add(0);
		while (dec.greater(PosBigInt.ZERO)) {
			final PosBigInt x = dec.mod(PosBigInt.TWO);
			result.addFirst(x.intValue());
			dec = dec.subtract(x).divide(PosBigInt.TWO);
		}
		return result;
	}	
	
	/**
	 * Returns a industrial prime with a bitlength of 'bitlength' 
	 * and a probability of 1/2^'sec'.
	 * @param bitlength
	 * @param sec
	 * @return
	 * @throws InterruptedException 
	 */
	public static PosBigInt generateProbablePrime(int bitlength, int sec) throws InterruptedException{
		PosBigInt result;
		SecureRandom random = new SecureRandom();
		do {
			if(Thread.currentThread().isInterrupted()) throw new InterruptedException();
			PosBigInt randomNumber = generateRandomNumberOfMinBitlength(bitlength,random);
			Integer randomOneOrMinOne = random.nextBoolean() ? 1 : -1;
			result = PosBigInt.create((PosBigInt.create(6).multiply(randomNumber)).asBigInt().add(BigInteger.valueOf(randomOneOrMinOne)));
		} while (!MathUtil.canBeAPrime(result, sec));
		return result;
	}
	
	/**
	 * Checks if 'n' is an industrial prime.
	 * This program has a security of 1/2^'sec' to return true with a non-prime.
	 * @param n number being checked
	 * @param sec security of result
	 * @return true, if 'n' is probably a prime.
	 */
	public static boolean canBeAPrime(PosBigInt n, int sec) {
		PosBigInt primeSubOne = n.subtract(PosBigInt.ONE);
		PosBigInt expo =  primeSubOne.divide(PosBigInt.TWO);
		List<PosBigInt> testValues = new LinkedList<PosBigInt>();
		for (int i = 1; i <= sec; i++) {
			if (PosBigInt.create(i).greaterEquals(n)) {
				System.out.println(i);
				return true; // zu 100% eine Primzahl!
			}
			PosBigInt testValue = MathUtil.generatePrimeTestNumberAndUpdateTestValues(testValues, n);
			PosBigInt testResult = MathUtil.powerByBinExpMod(testValue, expo, n);
			if (!testResult.equals(PosBigInt.ONE) && !testResult.equals(primeSubOne)){
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a new random number below 'maxValue'.
	 * If generated number is already in 'oldValues', a new is generated.
	 * This function CHANGES 'oldValues' - it adds generated result
	 * also to this list.
	 * @param oldValues random numbers generated in past tests
	 * @param maxValue upper bound of number generation
	 * @return a new random number and updates 'oldValues'.
	 */
	private static PosBigInt generatePrimeTestNumberAndUpdateTestValues(List<PosBigInt> oldValues, PosBigInt maxValue){
		PosBigInt testValue;
		SecureRandom r = new SecureRandom();
		do {
			testValue  = generateRandomNumber(maxValue.bitLength(), r);
		} while ( !(primeTestNumberAccepted(oldValues, maxValue, testValue)) );
		oldValues.add(testValue);
		return testValue;
	}
	
	private static boolean primeTestNumberAccepted(List<PosBigInt> oldValues, PosBigInt maxValue, PosBigInt testValue) {
		return  !(oldValues.contains(testValue)) &&
				testValue.less(maxValue) && 
				!(testValue.isZero());
	}
	/**
	 * Returns a number between 0 and 2^bitlength -1.
	 * @param bitlength
	 * @return
	 */
	public static PosBigInt generateRandomNumber(int bitlength, SecureRandom r) {
		return PosBigInt.create(new BigInteger(bitlength, r));
	}
	
	/**
	 * Returns a number of at least a certain bitlength.
	 * @param bitlength
	 * @return
	 */
	public static PosBigInt generateRandomNumberOfMinBitlength(int bitlength, SecureRandom r) {
		PosBigInt result;
		
		do {
			result = PosBigInt.create(new BigInteger(bitlength + bitlength / 10, r));
		} while (result.bitLength() < bitlength);
		
		return result;
	}
}

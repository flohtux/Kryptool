package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


import core.alphabet.Alphabet26;
import core.alphabet.Alphabet47;
import core.alphabet.AlphabetBig;
import core.util.G�delizer;
import core.util.PosBigInt;
import core.util.StringUtil;

public class G�delizerTests {

	G�delizer g�del26, g�del47, g�delBig;
	
	@Before
	public void setUp() {
		g�del26 = new G�delizer(new Alphabet26());
		g�del47 = new G�delizer(new Alphabet47());
		g�delBig = new G�delizer(new AlphabetBig());
	}
	
	@Test
	public void test1() {
		PosBigInt result = g�del47.singleCharBlockToInt("FHD", 3);
		assertEquals(PosBigInt.create(11377), result);
	}

	@Test
	public void test1a() {
		String result = g�del47.singleIntToCharBlock(PosBigInt.create(11377), 3);
		assertEquals("FHD", result);
	}
	
	@Test
	public void test2() {
		PosBigInt result = g�del47.singleCharBlockToInt("W  ", 3);
		assertEquals(PosBigInt.create(50806), result);
	}
	
	@Test
	public void testMultipleCharBlocksToInt() {
		List<String> fhdwBlocks = new ArrayList<String>();
		fhdwBlocks.add("FHD");
		fhdwBlocks.add("W  ");
		
		Iterator<PosBigInt> resultIter = g�del47.multipleCharBlocksToInt(fhdwBlocks, 3).iterator();
		assertEquals(PosBigInt.create(11377), resultIter.next());
		assertEquals(PosBigInt.create(50806), resultIter.next());
		assertFalse(resultIter.hasNext());
	}
	
	@Test
	public void testCipherAndDecipher() throws Exception {
		String message = "Wieviele Moeglichkeiten gibt es, eine natuerliche Zahl n als Summe von 4 Quadraten ganzer Zahlen darzustellen?".toUpperCase();	
		List<String> messageList = StringUtil.stringIntoBlocks(message, 5, new Alphabet47());
		
		List<PosBigInt> cipher = g�del47.multipleCharBlocksToInt(messageList, 5);
		List<String> deciphered = g�del47.multipleIntToCharBlocks(cipher, 5);
		
		assertEquals(messageList, deciphered);
		
	}
	
	@Test
	public void testCipherAndDecipherBig() throws Exception {
		String message = "Wieviele Moeglichkeiten gibt es, eine natuerliche Zahl n als Summe von 4 Quadraten ganzer Zahlen darzustellen?".toUpperCase();	
		List<String> messageList = StringUtil.stringIntoBlocks(message, 5, new AlphabetBig());
		
		List<PosBigInt> cipher = g�delBig.multipleCharBlocksToInt(messageList, 5);
		List<String> deciphered = g�delBig.multipleIntToCharBlocks(cipher, 5);
		
		assertEquals(messageList, deciphered);
		
	}
	
	@Test
	public void test5a() {
		String message = "USA";
		assertEquals(PosBigInt.create(13988), g�del26.singleCharBlockToInt(message, 3));
	}
	
	@Test
	public void test5b() {
		String message = "FBI";
		assertEquals(PosBigInt.create(3414), g�del26.singleCharBlockToInt(message, 3));
	}
	
	@Test
	public void test6a() {
		PosBigInt number = PosBigInt.create(90268);
		assertEquals("FDNW", g�del26.singleIntToCharBlock(number, 4));
	}
	
	@Test
	public void test6b() {
		PosBigInt number = PosBigInt.create(90268);
		assertEquals("AFDNW", g�del26.singleIntToCharBlock(number, 5));
	}
	
	@Test
	public void test7() {
		PosBigInt number = PosBigInt.create(209392);
		assertEquals("LXTO", g�del26.singleIntToCharBlock(number, 4));
	}
	
	@Test
	public void test8() {
		PosBigInt number = PosBigInt.ZERO;
		assertEquals("", g�del26.singleIntToCharBlock(number, 0));
	}
	
	@Test
	public void test9() {
		PosBigInt number = PosBigInt.ZERO;
		assertEquals("A", g�del26.singleIntToCharBlock(number, 1));
	}
	
	@Test
	public void test10() {
		PosBigInt number = PosBigInt.create(25);
		assertEquals("Z", g�del26.singleIntToCharBlock(number, 1));
	}
	
	@Test
	public void test11() {
		PosBigInt number = PosBigInt.create(26);
		assertEquals("BA", g�del26.singleIntToCharBlock(number, 2));
	}
	
	@Test(expected=core.exception.TooSmallBlockSizeException.class)
	public void testTooSmallBlockSize() {
		PosBigInt number = PosBigInt.create(26);
		g�del26.singleIntToCharBlock(number, 1);
	}
	
}

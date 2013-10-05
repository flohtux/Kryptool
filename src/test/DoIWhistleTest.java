package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Whistle.WhistleLevel;
import Whistle.Whistleblower;


public class DoIWhistleTest {
	
	@Test
	public void test() {
		Whistleblower whistleblower = Whistleblower.getInstance();
		
		whistleblower.setLevel(WhistleLevel.ALL);
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ALL));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.INFO));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.E_LEARN));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ERROR));
		
		whistleblower.setLevel(WhistleLevel.E_LEARN);
		assertFalse(whistleblower.doIWhistle(WhistleLevel.INFO));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ALL));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.E_LEARN));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ERROR));

		whistleblower.setLevel(WhistleLevel.ERROR);
		assertFalse(whistleblower.doIWhistle(WhistleLevel.INFO));
		assertFalse(whistleblower.doIWhistle(WhistleLevel.E_LEARN));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ERROR));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ALL));
		
		whistleblower.setLevel(WhistleLevel.INFO);
		assertTrue(whistleblower.doIWhistle(WhistleLevel.INFO));
		assertFalse(whistleblower.doIWhistle(WhistleLevel.E_LEARN));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ERROR));
		assertTrue(whistleblower.doIWhistle(WhistleLevel.ALL));
	}

}

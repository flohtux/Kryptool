package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class TODOTests {

	@Test
	public void testLeerzeichenZumAuffüllenErsetzen() {
		/*
		 * Es ist schöner, wenn wir ein Zeichen definieren und
		 * in alle Alphabete reinpacken, dass wirklich nur zum
		 * Auffüllen auf die Blocklänge verwendet wird und
		 * nicht gleichzeitig "Leerzeichen" ist.
		 * Vielleicht muss jedes Alphabet noch ne Funktion
		 * definieren, die die Nummer des Auffüllzeichens
		 * zurückgibt.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testAlphabetSollteInGUIAuswählbarSein() {
		/*
		 * Zum schnelleren Wechsel zwischen den Alphabeten.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testChiffratBlocklängeUndMessageBlocklängeSolltenüberprüftWerden() {
		/*
		 * Also das 26^k < n < 26^l.
		 * k ist übrigens MessageBlockSize und l ist ChiffreBlockSize.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testChiffratBlocklängeSollteInGUIAuswählbarSein() {
		/*
		 * Also das 'l' ausm Skript.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testSollBlockchiffreDesChiffratsOptionalSein() {
		/*
		 * Bei der ursprünglichen Version konnte man optional
		 * die Blockchiffre des verschlüsselten Textes
		 * einschalten (ascii chiffre). Bei dieser Version 
		 * habe ich mir nicht die Mühe gemacht das einzubauen.
		 * Meint ihr, dass das "Nice To Have" ist - ich seh
		 * nämlich gerade nicht so den Vorteil von Zahlen.
		 * Vielleicht kann man das dann einfacher nachrechnen,
		 * wenn man das möchte?
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testDieOptimalenBlocklängenSolltenDurchMagieVorgeschlagenWerden() {
		/*
		 * Sowohl 'k' als auch 'l' sind, wenn man was schnell
		 * verschlüsseln will, völlig doof auszurechnen. Das
		 * kann ruhig der Computer übernehmen und die Formel
		 * 26^k < n < 26^l auflösen.
		 * Es sollten in der GUI die beiden Schieberegler mit
		 * den "besten" Werten vorbelegt werden. Dann kriegt
		 * man auch nicht immer so dumme Fehler, dass die
		 * Blocklänge nicht passend ist.
		 */
		fail("Not yet implemented");
	}

}

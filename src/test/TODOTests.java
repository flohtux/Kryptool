package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class TODOTests {

	@Test
	public void testLeerzeichenZumAuff�llenErsetzen() {
		/*
		 * Es ist sch�ner, wenn wir ein Zeichen definieren und
		 * in alle Alphabete reinpacken, dass wirklich nur zum
		 * Auff�llen auf die Blockl�nge verwendet wird und
		 * nicht gleichzeitig "Leerzeichen" ist.
		 * Vielleicht muss jedes Alphabet noch ne Funktion
		 * definieren, die die Nummer des Auff�llzeichens
		 * zur�ckgibt.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testAlphabetSollteInGUIAusw�hlbarSein() {
		/*
		 * Zum schnelleren Wechsel zwischen den Alphabeten.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testChiffratBlockl�ngeUndMessageBlockl�ngeSollten�berpr�ftWerden() {
		/*
		 * Also das 26^k < n < 26^l.
		 * k ist �brigens MessageBlockSize und l ist ChiffreBlockSize.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testChiffratBlockl�ngeSollteInGUIAusw�hlbarSein() {
		/*
		 * Also das 'l' ausm Skript.
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testSollBlockchiffreDesChiffratsOptionalSein() {
		/*
		 * Bei der urspr�nglichen Version konnte man optional
		 * die Blockchiffre des verschl�sselten Textes
		 * einschalten (ascii chiffre). Bei dieser Version 
		 * habe ich mir nicht die M�he gemacht das einzubauen.
		 * Meint ihr, dass das "Nice To Have" ist - ich seh
		 * n�mlich gerade nicht so den Vorteil von Zahlen.
		 * Vielleicht kann man das dann einfacher nachrechnen,
		 * wenn man das m�chte?
		 */
		fail("Not yet implemented");
	}
	
	@Test
	public void testDieOptimalenBlockl�ngenSolltenDurchMagieVorgeschlagenWerden() {
		/*
		 * Sowohl 'k' als auch 'l' sind, wenn man was schnell
		 * verschl�sseln will, v�llig doof auszurechnen. Das
		 * kann ruhig der Computer �bernehmen und die Formel
		 * 26^k < n < 26^l aufl�sen.
		 * Es sollten in der GUI die beiden Schieberegler mit
		 * den "besten" Werten vorbelegt werden. Dann kriegt
		 * man auch nicht immer so dumme Fehler, dass die
		 * Blockl�nge nicht passend ist.
		 */
		fail("Not yet implemented");
	}

}

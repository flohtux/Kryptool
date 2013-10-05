package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.junit.Test;

import common.Tuple;

import core.algorithm.ecc.Point;

public class ByteSepTest {

	@Test
	public void test() throws IOException {
		File file = new File("test");
		if(!file.exists()) file.createNewFile();
		FileOutputStream stream = new FileOutputStream(file);
		
		byte[] b1 = new BigInteger("20333487683018595316766607256896629049001840743381827559009176466962726696293812170789939096712601228561911855999612963864357514523710449449484975881429206116335972049290723947808218024367980309468429402681716073612151382682721062805454814455565068819707415771348561300054383895856173544893360614225036879743").toByteArray();
		stream.write((byte)b1.length);
		stream.write(b1);
		byte[] b2 = new BigInteger("45267649708281833643080562712930302449673076165874946826240527739444600875180318905319420213309775340137688191304868620615186795940682690517772523681690195768882940735317199028885187618823235311871000655472894413631339085502781426455912580566110006650883666642134358162654304951934933564902796244750252791848").toByteArray();
		stream.write((byte)b2.length);
		stream.write(b2);
		
		stream.flush();
		stream.close();
		
		FileInputStream in = new FileInputStream(file);
		int i = 0;
		do{
			i = in.read();
			System.out.println("LŠnge: " + i);
			if(i == -1) break;
			byte[] b = new byte[i];
			in.read(b, 0, i);
			for (int j = 0; j < b.length; j++) {
				System.out.println(b[j]);
			}
			System.out.println("B:" + new BigInteger(b));
			System.out.println("__");
		}while(i != -1);
		in.close();
	}

}

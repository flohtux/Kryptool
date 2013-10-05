package test;


import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import core.hash.HashUtil;

public class HashTest {

	@Test
	public void test() {
		try {
			System.out.println(HashUtil.hashBysha(""));
		} catch (final NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

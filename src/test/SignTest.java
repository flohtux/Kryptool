package test;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import core.key.PrivateKeyRSA;
import core.key.PublicKeyRSA;
import core.util.PosBigInt;

public class SignTest {

	@Test
	public void test() throws NoSuchAlgorithmException {
		final PrivateKeyRSA prk = new PrivateKeyRSA( PosBigInt.create("12868250269082824031652939822600002646576548715135849754041459035397973684655072544531434592029997275913736079419786883291113633867084063617135936229819820914053024968980520431906402468232216972449675321378725315486993756686801943659094953783378389032820431033841471037683"),  PosBigInt.create("10194328135247432025075705573748054044690512618484244610344532482588005126804667859953474157322465374425167543436454543905947164492105549905776905747569215494738831139758073193677306728826383393134697399796538502108928515872268850888332279927353911058010091545735107971437"));
		final PublicKeyRSA puk = new PublicKeyRSA( PosBigInt.create("12868250269082824031652939822600002646576548715135849754041459035397973684655072544531434592029997275913736079419786883291113633867084063617135936229819820914053024968980520431906402468232216972449675321378725315486993756686801943659094953783378389032820431033841471037683"), PosBigInt.create(77));
		//TODO TEst

		/*PosBigInt a = RSA.deCode( PosBigInt.create("-167666489005720902570968031516667490219"), puk.getMainModul(), prk.getDecodeExponent());
		PosBigInt b = RSA.enCode(a, puk.getMainModul(), puk.getEncodeExponent());
		//TODO hier gehts weiter
		System.out.println(HashUtil.hashBysha("").toString(16));
		System.out.println(b);*/
	}

}

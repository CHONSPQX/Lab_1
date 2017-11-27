package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class BridgeWordsControlTest5 {

	@Test
	public void testQueryBridgeWords() {
		BridgeWordsControl bwc = new BridgeWordsControl();
		try 
		{
			BridgeWordsControl.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "The bridge words from seek to new are out ";
			//System.out.println(bwc.queryBridgeWords("hello", "new"));
			assertEquals(result, bwc.queryBridgeWords("seek", "new"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

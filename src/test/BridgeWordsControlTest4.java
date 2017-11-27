package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class BridgeWordsControlTest4 {

	@Test
	public void testQueryBridgeWords() {
		BridgeWordsControl bwc = new BridgeWordsControl();
		try 
		{
			BridgeWordsControl.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "No bridge words from civilizations to new";
			//System.out.println(bwc.queryBridgeWords("hello", "new"));
			assertEquals(result, bwc.queryBridgeWords("civilizations", "new"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

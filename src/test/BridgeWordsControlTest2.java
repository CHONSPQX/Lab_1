package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class BridgeWordsControlTest2 {

	@Test
	public void testQueryBridgeWords() {
		BridgeWordsControl bwc = new BridgeWordsControl();
		try 
		{
			BridgeWordsControl.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "No big in the graph";
			//System.out.println(bwc.queryBridgeWords("hello", "new"));
			assertEquals(result, bwc.queryBridgeWords("new", "big"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

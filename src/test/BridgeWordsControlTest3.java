package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class BridgeWordsControlTest3 {

	@Test
	public void testQueryBridgeWords() {
		BridgeWordsControl bwc = new BridgeWordsControl();
		try 
		{
			BridgeWordsControl.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "No hello and big  in the graph";
			//System.out.println(bwc.queryBridgeWords("hello", "new"));
			assertEquals(result, bwc.queryBridgeWords("hello", "big"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

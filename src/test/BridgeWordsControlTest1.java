package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class BridgeWordsControlTest1 {

	@Test
	public void testQueryBridgeWords() {
		BridgeWordsControl bwc = new BridgeWordsControl();
		try 
		{
			BridgeWordsControl.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "No hello in the graph ";
			System.out.println(bwc.queryBridgeWords("hello", "new"));
			//assertEquals(result, bwc.queryBridgeWords("hello", "new"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}

package functions;

import static org.junit.Assert.*;
import org.junit.Test;

public class WhiteBox3 {
	@Test
	public void testqueryBridgeWord() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:/java/JavaFXApplication1/test.txt"));
			String result = "No hello and big  in the graph";
			assertEquals(result, graph.queryBridgeWords("hello", "big"));//no fore and behi
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

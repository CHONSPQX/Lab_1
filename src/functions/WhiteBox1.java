package functions;

import static org.junit.Assert.*;
import org.junit.Test;

public class WhiteBox1 {
	@Test
	public void testqueryBridgeWord() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:/java/JavaFXApplication1/test.txt"));
			String result = "No hello in the graph";
			assertEquals(result, graph.queryBridgeWords("hello", "new"));//no fore
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

package functions;

import static org.junit.Assert.*;
import org.junit.Test;

public class WhiteBox2 {
	@Test
	public void testqueryBridgeWord() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:/java/JavaFXApplication1/test.txt"));
			String result = "No big in the graph";
			assertEquals(result, graph.queryBridgeWords("new", "big"));//no behi
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

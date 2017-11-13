package functions;

import static org.junit.Assert.*;
import org.junit.Test;

public class WhiteBox4 {
	@Test
	public void testqueryBridgeWord() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:/java/JavaFXApplication1/test.txt"));
			String result = "No bridge words from civilizations to new";
			assertEquals(result, graph.queryBridgeWords("civilizations", "new"));//fore is the last
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

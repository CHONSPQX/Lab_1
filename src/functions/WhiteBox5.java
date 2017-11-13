package functions;

import static org.junit.Assert.*;
import org.junit.Test;

public class WhiteBox5 {
	@Test
	public void testqueryBridgeWord() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:/java/JavaFXApplication1/test.txt"));
			String result = "The bridge words from seek to new are out ";
			assertEquals(result, graph.queryBridgeWords("seek", "new"));//beridge words exist
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

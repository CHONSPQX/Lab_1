package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest9 {
	@Test
	public void testcalcShortestPathString3() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:\\java\\JavaFXApplication1\\test.txt"));
			String result = "civilizations to 10000\ncivilizations explore 10000\ncivilizations strange 10000\ncivilizations new 10000\ncivilizations worlds 10000\ncivilizations seek 10000\ncivilizations out 10000\ncivilizations life 10000\ncivilizations and 10000\n";
			assertEquals(result, graph.calcShortestPath("civilizations"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

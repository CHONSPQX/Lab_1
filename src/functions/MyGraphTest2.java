package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest2 {
	@Test
	public void testcalcShortestPathStringString2() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:\\java\\JavaFXApplication1\\test.txt"));
			String result = " 10000\n";
			assertEquals(result, graph.calcShortestPath("civilizations", "worlds"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

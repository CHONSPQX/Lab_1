package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest6 {
	@Test
	public void testcalcShortestPathStringString6() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:\\java\\JavaFXApplication1\\test.txt"));
			String result = "";
			assertEquals(result, graph.calcShortestPath("team", "happy"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

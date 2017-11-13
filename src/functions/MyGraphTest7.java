package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest7 {
	
	@Test
	public void testcalcShortestPathString1() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:\\java\\JavaFXApplication1\\test.txt"));
			String result = "to explore 1\nto explore strange 2\nto explore strange new 3\nto explore strange new worlds 4\nto seek 1\nto seek out 2\nto explore strange new life 4\nto explore strange new life and 5\nto explore strange new civilizations 4\n";
			assertEquals(result, graph.calcShortestPath("to"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

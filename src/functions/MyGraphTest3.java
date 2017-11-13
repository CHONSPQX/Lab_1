package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest3 {
	@Test
	public void testcalcShortestPathStringString3() {
		MyGraph graph=null;
		
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:\\java\\JavaFXApplication1\\test.txt"));
			String result = " 3\nto explore strange new\nto seek out new\n";
			assertEquals(result, graph.calcShortestPath("to", "new"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

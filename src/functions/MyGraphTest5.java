package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest5 {
	@Test
	public void testcalcShortestPathStringString5() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:\\java\\JavaFXApplication1\\test.txt"));
			String result = "";
			assertEquals(result, graph.calcShortestPath("happy", "seek"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

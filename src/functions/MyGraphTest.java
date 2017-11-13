package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest {
	@Test
	public void testcalcShortestPathStringString1() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:/java/JavaFXApplication1/test.txt"));
			String result = " 2\nseek out new\n";
			assertEquals(result, graph.calcShortestPath("seek", "new"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

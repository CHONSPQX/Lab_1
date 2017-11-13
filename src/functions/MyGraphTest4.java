package functions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;

import com.sun.corba.se.impl.orbutil.graph.Graph;

public class MyGraphTest4 {
	@Test
	public void testcalcShortestPathStringString4() {
		MyGraph graph=null;
		try 
		{
			graph = new MyGraph(FileTrans.trans("E:\\java\\JavaFXApplication1\\test.txt"));
			String result = " 0\n";
			//graph.calcShortestPath("to", "happy");
			assertEquals(result, graph.calcShortestPath("to", "happy"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}

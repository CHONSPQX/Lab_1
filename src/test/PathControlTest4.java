package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class PathControlTest4 {

	@Test
	public void testCalcShortestPathStringString() {
		PathControl pc = new PathControl();
		try 
		{
			pc.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = " 0\n";
			//System.out.println(""+pc.calcShortestPath("to", "new"));
			assertEquals(result, pc.calcShortestPath("to", "happy"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class PathControlTest7 {

	@Test
	public void testCalcShortestPathString() {
		PathControl pc = new PathControl();
		try 
		{
			pc.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "";
			//System.out.println(""+pc.calcShortestPath("to", "new"));
			assertEquals(result, pc.calcShortestPath("happy"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

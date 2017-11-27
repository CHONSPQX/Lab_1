package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class PathControlTest9 {

	@Test
	public void testCalcShortestPathString() {
		PathControl pc = new PathControl();
		try 
		{
			PathControl.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "";
			//System.out.println(""+pc.calcShortestPath("civiliazations"));
			assertEquals(result, pc.calcShortestPath("civiliazations"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

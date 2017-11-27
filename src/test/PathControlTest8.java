package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class PathControlTest8 {

	@Test
	public void testCalcShortestPathString() {
		PathControl pc = new PathControl();
		try 
		{
			pc.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = "to explore 1\nto explore strange 2\nto explore strange new 3\n"
					+ "to explore strange new worlds 4\nto seek 1\nto seek out 2\n"
					+ "to explore strange new life 4\nto explore strange new life and 5\n"
					+ "to explore strange new civilizations 4\n";
			//System.out.println(""+pc.calcShortestPath("to", "new"));
			assertEquals(result, pc.calcShortestPath("to"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

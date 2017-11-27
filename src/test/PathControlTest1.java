package test;
import oocontrol.*;

import static org.junit.Assert.*;
import org.junit.Test;
import oounity.Graph;

public class PathControlTest1 {

	@Test
	public void testCalcShortestPathStringString() {
		PathControl pc = new PathControl();
		//pc.setGraph(new Graph("E:/java/JavaFXApplication1/test.txt"));
		try 
		{
			pc.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = " 2\nseek out new\n";
			//System.out.println(""+1+pc.calcShortestPath("seek", "new"));
			assertEquals(result, pc.calcShortestPath("seek", "new"));
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}

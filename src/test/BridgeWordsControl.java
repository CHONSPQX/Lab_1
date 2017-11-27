/**
 * 
 */
package test;
import oocontrol.*;
import java.util.ArrayList;

import oounity.*;

/**
 * @author Administrator
 *
 */
public class BridgeWordsControl {
	
	public static void main(String args[])
	{
		BridgeWordsControl bwc = new BridgeWordsControl();
		try 
		{
			bwc.setGraph(new Graph((new FileControl()).trans("F:/test.txt")));
			String result = " 2\nseek out new\n";
			System.out.println(bwc.queryBridgeWords("hello", "new"));
			//assertEquals(result, pc.calcShortestPath("seek", "new"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
   public static Graph graph;

  /**
   * @return the graph
   */
  public static Graph getGraph() {
    return graph;
  }

  /**
   * @param graph the graph to set
   */
  public static void setGraph(Graph graph) {
    BridgeWordsControl.graph = graph;
  }  
  public String queryBridgeWords(String fore, String behi)// 返回两个单词之间的所有桥接词
  {
      Graph graph=BridgeWordsControl.getGraph();
      ArrayList<String> bridge = new ArrayList<>();
      ArrayList<WordNode> templist = null;
      ArrayList<WordNode> pos = null;
      int flag1 = 0, flag2 = 0;
      StringBuilder bridgewords=new StringBuilder(); 
      if (!graph.words.contains(fore)) {

          flag1 = 1;
      }
      if (!graph.words.contains(behi)) {
          flag2 = 1;
      }
      if (flag1*flag2 == 1)
          return "No " + fore + " and " + behi + " " + " in the graph";
      else if (flag2 - flag1 == -1)
          return "No " + fore + " in the graph";
      else if (flag1 - flag2 == -1)
          return "No " + behi + " in the graph";
      else {
          templist = graph.maplink.get(fore);
          for (WordNode w : templist) {
              if (graph.words.contains(w.label)) {
                  pos = graph.maplink.get(w.label);
                  if (pos.contains(new WordNode(behi))) {
                      bridge.add(w.label);
                      bridgewords.append(w.label+" ");
                  }
              }
          }
          if (bridge.isEmpty()) {
              return "No bridge words from " + fore + " to " + behi;
          }
          //return "The bridge words from " + fore + " to " + behi + " are " + bridge.toString();
          return "The bridge words from " + fore + " to " + behi + " are " + bridgewords.toString();
      }
  }
  
   
}

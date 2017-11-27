/**
 * 
 */
package oocontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import oounity.*;

/**
 * @author Administrator
 *
 */
public class NewStringControl {
  public static Graph graph;

  /**
   * @return the graph
   */
  public static Graph getGraph() {
    return graph;
  }

  /**
   * @param graph
   *          the graph to set
   */
  public static void setGraph(Graph graph) {
    NewStringControl.graph = graph;
  }
  public String getNewString(String str) {
    String[] arr = str.split(" ");
    String outcome = "" + arr[0];
    List<String> list;
    for (int i = 0; i < arr.length - 1; i++) {
      list = findBridgeWords(arr[i], arr[i + 1]);
      if (list.size() == 0) {
        outcome = outcome + " " + arr[i + 1];
      } else if (list.size() == 1) {
        outcome = outcome + " " + list.get(0) + " " + arr[i + 1];
      } else {
        int j = new Random().nextInt(list.size() - 1);
        outcome = outcome + " " + list.get(j) + " " + arr[i + 1];
      }
    }
    return outcome;
  }

  public ArrayList<String> findBridgeWords(String fore, String behi) {
    Graph graph = NewStringControl.getGraph();
    ArrayList<String> bridge = new ArrayList<>();
    ArrayList<WordNode> templist = null;
    ArrayList<WordNode> pos = null;
    if (graph.words.contains(fore))
      templist = graph.maplink.get(fore);
    else
      return bridge;
    for (WordNode w : templist) {
      if (graph.words.contains(w.label)) {
        pos = graph.maplink.get(w.label);
        if (pos.contains(new WordNode(behi))) {
          bridge.add(w.label);
        }
      }
    }
    return bridge;
  }
}

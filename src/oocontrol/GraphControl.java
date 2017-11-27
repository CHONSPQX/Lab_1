/**
 * 
 */
package oocontrol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import oounity.*;

/**
 * @author Administrator
 *
 */
public class GraphControl {
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
    GraphControl.graph = graph;
  }
  
 public static void main(String[] args) throws IOException{  
        //System.loadLibrary("gv");
        FileControl fc=new FileControl();
        Graph g=new Graph(fc.trans("test.txt"));
        System.out.println(g);
        GraphControl.setGraph(g);
        GraphControl gc=new GraphControl();
        gc.SaveAsPiture();
    }  
  
   public String getPicture(String filename) throws IOException
   {
     Graph graph=new Graph(filename);
     GraphControl.setGraph(graph);
     BuildDot();
     return SaveAsPiture();
   }
   
   private void BuildDot() throws IOException// 根据图结构生成dot文件
   {
       Graph graph=GraphControl.getGraph();
       FileOutputStream out = new FileOutputStream(graph.dotfile + "dot.dot");
       OutputStreamWriter writer = new OutputStreamWriter(out);
       ArrayList<WordNode> pos = graph.maplink.get(graph.words.get(0));
       writer.write("digraph gra{" + '\n');
       for (String label : graph.words)
           writer.write("\"" + label + "\"\n");
       for (String label : graph.words) {
           pos = graph.maplink.get(label);
           if (!pos.isEmpty()) {
               for (WordNode w : pos) {
                   writer.write(
                           '\"' + label + '\"' + "->" + '\"' + w.label + '\"' + " [ label = " + w.weight + " ] \n");
               }
           }
       }
       writer.write("}\n");
       writer.close();
   }

   public String SaveAsPiture() throws IOException// 调用graphviz根据已经生成的dot文件得到GIF图片
   {
       Graph graph=GraphControl.getGraph();
       BuildDot();
       GraphViz gv = new GraphViz();
       gv.readSource(graph.dotfile + "dot.dot");
       // System.out.println(gv.getDotSource());
       String type = "gif";
       File out = new File(graph.dotfile + '.' + type); // Windows
       gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
       return graph.dotfile + '.' + type;
   }
}

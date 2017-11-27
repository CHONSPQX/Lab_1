/**
 * 
 */
package oocontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import oounity.*;

/**
 * @author Administrator
 *
 */
public class RandomWalkControl {
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
    RandomWalkControl.graph = graph;
  }
  
  public String randomWalk(TextArea outputStringArea,ImageView bottomImage) throws InterruptedException, IOException// 随机游走
  {
    ArrayList<Edge> edges = new ArrayList<>();
    StringBuffer fullpath = new StringBuffer();
    String label;
    int begin;
    ArrayList<WordNode> templist;
    begin = (int) (Math.random() * (graph.words.size()));
    label = graph.words.get(begin);
    templist = graph.maplink.get(label);
    outputStringArea.clear();
    outputStringArea.appendText(label + '\n');
    fullpath.append(label);
    while (templist.size() > 0 && !Thread.interrupted()) {
      Thread.sleep(1000);
      WordNode tempnode = templist
          .get((int) (Math.random() * (templist.size())));
      if (edges.contains(new Edge(label, tempnode.label)))
        break;
      else {
        outputStringArea.appendText(tempnode.label + "\n");
        fullpath.append(" ").append(tempnode.label);
        String temppathfile = BuildPathDot(fullpath.toString(), -1);
        String temppic =SaveAsPiture(temppathfile);
        FileInputStream inputStream = new FileInputStream(temppic);
        Image image = new Image(inputStream);
        bottomImage.setImage(image);
        edges.add(new Edge(label, tempnode.label));
        templist = graph.maplink.get(tempnode.label);
        label = tempnode.label;
      }
      Thread.sleep(1000);
    }
    if (Thread.interrupted()) {
      outputStringArea.appendText("randomWalk interrupted\n");
      return new String(fullpath);
    }
    try {
      FileOutputStream out = new FileOutputStream("randomPath" + ".txt");
      OutputStreamWriter writer = new OutputStreamWriter(out);
      if (edges.isEmpty())
        writer.write(label);
      else {
        writer.write(edges.get(0).from);
        for (Edge e : edges)
          writer.write(" " + e.to);
      }
      writer.close();
      outputStringArea.appendText("new random path has been saved in file\n");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new String(fullpath);
  }
  
  public String BuildPathDot(String path, int i) throws IOException// 根据path和数字，首先产生一个dot文件，然后将路径信息写入到dot文件中，返回dot文件名
  {
    Graph graph=RandomWalkControl.getGraph();
      List<String> pathnodes = Arrays.asList(path.split(" "));
      FileOutputStream out;
      if (i >= 0)
          out = new FileOutputStream(pathnodes.get(0) + "to" + pathnodes.get(pathnodes.size() - 1) + i + ".dot");
      else
          out = new FileOutputStream(pathnodes.get(0) + "to" + pathnodes.get(pathnodes.size() - 1) + ".dot");
      try (OutputStreamWriter writer = new OutputStreamWriter(out)) {
          ArrayList<WordNode> pos = null;
          writer.write("digraph gra{" + '\n');
          // writer.write("rankdir=LR\n");
          for (String label : graph.words) {
              if (pathnodes.contains(label))
                  writer.write("\"" + label + "\"" + " [ color = blue ] \n");
              else
                  writer.write("\"" + label + "\"\n");
          }

          for (String label : graph.words) {
              pos = graph.maplink.get(label);
              if (!pos.isEmpty()) {
                  int poslabel = pathnodes.indexOf(label);
                  int wlabel;
                  for (WordNode w : pos) {
                      // wlabel=pathnodes.indexOf(w.label);
                      // if(wlabel==poslabel+1&&poslabel>=0)
                      // writer.write("\""+label+"\""+" ->
                      // "+"\""+w.label+"\""+" [ label =
                      // "+w.weight+", color = green ] \n");
                      // else
                      if (poslabel >= 0 && (poslabel < pathnodes.size() - 1)
                              && pathnodes.get(poslabel + 1).equals(w.label))
                          writer.write("\"" + label + "\"" + " -> " + "\"" + w.label + "\"" + " [ label = " + w.weight
                                  + ", color = green  ] \n");
                      else
                          writer.write("\"" + label + "\"" + " -> " + "\"" + w.label + "\"" + " [ label = " + w.weight
                                  + " ] \n");
                  }
              }
          }
          writer.write("}\n");
          writer.close();
          if (i >= 0)
              return pathnodes.get(0) + "to" + pathnodes.get(pathnodes.size() - 1) + i + ".dot";
          else
              return pathnodes.get(0) + "to" + pathnodes.get(pathnodes.size() - 1) + ".dot";
      }
  }
  public String SaveAsPiture(String filename)// 根据输入的文件名所对应的dot文件生成GIF图片，返回图片的文件名
  {
      String input = filename.substring(0, filename.lastIndexOf("."));
      GraphViz gv = new GraphViz();
      gv.readSource(filename);
      // System.out.println(gv.getDotSource());

      String type = "gif";
      // String type = "dot";
      // String type = "fig"; // open with xfig
      // String type = "pdf";
      // String type = "ps";
      // String type = "svg"; // open with inkscape
      // String type = "png";
      // String type = "plain";
      // File out = new File("/tmp/simple." + type); // Linux
      File out = new File(input + "Pic" + '.' + type); // Windows
      gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
      return input + "Pic" + '.' + type;
  }

  
}

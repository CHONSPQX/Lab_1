/**
 * 
 */
package oocontrol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import oounity.*;

/**
 * @author Administrator
 *
 */
public class PathControl {
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
    PathControl.graph = graph;
  }
  
  public String calcShortestPath(String str1, String str2)// 单源最短路径算法，返回最短路径
  {
    Graph graph=PathControl.getGraph();
      StringBuffer path = new StringBuffer();
      try {
          int distance = 0;
          ArrayList<WordNode> s = new ArrayList<>();
          ArrayList<WordNode> set = new ArrayList<>();
          HashMap<String, ArrayList<String>> pathinfo = new HashMap<>();
          set.add(new WordNode(str1, 0));
          pathinfo.put(str1, new ArrayList<>());
          for (String label : graph.words) {
              if (!label.equals(str1)) {
                  s.add(new WordNode(label, graph.MAX_DIS));
                  pathinfo.put(label, new ArrayList<>());
              }
          }
          ArrayList<WordNode> templist = graph.maplink.get(str1);
          for (WordNode w : templist) {
              s.get(s.indexOf(w)).setDistance(w.weight);
              pathinfo.get(w.label).add(str1);
          }
          while (!s.isEmpty()) {
              s.sort(null);
              WordNode min = s.get(0);
              s.remove(0);
              set.add(min);
              templist = graph.maplink.get(min.label);
              for (WordNode node : s)
                  if (templist.contains(node)) {
                      int dis = templist.get(templist.indexOf(node)).weight;
                      if (node.weight > min.weight + dis) {
                          node.setDistance(min.weight + dis);
                          pathinfo.get(node.label).clear();
                          pathinfo.get(node.label).add(min.label);
                      } else if (node.weight == min.weight + dis) {
                          pathinfo.get(node.label).add(min.label);
                      }
                  }
          }
          if (set.contains(new WordNode(str2)))
              distance = set.get(set.indexOf(new WordNode(str2))).weight;
          ArrayList<String> PathOutcome = new ArrayList<>();
          getPaths(pathinfo, str1, str2, PathOutcome, "");
          SaveAllPaths(PathOutcome);
          PathOutcome.add(0, String.valueOf(distance));
          path.append(" ");
          for (String str : PathOutcome)
              path.append(str).append('\n');
          System.out.println(path.toString());
          return new String(path);
      } catch (Exception e) {
          return new String(path);
      }
  }

  public String calcShortestPath(String str1)// 单源最短路径，返回起点到所有其他顶点的最短路径
  {
      StringBuffer path = new StringBuffer();
      Graph graph=PathControl.getGraph();
      try {
          int distance = 0;
          ArrayList<WordNode> s = new ArrayList<>();
          ArrayList<WordNode> set = new ArrayList<>();
          HashMap<String, String> pathinfo = new HashMap<>();
          set.add(new WordNode(str1, 0));
          pathinfo.put(str1, "");
          for (String label : graph.words) {
              if (!label.equals(str1)) {
                  s.add(new WordNode(label, graph.MAX_DIS));
                  pathinfo.put(label, str1);
              }
          }
          ArrayList<WordNode> templist = graph.maplink.get(str1);
          for (WordNode w : templist) {
              s.get(s.indexOf(w)).setDistance(w.weight);
          }
          while (!s.isEmpty()) {
              s.sort(null);
              WordNode min = s.get(0);
              s.remove(0);
              set.add(min);
              templist = graph.maplink.get(min.label);
              for (WordNode node : s)
                  if (templist.contains(node)) {
                      int dis = templist.get(templist.indexOf(node)).weight;
                      if (node.weight > min.weight + dis) {
                          node.setDistance(min.weight + dis);
                          pathinfo.put(node.label, min.label);
                      }
                  }
          }
          ArrayList<String> PathOutcome = new ArrayList<>();
          for (String str : graph.words) {
              if (!str.equals(str1)) {
                  String tempstr = str;
                  StringBuffer temppath = new StringBuffer();
                  while (!pathinfo.get(tempstr).equals(str1)) {
                      temppath.insert(0, pathinfo.get(tempstr) + " ");
                      tempstr = pathinfo.get(tempstr);
                  }
                  temppath.insert(0, str1 + " ");
                  temppath.append(str);
                  PathOutcome.add(temppath.toString());
              }
          }
          for (String str : PathOutcome) {
              String fileString = BuildPathDot(str, -1);
              SaveAsPiture(fileString);
          }

          for (String str : PathOutcome) {
              String end = str.substring(str.lastIndexOf(" ") + 1);
              int dis = set.get(set.indexOf(new WordNode(end))).weight;
              path.append(str).append(" ").append(String.valueOf(dis)).append('\n');
          }
          return new String(path);
      } catch (Exception e) {
          return new String(path);
      }
  }
  private void getPaths(HashMap<String, ArrayList<String>> pathinfo, String begin, String end,
     ArrayList<String> PathOutcome, String temppath)// 根据单源最短路径算法的计算结果，整理出所有最短路径
{
  try {
      if (pathinfo.get(end).contains(begin)) {
          temppath = begin + " " + end + temppath;
          PathOutcome.add(temppath);
      } else {
          ArrayList<String> tempArrayList = pathinfo.get(end);
          temppath = " " + end + temppath;
          for (String str : tempArrayList) {
              getPaths(pathinfo, begin, str, PathOutcome, temppath);
          }
      }
  } catch (Exception e) {

  }
}

public void SaveAllPaths(ArrayList<String> paths)// 将输入paths中的所有路径信息都分别保存在dot文件中
{
  int i = 0;
  for (String pathString : paths) {
      try {
          String fileString = BuildPathDot(pathString, i);
          SaveAsPiture(fileString);
          i++;
      } catch (IOException ex) {
          // Logger.getLogger(MyGraph.class.getName()).log(Level.SEVERE,
          // null, ex);
      }
  }
}

public String FindPictureOfPath(String path, int i)// 根据路径和数字，解析出这条路径所对应的GIF文件名
{
  if (i >= 0) {
      String type = "gif";
      List<String> pathnodes = Arrays.asList(path.split(" "));
      return pathnodes.get(0) + "to" + pathnodes.get(pathnodes.size() - 1) + i + "Pic." + type;
  } else {
      String type = "gif";
      List<String> pathnodes = Arrays.asList(path.split(" "));
      return pathnodes.get(0) + "to" + pathnodes.get(pathnodes.size() - 1) + "Pic." + type;
  }
}

public String BuildPathDot(String path, int i) throws IOException// 根据path和数字，首先产生一个dot文件，然后将路径信息写入到dot文件中，返回dot文件名
{
  List<String> pathnodes = Arrays.asList(path.split(" "));
  Graph graph=PathControl.getGraph();
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

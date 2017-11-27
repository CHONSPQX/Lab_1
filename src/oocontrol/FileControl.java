/**
 * 
 */
package oocontrol;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import oounity.Graph;

public class FileControl {
  
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
    FileControl.graph = graph;
  }
  
  public String trans(final String txtFilename) throws IOException {
    Reader reader = null;
    final String outfilename = txtFilename.substring(0,
        txtFilename.lastIndexOf('.')) + "temp" + ".txt";
    try {
      final FileOutputStream out = new FileOutputStream(outfilename);
      final OutputStreamWriter writer = new OutputStreamWriter(out);
      reader = new InputStreamReader(new FileInputStream(txtFilename));
      int tempchar;
      boolean blank = false;
      boolean beginwrite = false;
      while ((tempchar = reader.read()) != -1) {
        if (Character.isLetter((char) tempchar)) {

          if (blank && beginwrite) {
             writer.write(' ');
          }
          writer.write((char) tempchar);
          beginwrite = true;
          blank = false;
        } else {
          blank = true;
        }
      }
      reader.close();
      writer.close();
      System.out.println("");
      return outfilename;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}

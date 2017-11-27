/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oounity;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class GraphViz {
  /**
   * The dir. where temporary files will be created.
   */
  // private static String TEMP_DIR = "/tmp"; // Linux
  private static String TempDir = "temp"; // Windows

  /**
   * Where is your dot program located? It will be called externally.
   */
  // private static String DOT = "/usr/bin/dot"; // Linux
  private static String Dot = "GraphViz\\bin\\dot.exe"; // Windows

  /**
   * The source of the graph written in dot language.
   */
  private StringBuilder graph = new StringBuilder();

  /**
   * Constructor: creates a new GraphViz object that will contain a graph.
   */
  public GraphViz(){
  }

  /**
   * Returns the graph's source description in dot language.
   * 
   * @return Source of the graph in dot language.
   */
  public String getDotSource() {
    return graph.toString();
  }

  /**
   * Returns the graph as an image in binary format.
   * 
   * @param dot_source
   *          Source of the graph to be drawn.
   * @param type
   *          Type of the output image to be produced, e.g.: gif, dot, fig, pdf,
   *          ps, svg, png.
   * @return A byte array containing the image of the graph.
   */
  public byte[] getGraph(String dotSource, String type) {
    File dot;
    byte[] imgStream = null;

    try {
      dot = writeDotSourceToFile(dotSource);
      if (dot != null) {
        imgStream = get_img_stream(dot, type);
        if (dot.delete() == false) {
          System.err.println(
              "Warning: " + dot.getAbsolutePath() + " could not be deleted!");
        }
        return imgStream;
      }
      return null;
    } catch (java.io.IOException ioe) {
      return null;
    }
  }

  /**
   * Writes the graph's image in a file.
   * 
   * @param img
   *          A byte array containing the image of the graph.
   * @param file
   *          Name of the file to where we want to write.
   * @return Success: 1, Failure: -1
   */
  public int writeGraphToFile(final byte[] img, final String file) {
    File to = new File(file);
    return writeGraphToFile(img, to);
  }

  /**
   * Writes the graph's image in a file.
   * 
   * @param img
   *          A byte array containing the image of the graph.
   * @param to
   *          A File object to where we want to write.
   * @return Success: 1, Failure: -1
   */
  public int writeGraphToFile(final byte[] img, final File to) {
    try {
      FileOutputStream fos = new FileOutputStream(to);
      fos.write(img);
      fos.close();
    } catch (java.io.IOException ioe) {
      ioe.printStackTrace();
      return -1;
    }
    return 1;
  }

  /**
   * It will call the external dot program, and return the image in binary
   * format.
   * 
   * @param dot
   *          Source of the graph (in dot language).
   * @param type
   *          Type of the output image to be produced, e.g.: gif, dot, fig, pdf,
   *          ps, svg, png.
   * @return The image of the graph in .gif format.
   */
  private byte[] get_img_stream(File dot, String type) {
    File img;
    byte[] imgStream = null;

    try {
      img = File.createTempFile("graph_", "." + type,
          new File(GraphViz.TempDir));
      Runtime rt = Runtime.getRuntime();

      // patch by Mike Chenault dot neato twopi circo fdp sfdp patchwork
      String[] args = {Dot, "-Kdot", "-T" + type, dot.getAbsolutePath(), "-o",
          img.getAbsolutePath()};
      Process p = rt.exec(args);

      p.waitFor();

      FileInputStream in = new FileInputStream(img.getAbsolutePath());
      imgStream = new byte[in.available()];
      in.read(imgStream);
      // Close it if we need to
      if (in != null) {
        in.close();
      }

      if (img.delete() == false) {
        System.err.println(
            "Warning: " + img.getAbsolutePath() + " could not be deleted!");
      }
    } catch (java.io.IOException ioe) {
      System.err.println("Error:    in I/O processing of tempfile in dir "
          + GraphViz.TempDir + "\n");
      System.err.println("       or in calling external command");
      ioe.printStackTrace();
    } catch (java.lang.InterruptedException ie) {
      System.err.println(
          "Error: the execution of the external program was interrupted");
      ie.printStackTrace();
    }

    return imgStream;
  }
  /**
   * Writes the source of the graph in a file, and returns the written file as a
   * File object.
   * 
   * @param str
   *          Source of the graph (in dot language).
   * @return The file (as a File object) that contains the source of the graph.
   */

  public File writeDotSourceToFile(String str) throws java.io.IOException {
    File temp;
    try {
      temp = File.createTempFile("graph_", ".dot.tmp",
          new File(GraphViz.TempDir));
      FileWriter fout = new FileWriter(temp);
      fout.write(str);
      fout.close();
    } catch (Exception e) {
      System.err.println(
          "Error: I/O error while writing the dot source to temp file!");
      return null;
    }
    return temp;
  }

  
  /**
   * Read a DOT graph from a text file.
   * 
   * @param input
   *          Input text file containing the DOT graph source.
   */
  public void readSource(String input) {
    StringBuilder sb = new StringBuilder();

    try {
      FileInputStream fis = new FileInputStream(input);
      DataInputStream dis = new DataInputStream(fis);
      BufferedReader br = new BufferedReader(new InputStreamReader(dis));
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      dis.close();
    } catch (Exception e) {
      System.err.println("Error: " + e.getMessage());
    }

    this.graph = sb;
  }

} // end of class GraphViz

/**
 * 
 */
package oounity;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Administrator
 *
 */
public class Graph {
  public static int MAX_DIS = 10000;
  public HashMap<String, ArrayList<WordNode>> maplink;// 邻接表
  public ArrayList<String> words;// 保存每一个单词再文章中出现的先后顺序
  public String dotfile;// 默认文件路径
  /**
   * @return the maplink
   */
  public HashMap<String, ArrayList<WordNode>> getMaplink() {
    return maplink;
  }
  /**
   * @param maplink the maplink to set
   */
  public void setMaplink(HashMap<String, ArrayList<WordNode>> maplink) {
    this.maplink = maplink;
  }
  /**
   * @return the words
   */
  public ArrayList<String> getWords() {
    return words;
  }
  /**
   * @param words the words to set
   */
  public void setWords(ArrayList<String> words) {
    this.words = words;
  }
  /**
   * @return the dotfile
   */
  public String getDotfile() {
    return dotfile;
  }
  /**
   * @param dotfile the dotfile to set
   */
  public void setDotfile(String dotfile) {
    this.dotfile = dotfile;
  }
  public Graph(String s) throws IOException// 根据处理过的文件建立图结构
  {
      dotfile = s.substring(0, s.lastIndexOf("temp"));
      maplink = new HashMap<String, ArrayList<WordNode>>();
      words = new ArrayList<>();
      ArrayList<WordNode> templist = null;
      String tempword;
      String nextword;
      Scanner reader = null;
      reader = new Scanner(new File(s));
      if (reader.hasNext())
          tempword = reader.next().toLowerCase();
      else
          return;
      try {
          while (reader.hasNext()) {
              nextword = reader.next().toLowerCase();
              // System.out.print(tempword);
              boolean flag = maplink.containsKey(tempword);
              if (flag) {
                  templist = maplink.get(tempword);
              } else {
                  templist = new ArrayList<>();
                  maplink.put(tempword, templist);
                  words.add(tempword);
              }
              if (templist.contains(new WordNode(nextword))) {
                  int index = templist.indexOf(new WordNode(nextword));
                  templist.get(index).addWeight();
              } else {
                  templist.add(new WordNode(nextword));
              }
              tempword = nextword;
          }
          if (!maplink.containsKey(tempword)) {
              maplink.put(tempword, new ArrayList<>());
              words.add(tempword);
          }
          reader.close();
      } catch (Exception e) {
      }
  }

}

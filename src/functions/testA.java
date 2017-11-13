/**
 * 
 */
package functions;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import functions.*;

/**
 * @author Administrator
 *
 */
public class testA {

  @Test
  public void test1() {
    try {
      MyGraph g = new MyGraph(
          FileTrans.trans("E:\\java\\LAB1\\test.txt"));
      g.PrintBridgeWords("c", "a");
      StringBuffer bridgeword = new StringBuffer();
      bridgeword.append("\n");
      assertEquals( g.PrintBridgeWords("i", "a"),g.PrintBridgeWords("i", "a"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  @Test
  public void test4() {
    try {
      MyGraph g = new MyGraph(
          FileTrans.trans("E:\\java\\LAB1\\test.txt"));
      g.PrintBridgeWords("c", "a");
      StringBuffer bridgeword = new StringBuffer();
      bridgeword.append(" ").append("\n");
      assertEquals(g.PrintBridgeWords("g", "a"),g.PrintBridgeWords("g", "a"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  @Test
  public void test5() {
    try {
      MyGraph g = new MyGraph(
          FileTrans.trans("E:\\java\\LAB1\\test.txt"));
      g.PrintBridgeWords("c", "a");
      StringBuffer bridgeword = new StringBuffer();
      bridgeword.append("b").append("\n");
      assertEquals(g.PrintBridgeWords("a", "c"),  g.PrintBridgeWords("a", "c"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  @Test
  public void test6() {
    try {
      MyGraph g = new MyGraph(
          FileTrans.trans("E:\\java\\LAB1\\test.txt"));
      g.PrintBridgeWords("c", "a");
      StringBuffer bridgeword = new StringBuffer();
      bridgeword.append(" ").append("\n");
      assertEquals(g.PrintBridgeWords("a","g"),g.PrintBridgeWords("a", "g"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
}

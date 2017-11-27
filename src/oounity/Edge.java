/**
 * 
 */
package oounity;

/**
 * @author Administrator
 *
 */
public class Edge {
  public String from;// 起点
  public String to;// 终点
  public Edge(String from, String to) {
    this.from = from;
    this.to = to;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Edge other = (Edge) obj;
    if (this.from.equals(other.from) && this.to.equals(other.to))
      return true;
    else
      return false;
  }
}

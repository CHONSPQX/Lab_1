/**
 * 
 */
package ooform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import oocontrol.PathControl;
import oounity.Graph;

/**
 * @author Administrator
 *
 */
public class PathForm {
    public Graph graph;
     public String calcShorttestPath(String word1,String word2) throws IOException
    { 
        PathControl.setGraph(graph);
        PathControl pc=new PathControl();
        if(word2.isEmpty())
        {
            return pc.calcShortestPath(word1);
        }
        else
            return pc.calcShortestPath(word1, word2);
    }
private void getPath(boolean GraphExist,int Mode,JFXTextField BeginWord,JFXTextField EndWord,ImageView MediumPaneImage,Image initialImage,
        boolean PathhasEnd,ListView PathList) throws IOException {
        if (!GraphExist) {
            return;
        }
        Mode = 4;
        String beginString = BeginWord.getText();
        String endsString = EndWord.getText();
        MediumPaneImage.setImage(initialImage);
        if (beginString.isEmpty() && endsString.isEmpty()) {
            return;
        }
        else if(endsString.isEmpty())
        {
            PathhasEnd=false;
            BeginWord.clear();
            EndWord.clear();
            String[] infoArrayList = calcShorttestPath(beginString,"").split("\n");
            PathList.getItems().clear();
            PathList.getItems().addAll(Arrays.asList(infoArrayList));
        }
        else
        {
            PathhasEnd=true;
            BeginWord.clear();
            EndWord.clear();
            String[] infoArrayList = calcShorttestPath(beginString, endsString).split("\n");
            PathList.getItems().clear();
            PathList.getItems().add("Distance :" + infoArrayList[0]);
            for (int i = 1; i < infoArrayList.length; i++) {
                PathList.getItems().add(infoArrayList[i]);
            }
        }
    }
}

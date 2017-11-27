/**
 * 
 */
package ooform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.ArrayList;
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
import oocontrol.BridgeWordsControl;
import oounity.Graph;

/**
 * @author Administrator
 *
 */
public class BridgeWordsForm {
    public Graph graph;
      public String queryBridgeWords(String word1,String word2) throws IOException
    {
        BridgeWordsControl.setGraph(graph);
        BridgeWordsControl bc=new BridgeWordsControl();
        return bc.queryBridgeWords(word1, word2);
    }
public void getBridgeword(boolean GraphExist,int Mode,JFXTextField BeginWord,JFXTextField EndWord,ImageView MediumPaneImage,Image initialImage,JFXTextField InfomationFeild) throws IOException {
        if (!GraphExist) {
            return;
        }
        Mode = 3;
        String beginString = BeginWord.getText();
        String endsString = EndWord.getText();
        if (beginString.isEmpty() || endsString.isEmpty()) {
            //javafx.scene.control.Alert alert=new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            //alert.setHeaderText(null);
            //alert.setContentText("please enter in all feilds");
            //alert.showAndWait();
            MediumPaneImage.setImage(initialImage);
            return;
        }
        BeginWord.clear();
        EndWord.clear();
        InfomationFeild.setText(queryBridgeWords(endsString, endsString));
        //String[] infoArrayList = queryBridgeWords(beginString, endsString).split("\n");
        /*if (infoArrayList.length==0) {
            InfomationFeild.setText("There is no \"Bridge Words\" beteen \"" + beginString + "\" and \"" + endsString + "\".");
            MediumPaneImage.setImage(initialImage);
        } else if (infoArrayList.length == 2) {
            InfomationFeild.setText("The \"Bridge Words\" beteen \"" + beginString + "\" and \"" + endsString + "\" is :" + infoArrayList[1]);
            FileInputStream inputStream = new FileInputStream(infoArrayList[0]);
            Image image = new Image(inputStream);
            MediumPaneImage.setImage(image);
        } else if (infoArrayList.length > 2) {
            InfomationFeild.setText("The \"Bridge Words\" beteen \"" + beginString + "\" and \"" + endsString + "\" are : \n");
            for (int i = 1; i < infoArrayList.length; i++) {
                InfomationFeild.appendText(infoArrayList[i]+"\n");
            }
            FileInputStream inputStream = new FileInputStream(infoArrayList[0]);
            Image image = new Image(inputStream);
            MediumPaneImage.setImage(image);
        }*/

    }
}

/**
 * 
 */
package ooform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import oocontrol.NewStringControl;
import oounity.Graph;

/**
 * @author Administrator
 *
 */
public class NewStringForm {
    public Graph graph;
 public void getNewstring(boolean  GraphExist,int Mode,ImageView BottomImage,Image initialImage,TextArea InputStringArea,TextArea OutputStringArea) {
        if (!GraphExist) {
            return;
        }
        Mode = 5;
        BottomImage.setImage(initialImage);
        String inputString = InputStringArea.getText();
        if (!inputString.isEmpty()) {
            NewStringControl.setGraph(graph);
            NewStringControl nc=new NewStringControl();
            String outcome = nc.getNewString(inputString);
            OutputStringArea.setText(outcome);
        }
    }
}

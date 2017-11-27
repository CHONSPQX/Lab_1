/**
 * 
 */
package ooform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import oocontrol.GraphControl;
import oounity.Graph;

/**
 * @author Administrator
 *
 */
public class ImageWindow {
public void showDirectedGraph(ImageView view,Graph graph,String handledTXTfilename,String initialPicturename,Image initialImage,boolean GraphExist)
    {
         try {
            graph = new Graph(handledTXTfilename);
            GraphControl gc=new GraphControl();
            GraphControl.setGraph(graph);
            String picture = gc.SaveAsPiture();
            initialPicturename = picture;
            FileInputStream inputStream = new FileInputStream(picture);
            Image image = new Image(inputStream);
            initialImage = image;
            view.setImage(image);
            GraphExist = true;
        } catch (IOException e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Build a new MyGraph class failed!");
            alert.showAndWait();
        } 
    }
}

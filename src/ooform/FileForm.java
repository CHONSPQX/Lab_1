/**
 * 
 */
package ooform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import oocontrol.FileControl;
import oounity.Graph;

/**
 * @author Administrator
 *
 */
public class FileForm {
public void fileTrans(String initialTXTfilename,int Mode, String handledTXTfilename, TextArea  HandledTXTFile ) throws IOException {
        if (initialTXTfilename == null) {
            return;
        }
        Mode = 1;
        FileControl fc=new FileControl();   
        handledTXTfilename = fc.trans(initialTXTfilename);
        HandledTXTFile.setWrapText(true);
        HandledTXTFile.setText(handledTXTfilename + "\n");
        File file = new File(handledTXTfilename);
        if (file.exists() && file.isFile()) {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                HandledTXTFile.appendText(text + "\n");
            }
        }
    }
}

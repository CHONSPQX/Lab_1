/**
 * 
 */
package ooform;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import oocontrol.PathControl;
import oounity.Edge;
import oounity.Graph;
import oounity.WordNode;

/**
 * @author Administrator
 *
 */
public class RandomWalkForm {
    public Graph graph;
    public JFXButton randomMoveButton;
public String randomWalk(TextArea  OutputStringArea,ImageView BottomImage) throws InterruptedException, IOException
    {
        ArrayList<Edge> edges=new ArrayList<>();
        StringBuffer fullpath=new StringBuffer();
        String label;
        int begin;
        ArrayList<WordNode> templist;
        begin=(int)(Math.random()*(graph.words.size()-1));
        label=graph.words.get(begin);
        templist=graph.maplink.get(label);
       OutputStringArea.clear();
       OutputStringArea.appendText(label+'\n');
       fullpath.append(label);
        while(templist.size()>0&&!Thread.interrupted())
        {
                Thread.sleep(1000);
                WordNode tempnode=templist.get((int)(Math.random()*(templist.size()-1)));
                OutputStringArea.appendText(tempnode.label+"\n");
                fullpath.append(" ").append(tempnode.label);
                PathControl.setGraph(graph);
                PathControl pc=new PathControl();
                String temppathfile=pc.BuildPathDot(fullpath.toString(), -1);
                String temppic=pc.SaveAsPiture(temppathfile);
                FileInputStream inputStream = new FileInputStream(temppic);
                Image image = new Image(inputStream);
                BottomImage.setImage(image);
                if(edges.contains(new Edge(label, tempnode.label)))
                        break;
                else
                {
                        edges.add(new Edge(label, tempnode.label));
                        templist=graph.maplink.get(tempnode.label);
                } 
                Thread.sleep(1000);
        }
        if(Thread.interrupted())
        {
            OutputStringArea.appendText("randomWalk interrupted\n");
            return new String(fullpath);
        }
        try {  
            FileOutputStream out=new FileOutputStream("randomPath"+".txt");
            OutputStreamWriter writer =new OutputStreamWriter(out);
            if(edges.isEmpty())
                writer.write(label);
            else
            {
                writer.write(edges.get(0).from);
                for(Edge e:edges)
                        writer.write(" "+e.to);
            }
                writer.close();
                 OutputStringArea.appendText("new random path has been saved in file\n");
            } catch (Exception e) 
            {  
                e.printStackTrace();  
            }  
            return new String(fullpath); 
    }
    private void randomMove(boolean GraphExist,int Mode,ImageView BottomImage,Image initialImage,TextArea OutputStringArea) {
        if (!GraphExist) {
            return;
        }
        Mode = 6;
        BottomImage.setImage(initialImage);
        final Thread randmoveThread=new Thread(){
                    public void run(){
                        try{
                            randomWalk(OutputStringArea,BottomImage);
                        }catch(Exception e){
                            OutputStringArea.appendText("randomWalk is interrupted\n");
                           // e.printStackTrace();
                         }
                    }
                };
       randomMoveButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                   randmoveThread.interrupt();
                   BottomImage.setImage(initialImage);
                }
            }
        });
        randmoveThread.start();    
    }
}

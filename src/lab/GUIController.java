package lab;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import jdk.nashorn.internal.parser.TokenType;
import oocontrol.*;
import oounity.*;
import ooform.*;
/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class GUIController implements Initializable {

    @FXML
    private MenuItem OpenFileIcon;
    @FXML
    private TextArea InitialTXTFile;
    @FXML
    private TextArea HandledTXTFile;
    @FXML
    private JFXButton fileTransButton;
    @FXML
    private JFXButton getPictureButton;
    @FXML
    private JFXButton getBridgewordButton;
    @FXML
    private JFXButton getPathButton;
    @FXML
    private JFXButton getNewtringButton;
    @FXML
    private JFXButton randomMoveButton;
    @FXML
    private AnchorPane TopPane;
    @FXML
    private AnchorPane MeidumPane;
    @FXML
    private AnchorPane BottomPane;
    @FXML
    private ImageView MediumPaneImage;
    @FXML
    private HBox TopTxtBox;
    @FXML
    private JFXTextField BeginWord;
    @FXML
    private JFXTextField EndWord;
    @FXML
    private TextArea InfomationFeild;
    @FXML
    private ListView<String> PathList;
    @FXML
    private TextArea InputStringArea;
    @FXML
    private JFXButton SubmitSTRButton;
    @FXML
    private JFXButton ClearSTRButton;
    @FXML
    private TextArea OutputStringArea;
    @FXML
    private JFXButton SubmitWORDButton;
    @FXML
    private JFXButton ClearWORDButton;
    @FXML
    private HBox MediumPaneBox;
    @FXML
    private ImageView BottomImage;
   

    //
    private Graph graph;
    private String initialTXTfilename;
    private String handledTXTfilename;
    private String initialPicturename;
    private Image initialImage;
    private ArrayList<String> PathPictureList;
    private String defaultcolor;
    private int Mode;
    private boolean GraphExist;
    private boolean PathhasEnd;
    @FXML
    private BorderPane stage;
    @FXML
    private Menu exitButton;
    public FileForm ff;
    public BridgeWordsForm bf;
    public NewStringForm nf;
    public PathForm pf;
    public RandomWalkForm rf;
    public ImageWindow iw;
   
    
    
    public void showDirectedGraph(ImageView view)
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
    
    public String queryBridgeWords(String word1,String word2) throws IOException
    {
        BridgeWordsControl.setGraph(graph);
        BridgeWordsControl bc=new BridgeWordsControl();
        return bc.queryBridgeWords(word1, word2);
    }
    
    public String generateNewText(String inputText)
    {
        NewStringControl.setGraph(graph);
        NewStringControl nc=new NewStringControl();
        return nc.getNewString(inputText);
    }
    
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
    
    public String randomWalk() throws InterruptedException, IOException
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Mode = 0;
        GraphExist = false;
        defaultcolor = fileTransButton.getStyle();
        ChangeItemsColor();
        SetVisiable();
        InitialTXTFile.setEditable(false);
        InitialTXTFile.setWrapText(true);

        HandledTXTFile.setEditable(false);
        HandledTXTFile.setWrapText(true);

        InfomationFeild.setEditable(false);
        InfomationFeild.setWrapText(true);

        InputStringArea.setWrapText(true);

        OutputStringArea.setEditable(false);
        OutputStringArea.setWrapText(true);
        bf=new BridgeWordsForm();
        ff=new FileForm();
        iw=new ImageWindow();
        nf=new NewStringForm();
        pf=new PathForm();
        rf=new RandomWalkForm();
    }

    private void ChangeItemsColor() {
        switch (Mode) {
            case 0:
                fileTransButton.setStyle(defaultcolor);
                getPictureButton.setStyle(defaultcolor);
                getBridgewordButton.setStyle(defaultcolor);
                getPathButton.setStyle(defaultcolor);
                getNewtringButton.setStyle(defaultcolor);
                randomMoveButton.setStyle(defaultcolor);
                break;
            case 1:
                fileTransButton.setStyle("-fx-background-color:#1874CD");
                getPictureButton.setStyle(defaultcolor);
                getBridgewordButton.setStyle(defaultcolor);
                getPathButton.setStyle(defaultcolor);
                getNewtringButton.setStyle(defaultcolor);
                randomMoveButton.setStyle(defaultcolor);
                break;
            case 2:
                fileTransButton.setStyle(defaultcolor);
                getPictureButton.setStyle("-fx-background-color:#1874CD");
                getBridgewordButton.setStyle(defaultcolor);
                getPathButton.setStyle(defaultcolor);
                getNewtringButton.setStyle(defaultcolor);
                randomMoveButton.setStyle(defaultcolor);
                break;
            case 3:
                fileTransButton.setStyle(defaultcolor);
                getPictureButton.setStyle(defaultcolor);
                getBridgewordButton.setStyle("-fx-background-color:#1874CD");
                getPathButton.setStyle(defaultcolor);
                getNewtringButton.setStyle(defaultcolor);
                randomMoveButton.setStyle(defaultcolor);
                break;
            case 4:
                fileTransButton.setStyle(defaultcolor);
                getPictureButton.setStyle(defaultcolor);
                getBridgewordButton.setStyle(defaultcolor);
                getPathButton.setStyle("-fx-background-color:#1874CD");
                getNewtringButton.setStyle(defaultcolor);
                randomMoveButton.setStyle(defaultcolor);
                break;
            case 5:
                fileTransButton.setStyle(defaultcolor);
                getPictureButton.setStyle(defaultcolor);
                getBridgewordButton.setStyle(defaultcolor);
                getPathButton.setStyle(defaultcolor);
                getNewtringButton.setStyle("-fx-background-color:#1874CD");
                randomMoveButton.setStyle(defaultcolor);
                break;
            case 6:
                fileTransButton.setStyle(defaultcolor);
                getPictureButton.setStyle(defaultcolor);
                getBridgewordButton.setStyle(defaultcolor);
                getPathButton.setStyle(defaultcolor);
                getNewtringButton.setStyle(defaultcolor);
                randomMoveButton.setStyle("-fx-background-color:#1874CD");
                break;
            default:
                break;
        }
    }

    private void SetVisiable() {
        switch (Mode) {
            case 0:
                TopPane.setVisible(true);
                MeidumPane.setVisible(false);
                MediumPaneImage.setFitWidth(650);
                MediumPaneImage.setFitWidth(680);
                MediumPaneImage.setPreserveRatio(false);
                BottomImage.setFitWidth(650);
                BottomImage.setFitWidth(680);
                BottomImage.setPreserveRatio(false);
                break;
            case 1:
                TopPane.setVisible(true);
                MeidumPane.setVisible(false);
                TopTxtBox.setVisible(true);
                break;
            case 2:
                TopPane.setVisible(true);
                TopTxtBox.setVisible(false);
                MeidumPane.setVisible(true);
                BottomPane.setVisible(false);
                MediumPaneBox.setVisible(true);
                InfomationFeild.setVisible(true);
                PathList.setVisible(false);
                break;
            case 3:
                TopPane.setVisible(true);
                TopTxtBox.setVisible(false);
                MeidumPane.setVisible(true);
                BottomPane.setVisible(false);
                MediumPaneBox.setVisible(true);
                InfomationFeild.setVisible(true);
                PathList.setVisible(false);
                break;
            case 4:
                TopPane.setVisible(true);
                TopTxtBox.setVisible(false);
                MeidumPane.setVisible(true);
                BottomPane.setVisible(false);
                MediumPaneBox.setVisible(true);
                InfomationFeild.setVisible(false);
                PathList.setVisible(true);
                break;
            case 5:
                TopPane.setVisible(true);
                TopTxtBox.setVisible(false);
                MeidumPane.setVisible(true);
                BottomPane.setVisible(true);
                MediumPaneBox.setVisible(false);
            case 6:
                TopPane.setVisible(true);
                TopTxtBox.setVisible(false);
                MeidumPane.setVisible(true);
                BottomPane.setVisible(true);
                MediumPaneBox.setVisible(false);
                break;
            default:
                break;
        }
    }

    @FXML
    private void Opentxtfile(ActionEvent event) throws IOException {
        Mode = 0;
        GraphExist = false;
        ChangeItemsColor();
        SetVisiable();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        initialTXTfilename = chooser.getSelectedFile().getName();
        InitialTXTFile.setWrapText(true);
        InitialTXTFile.setText(initialTXTfilename + "\n");
        File file = new File(initialTXTfilename);
        if (file.exists() && file.isFile()) {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                InitialTXTFile.appendText(text + "\n");
            }
        }
    }

    @FXML
    private void fileTrans(ActionEvent event) throws IOException {
        if (initialTXTfilename == null) {
            return;
        }
        Mode = 1;
        ChangeItemsColor();
        SetVisiable();
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

    @FXML
    private void getPicture(ActionEvent event) {
        long startTime = System.currentTimeMillis();    //获取开始时间
        Mode = 2;
        ChangeItemsColor();
        SetVisiable();
        try {
            graph = new Graph(handledTXTfilename);
            GraphControl.setGraph(graph);
            GraphControl gc=new GraphControl();
            String picture = gc.SaveAsPiture();
            initialPicturename = picture;
            FileInputStream inputStream = new FileInputStream(picture);
            Image image = new Image(inputStream);
            initialImage = image;
            MediumPaneImage.setImage(image);
            GraphExist = true;
        } catch (IOException e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Build a new MyGraph class failed!");
            alert.showAndWait();
        }
    }

    @FXML
    private void getBridgeword(ActionEvent event) throws IOException {
        if (!GraphExist) {
            return;
        }
        Mode = 3;
        ChangeItemsColor();
        SetVisiable();
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

    @FXML
    private void getPath(ActionEvent event) throws IOException {
        if (!GraphExist) {
            return;
        }
        Mode = 4;
        ChangeItemsColor();
        SetVisiable();
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

    @FXML
    private void getNewstring(ActionEvent event) {
        if (!GraphExist) {
            return;
        }
        Mode = 5;
        ChangeItemsColor();
        SetVisiable();
        BottomImage.setImage(initialImage);
        String inputString = InputStringArea.getText();
        if (!inputString.isEmpty()) {
            NewStringControl.setGraph(graph);
            NewStringControl nc=new NewStringControl();
            String outcome = nc.getNewString(inputString);
            OutputStringArea.setText(outcome);
        }
    }

    @FXML
    private void randomMove(ActionEvent event) {
        if (!GraphExist) {
            return;
        }
        Mode = 6;
        ChangeItemsColor();
        SetVisiable();
        BottomImage.setImage(initialImage);
        final Thread randmoveThread=new Thread(){
                    public void run(){
                        try{
                            randomWalk();
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
      
    @FXML
    private void WordSubmit(ActionEvent event) throws IOException {
        if (!GraphExist) {
            return;
        }
        if (Mode == 3) {
            String beginString = BeginWord.getText();
            String endsString = EndWord.getText();
            if (beginString.isEmpty() || endsString.isEmpty()) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("please enter in all feilds");
                alert.showAndWait();
                MediumPaneImage.setImage(initialImage);
                return;
            }
            getBridgeword(event);
        } else if (Mode == 4) {
            getPath(event);
        }
    }

    @FXML
    private void WordClear(ActionEvent event) throws IOException {
        BeginWord.setText("");
        EndWord.setText("");
        InfomationFeild.setText("");
        MediumPaneImage.setImage(initialImage);
    }

   /* @FXML
    private void PrintSelectedPathPicture(MouseEvent event) throws FileNotFoundException {
        if (!GraphExist) {
            return;
        }
        if(PathhasEnd)
        {
            int Pathnumber = PathList.getSelectionModel().getSelectedIndex() - 1;
            String PathString = PathList.getSelectionModel().getSelectedItem();
            if (Pathnumber >= 0) {
                FileInputStream inputStream = new FileInputStream(graph.FindPictureOfPath(PathString, Pathnumber));
                Image image = new Image(inputStream);
                MediumPaneImage.setImage(image);
            } else {
                return;
            }
        }
        else
        {
            String PathString = PathList.getSelectionModel().getSelectedItem();
            FileInputStream inputStream = new FileInputStream(graph.FindPictureOfPath(PathString.substring(0,PathString.lastIndexOf(" ")), -1));
            Image image = new Image(inputStream);
            MediumPaneImage.setImage(image);
        }
    }*/

    @FXML
    private void StringSubmit(ActionEvent event) {
        getNewstring(event);
    }

    @FXML
    private void StringClear(ActionEvent event) {
        InputStringArea.clear();
        OutputStringArea.clear();
        PathList.getItems().clear();
        InfomationFeild.clear();
    }

    @FXML
    private void SystemExit(ActionEvent event) {
    }
    @FXML
    private void ShowLargeImage(ActionEvent event) {
    }
    @FXML
    private void ShowLargeImage(MouseEvent event) {
    }
  
    @FXML
    private void PrintSelectedPathPicture(MouseEvent event) {
    }
}



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package functions;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * FXML Controller class.
 *
 * @author Administrator
 */
public class Lab1GuiController implements Initializable {
    
    @FXML
    private MenuItem openFileIcon;
    @FXML
    private TextArea initialTxtFile;
    @FXML
    private TextArea handledTxtFile;
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
    private AnchorPane topPane;
    @FXML
    private AnchorPane meidumPane;
    @FXML
    private AnchorPane bottomPane;
    @FXML
    private ImageView mediumPaneImage;
    @FXML
    private HBox topTxtBox;
    @FXML
    private JFXTextField beginWord;
    @FXML
    private JFXTextField endWord;
    @FXML
    private TextArea infomationFeild;
    @FXML
    private ListView<String> pathList;
    @FXML
    private TextArea inputStringArea;
    @FXML
    private JFXButton submitStrButton;
    @FXML
    private JFXButton clearStrButton;
    @FXML
    private TextArea outputStringArea;
    @FXML
    private JFXButton submitWordButton;
    @FXML
    private JFXButton clearWordButton;
    @FXML
    private HBox mediumPaneBox;
    @FXML
    private ImageView bottomImage;
    /**
     * Initializes the controller class.
     */
    
    //
    private MyGraph graph;
    private String initialTxtFilename;
    private String handledTxtFilename;
    private String initialPicturename;
    private Image initialImage;
    private ArrayList<String> pathPictureList;
    private String defaultcolor;
    private int mode;
    private boolean graphExist;
    private boolean pathhasEnd;
    @FXML
    private BorderPane stage;
    @FXML
    private Menu exitButton;
    
    public void showDirectedGraph(ImageView view) {
        try {
            graph = new MyGraph(handledTxtFilename);
            String picture = graph.SaveAsPiture();
            initialPicturename = picture;
            FileInputStream inputStream = new FileInputStream(picture);
            Image image = new Image(inputStream);
            initialImage = image;
            view.setImage(image);
            graphExist = true;
        } catch (IOException e) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Build a new MyGraph class failed!");
            alert.showAndWait();
        }
    }
    
    public String queryBridgeWords(String word1,String word2) throws IOException {
        return graph.PrintBridgeWords(word1, word2);
    }
    //根据bridge word生成新文本
    public String generateNewText(String inputText) {
        return graph.getNewString(inputText);
    }
    //计算两个单词之间的最短路径
    public String calcShorttestPath(String word1,String word2) throws IOException {
        if(word2.isEmpty())
            return graph.Dijistra(word1);
        else
            return graph.Dijistra(word1, word2);
    }
    
    public String randomWalk() throws InterruptedException, IOException//随机游走
    {
        ArrayList<Edge> edges=new ArrayList<>();
        StringBuffer fullpath=new StringBuffer();
        String label;
        int begin;
        ArrayList<WordNode> templist;
        begin=(int)(Math.random()*(graph.words.size()));
        label=graph.words.get(begin);
        templist=graph.maplink.get(label);
        outputStringArea.clear();
        outputStringArea.appendText(label+'\n');
        fullpath.append(label);
        while(templist.size()>0&&!Thread.interrupted())
        {
            Thread.sleep(1000);
            WordNode tempnode=templist.get((int)(Math.random()*(templist.size())));
            if(edges.contains(new Edge(label, tempnode.label)))
                break;
            else
            {
                outputStringArea.appendText(tempnode.label+"\n");
                fullpath.append(" ").append(tempnode.label);
                String temppathfile=graph.BuildPathDot(fullpath.toString(), -1);
                String temppic=graph.SaveAsPiture(temppathfile);
                FileInputStream inputStream = new FileInputStream(temppic);
                Image image = new Image(inputStream);
                bottomImage.setImage(image);
                edges.add(new Edge(label, tempnode.label));
                templist=graph.maplink.get(tempnode.label);
                label=tempnode.label;
            }
            Thread.sleep(1000);
        }
        if(Thread.interrupted())
        {
            outputStringArea.appendText("randomWalk interrupted\n");
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
            outputStringArea.appendText("new random path has been saved in file\n");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return new String(fullpath);
    }
    
    @Override
    //初始化
    public void initialize(URL url, ResourceBundle rb) {
        mode = 0;
        graphExist = false;
        defaultcolor = fileTransButton.getStyle();
        ChangeItemsColor();
        SetVisiable();
        mediumPaneImage.setPreserveRatio(false);
        
        initialTxtFile.setEditable(false);
        initialTxtFile.setWrapText(true);
        
        handledTxtFile.setEditable(false);
        handledTxtFile.setWrapText(true);
        
        infomationFeild.setEditable(false);
        infomationFeild.setWrapText(true);
        
        inputStringArea.setWrapText(true);
        
        outputStringArea.setEditable(false);
        outputStringArea.setWrapText(true);
    }
    
    //设置各个组件颜色
    private void ChangeItemsColor() {
        switch (mode) {
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
    
    //控制各个组件的visiable属性
    private void SetVisiable() {
        switch (mode) {
            case 0:
                topPane.setVisible(true);
                meidumPane.setVisible(false);
                mediumPaneImage.setFitWidth(650);
                mediumPaneImage.setFitWidth(680);
                mediumPaneImage.setPreserveRatio(false);
                bottomImage.setFitWidth(650);
                bottomImage.setFitWidth(680);
                bottomImage.setPreserveRatio(false);
                break;
            case 1:
                topPane.setVisible(true);
                meidumPane.setVisible(false);
                topTxtBox.setVisible(true);
                break;
            case 2:
                topPane.setVisible(true);
                topTxtBox.setVisible(false);
                meidumPane.setVisible(true);
                bottomPane.setVisible(false);
                mediumPaneBox.setVisible(true);
                infomationFeild.setVisible(true);
                pathList.setVisible(false);
                break;
            case 3:
                topPane.setVisible(true);
                topTxtBox.setVisible(false);
                meidumPane.setVisible(true);
                bottomPane.setVisible(false);
                mediumPaneBox.setVisible(true);
                infomationFeild.setVisible(true);
                pathList.setVisible(false);
                break;
            case 4:
                topPane.setVisible(true);
                topTxtBox.setVisible(false);
                meidumPane.setVisible(true);
                bottomPane.setVisible(false);
                mediumPaneBox.setVisible(true);
                infomationFeild.setVisible(false);
                pathList.setVisible(true);
                break;
            case 5:
                topPane.setVisible(true);
                topTxtBox.setVisible(false);
                meidumPane.setVisible(true);
                bottomPane.setVisible(true);
                mediumPaneBox.setVisible(false);
            case 6:
                topPane.setVisible(true);
                topTxtBox.setVisible(false);
                meidumPane.setVisible(true);
                bottomPane.setVisible(true);
                mediumPaneBox.setVisible(false);
                break;
            default:
                break;
        }
    }
    
    @FXML
    //打开文本文件
    private void Opentxtfile(ActionEvent event) throws IOException {
        mode = 0;
        graphExist = false;
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
        initialTxtFilename = chooser.getSelectedFile().getName();
        initialTxtFile.setWrapText(true);
        initialTxtFile.setText(initialTxtFilename + "\n");
        File file = new File(initialTxtFilename);
        if (file.exists() && file.isFile()) {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                initialTxtFile.appendText(text + "\n");
            }
        }
    }
    
    @FXML
    //处理文本文件
    private void fileTrans(ActionEvent event) throws IOException {
        if (initialTxtFilename == null) {
            return;
        }
        mode = 1;
        ChangeItemsColor();
        SetVisiable();
        handledTxtFilename = FileTrans.trans(initialTxtFilename);
        handledTxtFile.setWrapText(true);
        handledTxtFile.setText(handledTxtFilename + "\n");
        File file = new File(handledTxtFilename);
        if (file.exists() && file.isFile()) {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                handledTxtFile.appendText(text + "\n");
            }
        }
    }
    
    @FXML
    //图片显示
    private void getPicture(ActionEvent event) {
        mode = 2;
        ChangeItemsColor();
        SetVisiable();
        /* try {
         graph = new MyGraph(handledTxtFilename);
         String picture = graph.SaveAsPiture();
         initialPicturename = picture;
         FileInputStream inputStream = new FileInputStream(picture);
         Image image = new Image(inputStream);
         initialImage = image;
         mediumPaneImage.setImage(image);
         graphExist = true;
         } catch (IOException e) {
         javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
         alert.setHeaderText(null);
         alert.setContentText("Build a new MyGraph class failed!");
         alert.showAndWait();
         }*/
        showDirectedGraph(mediumPaneImage);
    }
    
    @FXML
    //词语查询
    private void getBridgeword(ActionEvent event) throws IOException {
        if (!graphExist) {
            return;
        }
        mode = 3;
        ChangeItemsColor();
        SetVisiable();
        String beginString = beginWord.getText().toLowerCase();
        String endString = endWord.getText().toLowerCase();
        if (beginString.isEmpty() || endString.isEmpty()) {
            //javafx.scene.control.Alert alert=new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            //alert.setHeaderText(null);
            //alert.setContentText("please enter in all feilds");
            //alert.showAndWait();
            mediumPaneImage.setImage(initialImage);
            return;
        }
        beginWord.clear();
        endWord.clear();
        if(!graph.words.contains(beginString)||!graph.words.contains(endString))
        {
            if(!graph.words.contains(beginString)&&!graph.words.contains(endString))
                infomationFeild.setText("No \"" + beginString + "\" and \"" + endString + "\"in the garph!");
            else if(!graph.words.contains(endString))
                infomationFeild.setText("No \"" + endString + "\" in the graph!");
            else
                infomationFeild.setText("No \"" + beginString + "\" in the graph!");
            return;
        }
        
        String[] infoArrayList = queryBridgeWords(beginString, endString).split("\n");
        if (infoArrayList.length==1) {
            infomationFeild.setText("No Bridge Words from \"" + beginString + "\" to \"" + endString + "\"!");
            mediumPaneImage.setImage(initialImage);
        }
        else if(infoArrayList.length >= 2) {
            infomationFeild.setText("The Bridge Words from \"" + beginString + "\" to \"" + endString + "\" are : \n");
            for (int i = 1; i < infoArrayList.length; i++) {
                infomationFeild.appendText(infoArrayList[i]+"\n");
            }
            FileInputStream inputStream = new FileInputStream(infoArrayList[0]);
            Image image = new Image(inputStream);
            mediumPaneImage.setImage(image);
        }
        
    }
    
    @FXML
    //路径查询
    private void getPath(ActionEvent event) throws IOException {
        if (!graphExist) {
            return;
        }
        mode = 4;
        ChangeItemsColor();
        SetVisiable();
        String beginString = beginWord.getText().toLowerCase();
        String endsString = endWord.getText().toLowerCase();
        mediumPaneImage.setImage(initialImage);
        if (beginString.isEmpty() && endsString.isEmpty()) {
            return;
        }
        else if(endsString.isEmpty())
        {
            pathhasEnd=false;
            beginWord.clear();
            endWord.clear();
            String[] infoArrayList = calcShorttestPath(beginString,"").split("\n");
            pathList.getItems().clear();
            pathList.getItems().addAll(Arrays.asList(infoArrayList));
        }
        else
        {
            pathhasEnd=true;
            beginWord.clear();
            endWord.clear();
            String[] infoArrayList = calcShorttestPath(beginString, endsString).split("\n");
            pathList.getItems().clear();
            if(Integer.parseInt(infoArrayList[0])>MyGraph.MAX_DIS)
            {
                pathList.getItems().add("cannot reach");
                return ;
            }
            pathList.getItems().add("Distance :" + infoArrayList[0]);
            for (int i = 1; i < infoArrayList.length; i++) {
                pathList.getItems().add(infoArrayList[i]);
            }
        }
    }
    
    @FXML
    //桥接文本*
    private void getNewstring(final ActionEvent event) {
        if (!graphExist) {
            return;
        }
        mode = 5;
        ChangeItemsColor();
        SetVisiable();
        bottomImage.setImage(initialImage);
        String inputString = inputStringArea.getText().toLowerCase();
        if (!inputString.isEmpty()) {
            String outcome= generateNewText(inputString);
            outputStringArea.setText(outcome);
        }
    }
    
    @FXML
    //随机游走
    private void randomMove(ActionEvent event) {
        if (!graphExist) {
            return;
        }
        mode = 6;
        ChangeItemsColor();
        SetVisiable();
        bottomImage.setImage(initialImage);
        final Thread randmoveThread=new Thread(){
            public void run(){
                try{
                    randomWalk();
                }catch(Exception e){
                    outputStringArea.appendText("randomWalk is interrupted\n");
                    // e.printStackTrace();
                }
            }
        };
        randomMoveButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    randmoveThread.interrupt();
                    bottomImage.setImage(initialImage);
                }
            }
        });
        randmoveThread.start();
    }
    
    @FXML
    //提交单词
    private void WordSubmit(ActionEvent event) throws IOException {
        if (!graphExist) {
            return;
        }
        if (mode == 3) {
            String beginString = beginWord.getText().toLowerCase();
            String endsString = endWord.getText().toLowerCase();
            if (beginString.isEmpty() || endsString.isEmpty()) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("please enter in all feilds");
                alert.showAndWait();
                mediumPaneImage.setImage(initialImage);
                return;
            }
            getBridgeword(event);
        } else if (mode == 4) {
            getPath(event);
        }
    }
    
    @FXML
    //清除输出
    private void WordClear(ActionEvent event) throws IOException {
        beginWord.setText("");
        endWord.setText("");
        infomationFeild.setText("");
        mediumPaneImage.setImage(initialImage);
    }
    
    @FXML
    //打开用户选择的图片
    private void PrintSelectedPathPicture(MouseEvent event) throws FileNotFoundException {
        if (!graphExist) {
            return;
        }
        if(pathhasEnd)
        {
            int Pathnumber = pathList.getSelectionModel().getSelectedIndex() - 1;
            String PathString = pathList.getSelectionModel().getSelectedItem();
            if (Pathnumber >= 0) {
                FileInputStream inputStream = new FileInputStream(graph.FindPictureOfPath(PathString, Pathnumber));
                Image image = new Image(inputStream);
                mediumPaneImage.setImage(image);
            } else {
                return;
            }
        }
        else
        {
            String PathString = pathList.getSelectionModel().getSelectedItem();
            FileInputStream inputStream = new FileInputStream(graph.FindPictureOfPath(PathString.substring(0,PathString.lastIndexOf(" ")), -1));
            Image image = new Image(inputStream);
            mediumPaneImage.setImage(image);
        }
    }
    
    @FXML
    //提交新文本
    private void StringSubmit(ActionEvent event) {
        getNewstring(event);
    }
    
    @FXML
    //清除输出
    private void stringClear(ActionEvent event) {
        inputStringArea.clear();
        outputStringArea.clear();
        pathList.getItems().clear();
        infomationFeild.clear();
    }
    
    @FXML
    //退出程序
    private void SystemExit(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    //用大图展示
    private void ShowLargeImage(ActionEvent event)
    {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ImageWindow.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            ImageWindowController  childController = fxmlLoader.getController();
            Stage nstage = new Stage(StageStyle.DECORATED);
            nstage.setScene(new Scene(parent));
            nstage.setTitle("ImageViewer");
            childController.SetImage(initialImage);
            nstage.show();
        }catch(IOException e)
        {
            ;
        }
        
    }
    
}

class Edge
{
    public String from;//起点
    public String to;//终点
    Edge(String from, String to)
    {
        this.from=from;
        this.to=to;
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
        if( this.from.equals(other.from)&&this.to.equals(other.to))
            return true;
        else
            return false;
    }
}


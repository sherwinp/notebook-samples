package notebook.sample;

import java.net.URL;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.*;
 
public class NoteBookView extends Application {
    private Scene scene;
    @Override public void start(Stage stage) {
        // create the scene
        stage.setTitle("Web View");
        final Group rootGroup = new Group();
        
        rootGroup.getChildren().add(new Browser());
        scene = new Scene(rootGroup,750,500, Color.web("#666970"));
        final MenuBar menuBar = buildMenuBarWithMenus(stage.widthProperty());
        menuBar.useSystemMenuBarProperty ().set (true);
        menuBar.setUseSystemMenuBar(true);
        rootGroup.getChildren().add(menuBar);
        
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("layout.css").toExternalForm());  
        stage.show();
    }
 
    public static void main(String[] args){
        launch(args);
    }
    private MenuBar buildMenuBarWithMenus(final ReadOnlyDoubleProperty menuWidthProperty)
    {
       final MenuBar menuBar = new MenuBar();

       // Prepare left-most 'File' drop-down menu
       final Menu fileMenu = new Menu("File");
       fileMenu.getItems().add(new MenuItem("New"));
       fileMenu.getItems().add(new MenuItem("Open"));
       fileMenu.getItems().add(new MenuItem("Save"));
       fileMenu.getItems().add(new MenuItem("Save As"));
       fileMenu.getItems().add(new SeparatorMenuItem());
       fileMenu.getItems().add(new MenuItem("Exit"));
       menuBar.getMenus().add(fileMenu);

       // Prepare 'Examples' drop-down menu
       final Menu examplesMenu = new Menu("JavaFX 2.0 Examples");
       examplesMenu.getItems().add(new MenuItem("Text Example"));
       examplesMenu.getItems().add(new MenuItem("Objects Example"));
       examplesMenu.getItems().add(new MenuItem("Animation Example"));
       menuBar.getMenus().add(examplesMenu);

       // Prepare 'Help' drop-down menu
       final Menu helpMenu = new Menu("Help");
       final MenuItem searchMenuItem = new MenuItem("Search");
       searchMenuItem.setDisable(true);
       helpMenu.getItems().add(searchMenuItem);
       final MenuItem onlineManualMenuItem = new MenuItem("Online Manual");
       onlineManualMenuItem.setVisible(false);
       helpMenu.getItems().add(onlineManualMenuItem);
       helpMenu.getItems().add(new SeparatorMenuItem());
       final MenuItem aboutMenuItem =
          MenuItemBuilder.create()
                         .text("About")
                         .onAction(
                             new EventHandler<ActionEvent>()
                             {
                                @Override public void handle(ActionEvent e)
                                {
                                  
                                }
                             })
                         .accelerator(
                             new KeyCodeCombination(
                                KeyCode.A, KeyCombination.CONTROL_DOWN))
                         .build();             
       helpMenu.getItems().add(aboutMenuItem);
       menuBar.getMenus().add(helpMenu);

       // bind width of menu bar to width of associated stage
       menuBar.prefWidthProperty().bind(menuWidthProperty);

       return menuBar;
    }
}
class Browser extends Region {
 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        URL url = getClass().getResource("Index.html");
        //webEngine.load("http://css3test.com/");
        webEngine.load(url.toExternalForm());
        //add the web view to the scene
        getChildren().add(browser);
    }
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}

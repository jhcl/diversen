/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monitorcondition;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author nl08940
 */
public class RunnableVoortggang extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btnStart = new Button("Start");
        Button btnCancel = new Button("Cancel");
        ProgressBar p = new ProgressBar(0);
        btnStart.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        btnCancel.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        GridPane root = new GridPane();
        root.setHgap(20);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.add(btnStart,0,0);
        root.add(btnCancel,1,0);
        root.add(p,0,1,2,1);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uc_week5_fileread;

import java.io.File;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import unboundedbuffer.IBuffer;
import unboundedbuffer.UnboundedBuffer;
import utils.HandleRAFFilesWithThreads;
import utils.VerwerkRegelRunnableFX;

/**
 *
 * @author nl08940
 */
public class RunnableVoortgang extends Application {
    private IBuffer sb;
    private final int regelLengte = 50;
    private final int aantalThreads = 10;
    
    @Override
    public void start(Stage primaryStage) {
        
        Button btnStart = new Button("Start");
        Button btnCancel = new Button("Cancel");
        ProgressBar p = new ProgressBar(0);
        sb = new UnboundedBuffer();
        
        
        HandleRAFFilesWithThreads read = new HandleRAFFilesWithThreads(
                new File("testmon.txt"), regelLengte, aantalThreads, sb);
        read.readFile();
        Task task = new VerwerkRegelRunnableFX(regelLengte,sb);
        p.progressProperty().bind(task.progressProperty());
        Thread th = new Thread(task);
        
        btnStart.setOnAction((ActionEvent event) -> {
            th.start();
        });
        
        btnCancel.setOnAction((ActionEvent event) -> {
            task.cancel(true);
            System.out.println(task.getMessage());
//            th.interrupt();
        });
        
        GridPane root = new GridPane();
        root.setHgap(20);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.add(btnStart,0,0);
        root.add(btnCancel,1,0);
        root.add(p,0,1,2,1);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Voortgangsindicator");
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

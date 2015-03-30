/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uc_week5_fileread;

import utils.RAFFilesUtils;
import utils.HandleRAFFilesWithThreads;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import unboundedbuffer.*;
import utils.I_HandleRAFFiles;

/**
 *
 * @author nl08940
 */
public class GUI extends Application {

    private int regelLengte = 0;
    private int aantalRegels = 0;
    private int aantalThreads;
    private File file;
    private IBuffer sharedbuffer;
    private HandleRAFFilesWithThreads handleraf;
    private Label lblRegels, lblRegelLengte, lblFeedback, lblAantalThreads;
    private TextField txtRegels, txtRegelLengte, txtAantalThreads;
    private Button btnMaakAan, btnReadFileRun, btnReadBounded, btnVerwerkRegelRun, btnBeide, btnStopVerwerk;
    private final GridPane root = new GridPane();
    private final Scene scene = new Scene(root, 400, 300);

    @Override
    public void start(Stage primaryStage) {
        file = new File("test.txt");
        handleraf = null;

        addComponents();

        btnMaakAan.setOnAction((ActionEvent event) -> {
            maakFile();

        });

        btnReadFileRun.setOnAction((ActionEvent event) -> {
            sharedbuffer = new UnboundedBuffer();
            handleraf = (HandleRAFFilesWithThreads) vulBuffer(sharedbuffer);
        });

        btnReadBounded.setOnAction((ActionEvent event) -> {
            sharedbuffer = new BoundedBuffer();
            handleraf = (HandleRAFFilesWithThreads) vulBuffer(sharedbuffer);
        });

        btnVerwerkRegelRun.setOnAction((ActionEvent event) -> {
            if (handleraf != null) {
                handleraf.verwerk();
            }
        });

        btnStopVerwerk.setOnAction((ActionEvent event) -> {
            if (handleraf != null) {
                handleraf.stopVerwerk();
            }
        });

        btnBeide.setOnAction((ActionEvent event) -> {
            btnReadFileRun.getOnAction().handle(event);
            btnVerwerkRegelRun.getOnAction().handle(event);
        });

        sceneSetLayout();

        primaryStage.setTitle("Fileread");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            if (handleraf != null) {
                handleraf.stopAll();
            }

            try {
                Files.delete(Paths.get(file.getAbsolutePath()));
            } catch (IOException ex) {
                System.out.println("File was al weg of is in gebruik: " + ex.getMessage());
            }

        });

    }

    private void addComponents() {
        lblRegels = new Label("Aantal regels: ");
        txtRegels = new TextField();
        txtRegelLengte = new TextField();
        lblRegelLengte = new Label("Regellengte: ");
        lblFeedback = new Label("feedback");
        lblAantalThreads = new Label("Aantal threads: ");
        txtAantalThreads = new TextField();
        btnMaakAan = new Button("Maak aan");
        btnReadFileRun = new Button("ReadUnbounded");
        btnReadBounded = new Button("ReadBounded");
        btnVerwerkRegelRun = new Button("VerwRegelRun");
        btnBeide = new Button("Beide");
        btnStopVerwerk = new Button("Stop verwerk");
    }

    private void sceneSetLayout() {
        root.getColumnConstraints().add(new ColumnConstraints(110));
        root.getColumnConstraints().add(new ColumnConstraints(100));
        root.getColumnConstraints().add(new ColumnConstraints(80));
        root.setHgap(10);
        root.setVgap(10);
        root.addRow(0, lblRegels, txtRegels, lblRegelLengte, txtRegelLengte);
        root.addRow(1, btnMaakAan);
        root.add(lblFeedback, 1, 1, 3, 1);
        root.addRow(2, lblAantalThreads, txtAantalThreads);
        root.add(btnReadFileRun, 0, 3);
        root.add(btnReadBounded, 1, 3);
        root.add(btnVerwerkRegelRun, 0, 4);
        root.add(btnBeide, 0, 5);
        root.add(btnStopVerwerk, 0, 6);

    }

    private void maakFile() {
        try {
            if ("".equals(txtRegelLengte.getText())) {
                regelLengte = 50;
            } else {
                regelLengte = Integer.parseInt(txtRegelLengte.getText());
            }
            if ("".equals(txtRegels.getText())) {
                aantalRegels = 1000;
            } else {
                aantalRegels = Integer.parseInt(txtRegels.getText());
            }
            RAFFilesUtils.writeFile(file, regelLengte, aantalRegels);
            lblFeedback.setText("File " + file.getName() + " created.");
        } catch (NumberFormatException e) {
            lblFeedback.setText("Not a Number.");
        }
    }

    private I_HandleRAFFiles vulBuffer(IBuffer buf) {
        HandleRAFFilesWithThreads hr = null;
        try {

            if ("".equals(txtAantalThreads.getText())) {
                aantalThreads = 2;
            } else {
                aantalThreads = Integer.parseInt(txtAantalThreads.getText());
            }
            if (file.exists()) {
                hr = new HandleRAFFilesWithThreads(file, regelLengte, aantalThreads, buf);
                hr.readFile(file, regelLengte);
            } else {
                lblFeedback.setText("File " + file + " bestaat niet.");
            }

        } catch (NumberFormatException e) {
            lblFeedback.setText("Not a Number.");
        }
        return hr;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

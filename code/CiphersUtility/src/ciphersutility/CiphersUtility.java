package ciphersutility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javafx.application.Application;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author _
 */
public class CiphersUtility extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Cipher Selection Box at top of page
        HBox cipherselectionHBox = new HBox();
        Label cipherSelectLabel = new Label("Select Cipher Type:");
        ComboBox cipherSelection = new ComboBox();
        cipherSelection.getItems().addAll(
            "Single Shift Cipher",
            "Double Shift Cipher"
        );
        cipherselectionHBox.getChildren().addAll(cipherSelectLabel, cipherSelection);
        cipherselectionHBox.setSpacing(10);
        cipherselectionHBox.setAlignment(Pos.CENTER);
        
        // Decrypt and Encrypt Tabs in center of page
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        Tab encryptTab = new Tab();
        Tab decryptTab = new Tab();
        
        // Always present feedback console at the bottom of page     
        TextArea logConsole = new TextArea();
        Console console = new Console(logConsole);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);
        logConsole.setEditable(false);
        logConsole.setText("Enter values and press button to begin \n");   
        logConsole.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 12px;");
        logConsole.setPrefHeight(115);
        logConsole.setPrefWidth(40);      
        
        /* Encrypt ShiftOne Tab */
        encryptTab.setText("Encrypt");
        
        // Plaintext field with validation
        Label plaintextLabel = new Label("Plaintext:");
        TextField plaintextField = new TextField ();
        HBox plaintextHBox = new HBox();
        plaintextHBox.getChildren().addAll(plaintextLabel, plaintextField);
        plaintextHBox.setSpacing(10);
        
        plaintextField.focusedProperty().addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue) {
                if (!plaintextField.getText().matches("[a-zA-Z]+")) {
                    plaintextField.setText("");
                    logConsole.setText("Please only enter letters a-z (case insensitive)");
                }
              }
            }
          );
        
        // Key field with validation
        Label keyLabel = new Label("Key:");
        TextField keyField = new TextField ();
        HBox keyHBox = new HBox();
        keyHBox.getChildren().addAll(keyLabel, keyField);
        keyHBox.setSpacing(10);
       
        // Encrypt button to begin encryption process 
        Button encryptShiftOne = new Button();
        encryptShiftOne.setText("Begin Encryption.");
        encryptShiftOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plaintextField.getText() != null && keyField.getText() != null) {
                    logConsole.setText("Encrypting... \n");
                    int key = Integer.parseInt(keyField.getText()); 
                    String plaintext = (String)plaintextField.getText();
                    Ciphers cipher = new ShiftOne("", plaintext, key);
                    cipher.encrypt();
                }
            }
        });
        
        // Populate Encrypt Shift One tab with contents
        VBox encryptShiftOneVBox = new VBox();
        encryptShiftOneVBox.getChildren().addAll(plaintextHBox, keyHBox, encryptShiftOne);
        encryptTab.setContent(encryptShiftOneVBox);
        tabPane.getTabs().add(encryptTab);
        
        /* Decrypt ShiftOne Tab */
        decryptTab.setText("Decrypt");
        
        // Plaintext field with validation
        Label plaintextLabelD = new Label("Plaintext:");
        TextField plaintextFieldD = new TextField ();
        HBox plaintextHBoxD = new HBox();
        plaintextHBoxD.getChildren().addAll(plaintextLabelD, plaintextFieldD);
        plaintextHBoxD.setSpacing(10);
        
        plaintextFieldD.focusedProperty().addListener(
            (observable, oldValue, newValue) -> {
              if (!newValue) {
                if (!plaintextField.getText().matches("[a-zA-Z]+")) {
                    plaintextField.setText("");
                    logConsole.setText("Please only enter letters a-z (case insensitive) \n");
                }
              }
            }
        );
        
        // Key field with validation
        Label keyLabelD = new Label("Key:");
        TextField keyFieldD = new TextField ();
        HBox keyHBoxD = new HBox();
        keyHBoxD.getChildren().addAll(keyLabelD, keyFieldD);
        keyHBoxD.setSpacing(10);
       
        // Encrypt button to begin encryption process 
        Button decryptShiftOne = new Button();
        decryptShiftOne.setText("Begin Decryption.");
        decryptShiftOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plaintextFieldD.getText() != null && keyFieldD.getText() != null) {
                    logConsole.setText("Decrypting... \n");
                    int key = Integer.parseInt(keyField.getText()); 
                    String plaintext = (String)plaintextFieldD.getText();
                    Ciphers cipher = new ShiftOne("", plaintext, key);
                    cipher.decrypt();
                }
            }
        });
        
        // Populate Encrypt Shift One tab with contents
        VBox decryptShiftOneVBox = new VBox();
        decryptShiftOneVBox.getChildren().addAll(plaintextHBoxD, keyHBoxD, decryptShiftOne);
        decryptTab.setContent(decryptShiftOneVBox);
        tabPane.getTabs().add(decryptTab);

        
        BorderPane root = new BorderPane();
        root.setTop(cipherselectionHBox);
        root.setCenter(tabPane);
        root.setBottom(logConsole);
        
        Scene scene = new Scene(root, 400, 300);
        
        primaryStage.setTitle("Basic Cipher Utility");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static class Console extends OutputStream {

        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException {
            output.appendText(String.valueOf((char) i));
        }
    }
    
}

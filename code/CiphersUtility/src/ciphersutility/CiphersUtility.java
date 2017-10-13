package ciphersutility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

/* GUI class to display and run single and double shift ciphers */
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
        cipherSelection.setValue("Single Shift Cipher");
        cipherselectionHBox.getChildren().addAll(cipherSelectLabel, cipherSelection);
        cipherselectionHBox.setSpacing(9);
        cipherselectionHBox.setPadding(new Insets(7, 7, 7, 7));
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
        logConsole.setStyle("-fx-highlight-fill: lightgray; -fx-highlight-text-fill: firebrick; -fx-font-size: 16px;");
        logConsole.setPrefHeight(150);
        /* Encrypt ShiftOne Tab */
        encryptTab.setText("Encrypt");

        // Encryption Plaintext field 
        Label plaintextLabel = new Label("Plaintext:");
        TextField plaintextField = new TextField();
        HBox plaintextHBox = new HBox();
        plaintextHBox.getChildren().addAll(plaintextLabel, plaintextField);
        plaintextHBox.setSpacing(16);

        // Encryption field for key
        Label keyLabel = new Label("Key:");
        TextField keyField = new TextField();
        HBox keyHBox = new HBox();
        keyHBox.getChildren().addAll(keyLabel, keyField);
        keyHBox.setSpacing(42);
        // Visibility is toggled based on cipher type
        keyHBox.managedProperty().bind(keyHBox.visibleProperty());

        // Double encryption field for key1 
        Label key1Label = new Label("Key1: ");
        TextField key1Field = new TextField();
        HBox key1HBox = new HBox();
        key1HBox.getChildren().addAll(key1Label, key1Field);
        key1HBox.setSpacing(32);
        // Visibility is toggled based on cipher type. Invisible by default
        key1HBox.managedProperty().bind(key1HBox.visibleProperty());
        key1HBox.setVisible(false);

        // Double encryption field for key2 
        Label key2Label = new Label("Key2: ");
        TextField key2Field = new TextField();
        HBox key2HBox = new HBox();
        key2HBox.getChildren().addAll(key2Label, key2Field);
        key2HBox.setSpacing(32);
        // Visibility is toggled based on cipher type. Invisible by default
        key2HBox.managedProperty().bind(key2HBox.visibleProperty());
        key2HBox.setVisible(false);

        // Encryption button to begin encryption process 
        Button encryptShiftOne = new Button();
        encryptShiftOne.setText("Begin Single Encryption.");
        encryptShiftOne.managedProperty().bind(encryptShiftOne.visibleProperty());
        encryptShiftOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plaintextField.getText() != null && keyField.getText() != null) {
                    // Check for valid text and key input before decrypting 
                    try {
                        logConsole.setText(""); // Clear console
                        boolean proceed = true;
                        // If invalid text set textfield to empty
                        if (!plaintextField.getText().matches("[a-zA-Z ]+")) {
                            proceed = false;
                            plaintextField.setText(plaintextField.getText().replaceAll("[^A-Za-z]+", "").toLowerCase());
                            logConsole.appendText("Please only enter letters a-z (case insensitive) \n");
                        }
                        // Get key and plaintext. If key isn't an integer, raise error. 
                        int key = Integer.parseInt(keyField.getText());
                        String plaintext = ((String) plaintextField.getText()).replaceAll("[^A-Za-z]+", "").toLowerCase();
                        if (key < 25 && key > -25 && proceed) {
                            logConsole.appendText("Encrypting   ... \n");
                            ShiftOne cipher = new ShiftOne(plaintext, "", key);
                            cipher.encrypt();
                            printResults(Integer.toString(key), cipher.getCiphertext(), plaintext);
                            // If key not in range, set key field to empty
                        } else if (key > 25 && key < -25) {
                            keyField.setText("");
                            logConsole.appendText("Key must be an integer between -25 through 25");
                        }
                    } catch (Exception e) {
                        keyField.setText("");
                        logConsole.appendText("Please enter an integer for your key.");
                    }
                }
            }
        });

        // Encryption button to begin double encryption process 
        Button encryptShiftTwo = new Button();
        encryptShiftTwo.setText("Begin Double Encryption.");
        encryptShiftTwo.managedProperty().bind(encryptShiftTwo.visibleProperty());
        encryptShiftTwo.setVisible(false);
        encryptShiftTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (plaintextField.getText() != null && key1Field.getText() != null && key2Field.getText() != null) {
                    // Check for valid text and key input before decrypting 
                    try {
                        logConsole.setText(""); // Clear console
                        boolean proceed = true;
                        // If invalid text set textfield to empty
                        if (!plaintextField.getText().matches("[a-zA-Z ]+")) {
                            proceed = false;
                            plaintextField.setText(plaintextField.getText().replaceAll("[^A-Za-z]+", "").toLowerCase());
                            logConsole.appendText("Please only enter letters a-z (case insensitive) \n");
                        }
                        // Get key and plaintext. If key isn't an integer, raise error. 
                        int key1 = Integer.parseInt(key1Field.getText());
                        int key2 = Integer.parseInt(key2Field.getText());
                        String plaintext = ((String) plaintextField.getText()).replaceAll("[^A-Za-z]+", "").toLowerCase();

                        if (key1 < 25 && key1 > -25 && key2 < 25 && key2 > -25 && proceed) {
                            logConsole.appendText("Double Encrypting... \n");
                            ShiftTwo cipher = new ShiftTwo(plaintext, "", key1, key2);
                            cipher.encrypt();
                            printResults(Integer.toString(key1), Integer.toString(key2), cipher.getCiphertext(), plaintext);
                            // If key not in range, set key field to empty
                        } else if (key1 < 25 && key1 > -25 && key2 < 25 && key2 > -25) {
                            keyField.setText("");
                            logConsole.appendText("Key(s) must be an integer between -25 through 25");
                        }
                    } catch (Exception e) {
                        keyField.setText("");
                        logConsole.appendText("Please enter an integer for your key(s).");
                    }
                }
            }
        });

        // Populate Encrypt Shift tab with contents
        VBox encryptShiftVBox = new VBox(10);
        encryptShiftVBox.getChildren().addAll(plaintextHBox, keyHBox, key1HBox, key2HBox, encryptShiftOne, encryptShiftTwo);
        encryptShiftVBox.setPadding(new Insets(15, 15, 0, 15));
        encryptTab.setContent(encryptShiftVBox);
        tabPane.getTabs().add(encryptTab);
        tabPane.setPrefHeight(300);

        /* Decrypt ShiftOne Tab */
        decryptTab.setText("Decrypt");

        // Decryption plaintext field
        Label ciphertextLabelD = new Label("Ciphertext:");
        TextField ciphertextField = new TextField();
        HBox plaintextHBoxD = new HBox();
        plaintextHBoxD.getChildren().addAll(ciphertextLabelD, ciphertextField);
        plaintextHBoxD.setSpacing(6);

        // Decryption Key field 
        Label keyLabelD = new Label("Key:");
        TextField keyFieldD = new TextField();
        HBox keyHBoxD = new HBox();
        keyHBoxD.getChildren().addAll(keyLabelD, keyFieldD);
        keyHBoxD.setSpacing(42);
        keyHBoxD.managedProperty().bind(keyHBoxD.visibleProperty());

        // Double Decryption Key1 field 
        Label key1LabelD = new Label("Key1: ");
        TextField key1FieldD = new TextField();
        HBox key1HBoxD = new HBox();
        key1HBoxD.getChildren().addAll(key1LabelD, key1FieldD);
        key1HBoxD.setSpacing(32);
        // Visibility is toggled based on cipher type. Invisible by default
        key1HBoxD.managedProperty().bind(key1HBoxD.visibleProperty());
        key1HBoxD.setVisible(false);

        // Double Decryption Key2 field 
        Label key2LabelD = new Label("Key2: ");
        TextField key2FieldD = new TextField();
        HBox key2HBoxD = new HBox();
        key2HBoxD.getChildren().addAll(key2LabelD, key2FieldD);
        key2HBoxD.setSpacing(32);
        // Visibility is toggled based on cipher type. Invisible by default
        key2HBoxD.managedProperty().bind(key2HBoxD.visibleProperty());
        key2HBoxD.setVisible(false);

        // Checkbox to toggle automatic decryption and list all possibilities
        CheckBox automaticDecyptOption = new CheckBox("Automatic Decrypt");
        automaticDecyptOption.setSelected(false);
        CheckBox listAllOption = new CheckBox("List All Decrypted");
        automaticDecyptOption.setSelected(false);
        listAllOption.setDisable(true);

        // Combobox for number of possibilities to list
        Label numOfPossibilitesLabelOne = new Label("List  ");
        ComboBox<Integer> numOfPossibilites = new ComboBox<Integer>();
        numOfPossibilites.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15);
        numOfPossibilites.setValue(1);
        Label numOfPossibilitesLabelTwo = new Label("  Possibilities");
        HBox possibilitesHBox = new HBox();
        possibilitesHBox.getChildren().addAll(numOfPossibilitesLabelOne,
                numOfPossibilites,
                numOfPossibilitesLabelTwo);

        // Add checkboxes and possibilities to VBox
        VBox checkboxVBox = new VBox();
        checkboxVBox.getChildren().addAll(automaticDecyptOption, possibilitesHBox, listAllOption);

        // Add listener for the automatic decryption option
        automaticDecyptOption.selectedProperty().addListener((observable, oldValue, newValue)
                -> {
            // Toggle Key Entries and List All Checkbox when checked
            if (automaticDecyptOption.isSelected()) {
                keyFieldD.setDisable(true);
                key1FieldD.setDisable(true);
                key2FieldD.setDisable(true);
                listAllOption.setDisable(false);
            } else if (!automaticDecyptOption.isSelected()) {
                keyFieldD.setDisable(false);
                key1FieldD.setDisable(false);
                key2FieldD.setDisable(false);
                listAllOption.setDisable(true);
            }
        }
        );
        // Add listener for all possibilites checkbox 
        listAllOption.selectedProperty().addListener((observable, oldValue, newValue)
                -> {
            if (listAllOption.isSelected()) {
                numOfPossibilites.setDisable(true);
            } else if (!listAllOption.isSelected()) {
                numOfPossibilites.setDisable(false);
            }
        }
        );

        // Decryption button to begin decryption process 
        Button decryptShiftOne = new Button();
        decryptShiftOne.setText("Begin Single Decryption.");
        decryptShiftOne.managedProperty().bind(decryptShiftOne.visibleProperty());
        decryptShiftOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ciphertextField.getText() != null && keyFieldD.getText() != null) {
                    // Check for valid text and key input before decrypting 
                    try {
                        boolean proceed = true;
                        logConsole.setText(""); // Clear Console
                        // If invalid text set textfield to empty
                        if (!ciphertextField.getText().matches("[a-zA-Z ]+")) {
                            proceed = false;
                            ciphertextField.setText(ciphertextField.getText().replaceAll("[^A-Za-z]+", "").toLowerCase());
                            logConsole.appendText("Please only enter letters a-z (case insensitive) \n");
                        }
                        /* Automatic Decryption */
                        if (automaticDecyptOption.isSelected() && proceed) {
                            String ciphertext = ((String) ciphertextField.getText()).replaceAll("[^A-Za-z]+", "").toLowerCase();
                            ShiftOne cipher = new ShiftOne("", ciphertext);

                            System.out.println("Ciphertext: " + ciphertext);
                            if (listAllOption.isSelected()) {
                                cipher.decryptAutomatic(-1);
                            } else {
                                cipher.decryptAutomatic((int) numOfPossibilites.getValue());
                            }
                        } else {
                            /* Manual Decryption */
                            // Parse to check for integer 
                            int key = Integer.parseInt(keyFieldD.getText());

                            // If key and text are validated, decypt ciphertext
                            if (key < 25 && key > -25 && proceed) {
                                logConsole.appendText("Decrypting... \n");
                                String ciphertext = (String) ciphertextField.getText().toLowerCase();
                                ShiftOne cipher = new ShiftOne("", ciphertext, key);
                                cipher.decryptManual();
                                printResults(Integer.toString(key), ciphertext, cipher.getPlaintext());
                                // If key not in range, set key field to empty
                            } else if (key > 25 && key < -25) {
                                keyFieldD.setText("");
                                logConsole.appendText("Key must be an integer between -25 through 25");
                            }
                        }
                    } catch (Exception e) {
                        keyFieldD.setText("");
                        logConsole.appendText("Please enter an integer for your key.");
                    }
                }
            }
        });

        // Decryption button to begin double decryption process 
        Button decryptShiftTwo = new Button();
        decryptShiftTwo.setText("Begin Double Decryption.");
        decryptShiftTwo.managedProperty().bind(decryptShiftTwo.visibleProperty());
        decryptShiftTwo.setVisible(false);
        decryptShiftTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (ciphertextField.getText() != null && key1FieldD.getText() != null && key2FieldD.getText() != null) {
                    // Check for valid text and key(s) input before decrypting 
                    try {
                        boolean proceed = true;
                        logConsole.setText(""); // Clear console
                        // If invalid text set textfield to empty
                        if (!ciphertextField.getText().matches("[a-zA-Z ]+")) {
                            proceed = false;
                            ciphertextField.setText(ciphertextField.getText().replaceAll("[^A-Za-z]+", "").toLowerCase());
                            logConsole.appendText("Please only enter letters a-z (case insensitive) \n");
                        }
                        /* Automatic Decryption */
                        if (automaticDecyptOption.isSelected() && proceed) {
                            String ciphertext = ((String) ciphertextField.getText()).replaceAll("[^A-Za-z]+", "").toLowerCase();
                            ShiftTwo cipher = new ShiftTwo("", ciphertext);
                            System.out.println("Ciphertext: " + ciphertext);
                            if (listAllOption.isSelected()) {
                                cipher.decryptAutomatic(-1);
                            } else {
                                cipher.decryptAutomatic(numOfPossibilites.getValue());
                            }
                        } else if (!automaticDecyptOption.isSelected()) {
                            /* Manual Decryption */
                            // Get key and plaintext. If key isn't an integer, raise error. 
                            int key1 = Integer.parseInt(key1FieldD.getText());
                            int key2 = Integer.parseInt(key2FieldD.getText());
                            String ciphertext = ((String) ciphertextField.getText()).replaceAll("[^A-Za-z]+", "").toLowerCase();

                            if (key1 < 25 && key1 > -25 && key2 < 25 && key2 > -25 && proceed) {
                                logConsole.appendText("Double Decrypting... \n");
                                ShiftTwo cipher = new ShiftTwo("", ciphertext, key1, key2);
                                cipher.decryptManual();
                                printResults(Integer.toString(key1), Integer.toString(key2), ciphertext, cipher.getPlaintext());
                                // If key not in range, set key field to empty
                            } else if (key1 > 25 && key1 < -25 && key2 < 25 && key2 > -25) {
                                keyField.setText("");
                                logConsole.appendText("Keys must be an integer between -25 through 25");
                            }
                        } else {
                            logConsole.appendText("Please enter valid ciphertext.");
                        }
                    } catch (Exception e) {
                        keyField.setText("");
                        logConsole.appendText("Please enter an integer for your keys.");
                    }
                }
            }
        });

        // Add Buttons and Selection to HBox
        HBox decryptBtns = new HBox(15);
        decryptBtns.getChildren().addAll(decryptShiftOne, decryptShiftTwo, checkboxVBox);

        // Fill Decrypt Shift tab with contents
        VBox decryptShiftVBox = new VBox(10);
        decryptShiftVBox.getChildren().addAll(plaintextHBoxD, keyHBoxD, key1HBoxD, key2HBoxD, decryptBtns);
        decryptShiftVBox.setPadding(new Insets(15, 15, 0, 15));
        decryptTab.setContent(decryptShiftVBox);
        tabPane.getTabs().add(decryptTab);

        // Detect cipher type selection and adjust options appropriately
        cipherSelection.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue)
                -> {
            // Toggle Key and Button Visabilities
            if (((String) newValue).equals("Double Shift Cipher")) {
                // Toggle Keys
                keyHBox.setVisible(false);
                keyHBoxD.setVisible(false);
                key1HBox.setVisible(true);
                key2HBox.setVisible(true);
                key1HBoxD.setVisible(true);
                key2HBoxD.setVisible(true);

                // Toggle Buttons
                encryptShiftOne.setVisible(false);
                encryptShiftTwo.setVisible(true);
                decryptShiftOne.setVisible(false);
                decryptShiftTwo.setVisible(true);

            } else if (((String) newValue).equals("Single Shift Cipher")) {
                // Toggle Keys
                keyHBox.setVisible(true);
                keyHBoxD.setVisible(true);
                key1HBox.setVisible(false);
                key2HBox.setVisible(false);
                key1HBoxD.setVisible(false);
                key2HBoxD.setVisible(false);
                // Toggle Buttons
                encryptShiftOne.setVisible(true);
                encryptShiftTwo.setVisible(false);
                decryptShiftOne.setVisible(true);
                decryptShiftTwo.setVisible(false);
            }
        }
        );

        BorderPane root = new BorderPane();
        root.setTop(cipherselectionHBox);
        root.setCenter(tabPane);
        root.setBottom(logConsole);
        
        // Make tabs fill width
        tabPane.tabMinWidthProperty().bind(root.widthProperty().divide(tabPane.getTabs().size()).subtract(25));
        Scene scene = new Scene(root, 400, 410);

        primaryStage.setTitle("Shift Cipher Utility");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /* Main method to initalize GUI loop */
    public static void main(String[] args) {
        launch(args);
    }
    
    /* Print results method for printing key, ciphertexts and plaintexts  */
    public static void printResults(String key, String ciphertext, String plaintext) {
        System.out.println("Key: " + key);
        System.out.println("Ciphertext: " + ciphertext.toUpperCase());
        System.out.println("Plaintext: " + plaintext.toUpperCase());
    }

    public static void printResults(String key1, String key2, String ciphertext, String plaintext) {
        System.out.println("Key #1: " + key1);
        System.out.println("Key #2: " + key2);
        System.out.println("Ciphertext: " + ciphertext.toUpperCase());
        System.out.println("Plaintext: " + plaintext.toUpperCase());
    }

    /* Redirects System.out.print() to GUI Console */
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

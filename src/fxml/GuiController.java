/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;
import typecompeter.DataHandler;
import typecompeter.Profile;
import typecompeter.Text;


/**
 *
 * @author moss_899291
 */
public class GuiController {
    @FXML private Pane main;
    @FXML private Label currentRacer;
    @FXML private TableView textTable;
    @FXML private TableColumn tcnames;
    @FXML private TableColumn tcchars;
    ArrayList<Profile> loaded;
    
    
    @FXML
    public void initialize() {
        loaded = new ArrayList();
	tcnames.setCellValueFactory(new PropertyValueFactory<Text,String>("name"));
	tcchars.setCellValueFactory(new PropertyValueFactory<Text,Integer>("size"));
        DataHandler.getSavedProfiles().stream().map(prf -> loaded.add(prf));
        loaded.toString();
        if (!loaded.isEmpty()) {
            currentRacer.setText("Current Racer: " + loaded.get(0).getName().trim());
        }
        
    }
    public void profileGoals() throws IOException {
	Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ProfileCreation.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchProfile() {
	
    }
    
    @FXML
    public void importText() {
	JFileChooser fc = new JFileChooser();
        int ret = fc.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            String s = DataHandler.parseText(f);
            String[] parts = (f.getName()).split("\\.");
            if (s != null) {
		textTable.getItems().add(new Text(parts[0],s));
            } else {
                //make error gui
            }
        }
    }
}

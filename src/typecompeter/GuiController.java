/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JFileChooser;


/**
 *
 * @author moss_899291
 */
public class GuiController {
    @FXML private Pane main;
    @FXML private Label currentRacer;
    @FXML private Label loadedTexts;
    @FXML private TableView textTable;
    @FXML private TableColumn tcnames;
    @FXML private TableColumn tcchars;
    public ArrayList<Text> texts;
    public ArrayList<Profile> loaded;
    public Profile currentProfile;
    int bots = 1;

    
    @FXML
    public void initialize() {
        loaded = new ArrayList();
	tcnames.setCellValueFactory(new PropertyValueFactory<Text,String>("name"));
	tcchars.setCellValueFactory(new PropertyValueFactory<Text,Integer>("size"));
        DataHandler.getSavedProfiles().stream().forEach(prf -> loaded.add(prf));
        
        if (!loaded.isEmpty()) {
            currentRacer.setText("Current Racer: " + loaded.get(0).getName().trim());
            currentProfile = loaded.get(0);
        }
        updateTextTable();
        
    }
    public void profileGoals() throws IOException {
	Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../fxml/ProfileCreation.fxml")));
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchProfile() {
	//remove cycling and add actual UI
        
        if (loaded.indexOf(currentProfile) + 1 < loaded.size()) {
            currentRacer.setText("Current Racer: " + loaded.get(loaded.indexOf(currentProfile) + 1).getName().trim());
            currentProfile = loaded.get(loaded.indexOf(currentProfile) + 1);
        } else {
            currentRacer.setText("Current Racer: " + loaded.get(0).getName().trim());
            currentProfile = loaded.get(0);
        } 
    }
    
    public void startGame() throws IOException {
        int n = (int) (Math.random() * texts.size());
        ArrayList<Player> plrs = new ArrayList();
        Player user = new Player(currentProfile);
        for (int i = 0; i < bots; i++) {
            plrs.add(new Player());
        }
        Race r = new Race(plrs, texts.get(n));
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
                Text tmp = new Text(parts[0],s);
                DataHandler.saveObject(tmp);
                updateTextTable();
            } else {
                //make error gui
            }
        }
    }

    @FXML
    private void updateTextTable() {
        texts = DataHandler.getSavedTexts();
        for (Text t : texts) {
            textTable.getItems().add(t);
        }
        loadedTexts.setText("Loaded Texts: " + texts.size());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JFileChooser;


/**
 *
 * @author moss_899291
 */
public class GuiController {
    @FXML private TableView textTable;
    @FXML private TableColumn tcnames;
    @FXML private TableColumn tcchars;
    @FXML
    public void initialize() {
	tcnames.setCellValueFactory(new PropertyValueFactory<>("name"));
	tcchars.setCellValueFactory(new PropertyValueFactory<>("size"));
    }
    public void profileGoals() {
	
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

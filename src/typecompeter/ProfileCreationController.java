/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dylan
 */
public class ProfileCreationController {
    @FXML private TextField name_input;
    @FXML private TextField key_input;
    @FXML private AnchorPane rootPane;
    private GuiController g;
    
    public void setGuiController(GuiController g) {
        this.g = g;
    }
    
    public void takeInput() {
        Profile p = new Profile(name_input.getText(),Integer.parseInt(key_input.getText())); //add safeguard incase inputn is not integer
        DataHandler.saveObject(p);
        Stage closeMe = (Stage) rootPane.getScene().getWindow();
        g.updateProfiles();
        closeMe.close();
    }
}

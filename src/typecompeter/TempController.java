/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author dylan
 */
public class TempController {
    @FXML TextField name_input;
    @FXML TextField key_input;
    
    public void takeInput() {
        Profile p = new Profile(name_input.getText(),Integer.parseInt(key_input.getText())); //add safeguard incase inputn is not integer
        DataHandler.saveObject(p);
        
    }
}

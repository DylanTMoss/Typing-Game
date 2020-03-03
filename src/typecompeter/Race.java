/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fxml.GameGui;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author dylan
 */
public class Race {
    ArrayList<Player> players;
    private Canvas race_canvas;
    private TextArea textBox;
    private TextField inputBox;
    
    public Race(ArrayList<Player> players, Text t) throws IOException {
        this.players = players;
        Stage stage = new Stage();
        FXMLLoader loada = new FXMLLoader();
        Scene scene = new Scene(loada.load(getClass().getResource("../fxml/GameGui.fxml").openStream()));
        stage.setScene(scene);
        stage.show();
        GameGui g = (GameGui) loada.getController();
        this.race_canvas = g.race_canvas;
        this.textBox = g.textBox;
        this.inputBox = g.inputBox;
        textBox.setText(String.valueOf(t.getText())); //make textBox uneditable
    }
    
    public void add(Player p) {
        players.add(p);
    }
}

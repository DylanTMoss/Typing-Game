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
import java.util.Map;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author dylan
 */
public class Race {
    ArrayList<Player> players;
    @FXML private Canvas theCanvas;
    boolean started;
    
    public Race(ArrayList<Player> players, Text t) throws IOException {
        started = false;
        this.players = players;
        for (Player p : players) {
            p.setText(t);
            p.setRace(this);
        }
    }
    
    public void start() throws IOException {
        for (Player p : players) {
            if (p.isPlayer()) {
                p.initializePlr();
            } else {
                p.initializeBot();
            }
        }
        started = true;
    }
    
    public boolean getStarted() {
        return started;
    }
    
    public void setCanvas(Canvas c) {
        theCanvas = c;
    }
}

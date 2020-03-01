/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 *
 * @author dylan
 */
public class Race {
    ArrayList<Player> players;
    
    public Race() {
        this.players = new ArrayList();
        //Parent root = FXMLLoader.load();
    }
    
    public Race(ArrayList<Player> players) {
        this.players = players;
        //Parent root = FXMLLoader.load();
    }
    
    public void add(Player p) {
        players.add(p);
        
    }
}

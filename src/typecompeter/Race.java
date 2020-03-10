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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author dylan
 */
public class Race {
    ArrayList<Player> players;
    @FXML private Canvas theCanvas;
    GraphicsContext g;
    boolean isDone;
    boolean started;
    Image img;
    
    public Race(ArrayList<Player> players, Text t) throws IOException {
        this.img = new Image((new File("assets/Truck.png").toURI().toString()));
        started = false;
        this.players = players;
        for (Player p : players) {
            p.setText(t);
            p.setRace(this);
        } 
   }
    
    public void start() throws IOException {
        isDone = false;
        for (Player p : players) {
            if (p.isPlayer()) {
                p.initializePlr();
            } else {
                p.initializeBot();
            }
        }
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (!isDone) {
                    try {
                        Thread.sleep(500);
                        update();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Race.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        new Thread(r).start();
        started = true;
    }
    
    public boolean getStarted() {
        return started;
    }
    
    public void setCanvas(Canvas c) {
        theCanvas = c;
        g = theCanvas.getGraphicsContext2D();
    }

    public void update() { //in the future make this update every second so updates arent sparratic
        g.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight()); //clear the canvas
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            double heightCalc = (theCanvas.getHeight()-50) - (theCanvas.getHeight()-50)/(players.size()-1) * i;
            g.drawImage(img, 40 + (theCanvas.getWidth()-120)*p.getPercentCompletion(), heightCalc);
            g.fillText(p.getName(), 0, heightCalc+25);
            g.fillText(""+(int)p.getWpm(), theCanvas.getWidth()-40, heightCalc+30);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.fxml.FXML;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

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
    
    public void start() throws InterruptedException {
        isDone = false;
        for (Player p : players) {
            if (p.isPlayer()) {
                try {
                    p.initializePlr();
                } catch (IOException ex) {
                    Logger.getLogger(Race.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        update();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    startSequence();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Race.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Player p : players) {
                    if (p.isPlayer()) {
                        p.startPlr();
                        p.getUser().played++;
                    } else {
                        p.startBot();
                    }
                   
                }
                started = true;
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
        
        
        
    }
    
    public boolean getStarted() {
        return started;
    }
    
    public void setCanvas(Canvas c) {
        theCanvas = c;
        g = theCanvas.getGraphicsContext2D();
    }
    
    public void startSequence() throws InterruptedException {
        g.setFont(new Font(30));
        g.fillText("3",theCanvas.getWidth()/2, theCanvas.getHeight()/2);
        Thread.sleep(1000);
        g.clearRect(theCanvas.getWidth()/2, theCanvas.getHeight()/2 - 30, 35,35); //since font size is 30
        g.fillText("2",theCanvas.getWidth()/2, theCanvas.getHeight()/2);
        Thread.sleep(1000);
        g.clearRect(theCanvas.getWidth()/2, theCanvas.getHeight()/2 - 30, 35,35);
        g.fillText("1",theCanvas.getWidth()/2, theCanvas.getHeight()/2);
        Thread.sleep(1000);
        g.clearRect(theCanvas.getWidth()/2, theCanvas.getHeight()/2 - 30, 35,35);
        g.setFont(new Font(12));
    }

    public void update() { //in the future make this update every second so updates arent sparratic
        g.clearRect(0, 0, theCanvas.getWidth(), theCanvas.getHeight()); //clear the canvas
        ArrayList<Double> wpms = new ArrayList();
        for (int i=0; i < players.size();i++) {
            wpms.add(players.get(i).getWpm());
        }
        //compareTo is intentionally reversed so Higher WPMs are first'
        wpms.sort((Object o1, Object o2) -> ((Double)o2).compareTo((Double)o1)); 
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            double heightCalc = (theCanvas.getHeight()-50) - (theCanvas.getHeight()-50)/(players.size()-1) * i;
            g.drawImage(img,40+ (theCanvas.getWidth()-160)*p.getPercentCompletion(), heightCalc);
            g.fillText(p.getName(), 0, heightCalc+25);
            g.fillText("WPM:"+(int)p.getWpm(), theCanvas.getWidth()-60, heightCalc+30);
            if (players.get(i).finished && (wpms.indexOf(p.getWpm())) < 3) {
                g.fillText(""+(wpms.indexOf(p.getWpm())+1), theCanvas.getWidth()-150, heightCalc+30);
                if (wpms.indexOf(p.getWpm()) == 0){
                    if (p.isPlayer() && !p.won) {
                        p.getUser().incrementWins();
                        p.won = true;
                    }
                }
            }
        }
        boolean done = true;
        for (Player p : players) {
            if (!p.finished) {
                done = false;
            }
        }
        isDone = done;
    }
}

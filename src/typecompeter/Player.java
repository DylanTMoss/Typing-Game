/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import fxml.GameGui;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author dylan
 */
class Player {
    public Profile user;
    Race curRace;
    Text words;
    String progress;
    double percentCompletion;
    int mistakes;
    int defaultCpm;
    long startTime;
    long completionTime;
    boolean finished = false;
    private TextArea textBox;
    private TextField inputBox;
    private String name;
    private final String[] botNames = {"Monkey", "Mouse", "Macaw", "Mallard", "Manatee", "Mule", "Moose", "Mole", "Mink", "Mollusk", "Magpie", "Moth"};
    
    public Player(int cpm) {
        user = null;
        words = null;
        name  = botNames[(int) (Math.random() * botNames.length)];
        percentCompletion = 0;
        mistakes = 0;
        defaultCpm = cpm - (int) (Math.random() * cpm/10);
        startTime = 0;
        completionTime = 0L;
        progress ="";
    }
    
    public Player(Profile p){
        user = p;
        name = p.name;
        words = null;
        percentCompletion = 0;
        mistakes = 0;
        startTime = 0;
        completionTime = 0L;
        progress="";
    }
    
    public void initializeBot() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!finished) {
                    if (startTime == 0) {
                        startTime = Instant.now().toEpochMilli();
                    }
                    if (progress.equals(new String(words.getText()))) {
                        finished = true;
                        completionTime = Instant.now().toEpochMilli();
                    } else {
                        progress += words.getText()[i++];
                    }
                    percentCompletion = ((double) progress.length() / (double) words.getSize());
                    try {
                        Thread.sleep(1000/ ((long) ((defaultCpm / 60.0))));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        new Thread(r).start();
    }
    
    public void initializePlr() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loada = new FXMLLoader();
        Scene scene = new Scene(loada.load(getClass().getResource("../fxml/GameGui.fxml").openStream()));
        stage.setScene(scene);
        stage.show();
        GameGui g = (GameGui) loada.getController();
        curRace.setCanvas(g.race_canvas);
        this.textBox = g.textBox;
        this.inputBox = g.inputBox;
        textBox.setText(String.valueOf(words.getText()));
        inputBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (startTime == 0) {
                    startTime = Instant.now().toEpochMilli();
                }
                progress = inputBox.getText();
                int diff = indexDiff();
                if (diff == -1) {
                    if (event.getCode() != KeyCode.BACK_SPACE) {
                        mistakes++;
                    }
                    percentCompletion = ((double) progress.length() / (double) words.getSize());
                    textBox.selectRange(0, 0);
                } else {
                    percentCompletion = (double) (diff) / words.getSize();
                    textBox.selectRange(diff, progress.length());
                }
                if (progress.equals(new String(words.getText()))) {
                    completionTime = Instant.now().toEpochMilli();
                    onFinish();
                }
            }
        });
    }
    
    private void onFinish() {
        if (isPlayer()) {
            finished = true;
            double accuracy = (double)mistakes/words.getSize();
            user.addScore(new Result((int)getWpm(), accuracy));
        }
    }
    
    private int indexDiff() {
        for (int i=0; i < progress.length(); i++) {
            if (progress.charAt(i) != words.getText()[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public double getWpm() {
        //divide characters per minute by 5 rather than counting words bc better representaiton of speed
        if (finished) {
            double typed = (double) progress.length();
            return ((typed * 60.0)/((completionTime - startTime)/1000.0)) / 5;
        }
        if (indexDiff() == -1) {
            double typed = (double) progress.length();
            return ((typed * 60.0)/((Instant.now().toEpochMilli() - startTime)/1000.0)) / 5;
        } else {
            double typed = indexDiff();
            return ((typed * 60.0)/((Instant.now().toEpochMilli() - startTime)/1000.0)) / 5;
        }

    }
    
    public boolean isPlayer() {
        return user != null;
    }
 
    public String getName() {
        return name;
    }

    public void setText(Text t) {
        words = t;
    }
    
    public void setRace(Race r) {
        curRace = r;
    }
    
    public void setDefaultCpm(int c) {
        defaultCpm = c;
    }
    
    public double getPercentCompletion() {
        return percentCompletion;
    }
}

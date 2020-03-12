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
import java.util.Calendar;
import java.util.TimeZone;
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
    double defaultCpm;
    long startTime;
    long completionTime;
    boolean finished = false;
    private TextArea textBox;
    private TextField inputBox;
    private String name;
    boolean won = false;
    private final String[] botNames = {"Monkey", "Mouse", "Macaw", "Mallard", "Manatee", "Mule", "Moose", "Mole", "Mink", "Mollusk", "Magpie", "Moth"};
    
    public Player(int cpm) {
        user = null;
        words = null;
        name  = botNames[(int) (Math.random() * botNames.length)];
        percentCompletion = 0;
        mistakes = 0;
        if (Math.random() > .5) {
            defaultCpm = cpm + (Math.random() * cpm/10.0);
        } else {
            defaultCpm = cpm - (Math.random() * cpm/10.0);
        }
        System.out.println(defaultCpm);
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

    public Profile getUser() {
        return user;
    }
    
    public void startBot() {
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
                        Thread.sleep((long) (1000.0/ ((defaultCpm / 60.0))));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        new Thread(r).start();
    }
    
    public void startPlr() {
        startTime = Instant.now().toEpochMilli();
        inputBox.setEditable(true);
        inputBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!finished) {
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
                    if (progress.equals(new String(words.getText()).replaceAll("\n", ""))) {
                        completionTime = Instant.now().toEpochMilli();
                        onFinish();
                    }
                }
            }
        });
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
        textBox.setWrapText(true);
        this.inputBox.setEditable(false);
    }
    
    private void onFinish() {
        
        if (isPlayer()) {
            finished = true;
            double accuracy = (double)mistakes/words.getSize();
            Calendar c = Calendar.getInstance(TimeZone.getDefault());
            int date = c.get(Calendar.DATE);
            user.addScore(new Result((int)getWpm(), accuracy, date));
        }
    }
    
    private int indexDiff() {
        if (progress.length() < words.getSize()) {
            for (int i=0; i < progress.length(); i++) {
                if (progress.charAt(i) != words.getText()[i]) {
                    return i;
                }
            }
            return -1;
        } else {
            for (int i=0; i < words.getSize(); i++) {
                if (progress.charAt(i) != words.getText()[i]) {
                    return i;
                }
            }
            return -1;
        }
    }
    
    public double getWpm() {
        //divide characters per minute by 5 rather than counting words bc better representaiton of speed
        if (finished) {
            double typed = (double) progress.length();
            return ((typed * 60.0)/((completionTime - startTime)/1000.0)) / 5.0;
        }
        if (indexDiff() == -1) {
            double typed = (double) progress.length();
            return ((typed * 60.0)/((Instant.now().toEpochMilli() - startTime)/1000.0)) / 5.0;
        } else {
            double typed = indexDiff();
            return ((typed * 60.0)/((Instant.now().toEpochMilli() - startTime)/1000.0)) / 5.0;
        }
    }
}

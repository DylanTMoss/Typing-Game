/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import fxml.GameGui;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
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
    Profile user;
    Race curRace;
    Text words;
    String progress;
    double percentCompletion;
    int mistakes;
    int defaultCpm;
    Long startTime;
    Long completionTime;
    private TextArea textBox;
    private TextField inputBox;
    
    public Player() {
        user = null;
        words = null;
        percentCompletion = 0;
        mistakes = 0;
        defaultCpm  = 500;
        startTime = 0L;
        completionTime = 0L;
    }
    
    public Player(Profile p){
        user = p;
        words = null;
        percentCompletion = 0;
        mistakes = 0;
        startTime = 0L;
        completionTime = 0L;
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
                progress = inputBox.getText();
                int diff = indexDiff();
                if (diff == -1) {
                    if (event.getCode() != KeyCode.BACK_SPACE) {
                        mistakes++;
                    }
                    textBox.selectRange(0, 0);
                } else {
                    textBox.selectRange(diff, progress.length());
                }
                if (progress.equals(words.getText())) {
                    //completionTime = 
                    onFinish();
                }
                percentCompletion = ((double) progress.length() / (double) words.getText().length);
            }
        });
    }
    
    private void onFinish() {
        //handle updating profile, showing race stats, etc here
    }
    
    private int indexDiff() {
        for (int i=0; i < progress.length(); i++) {
            if (progress.charAt(i) != words.getText()[i]) {
                return i;
            }
        }
        return -1;
    }
    
    public void initializeBot() {
        //update progress based on set cpm
    }
    
    public boolean isPlayer() {
        return user != null;
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

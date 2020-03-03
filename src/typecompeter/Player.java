/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import fxml.GameGui;
import java.io.IOException;
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
    Race curr;
    Text words;
    String progress;
    int percentCompletion;
    int mistakes;
    int defaultCpm;
    Long startTime;
    Long completionTime;
    private Canvas race_canvas;
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
        this.race_canvas = g.race_canvas;
        this.textBox = g.textBox;
        this.inputBox = g.inputBox;
        textBox.setText(String.valueOf(words.getText()));
        inputBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.BACK_SPACE) {
                    progress = progress.substring(progress.length() -1);
                    textBox.backward();
                } else { 
                    progress += event.getCode(); //fix this
                    textBox.forward();
                    //there is a highlight option somewhere, look in java docs
                }
                System.out.println(progress);
                System.out.println(progress.equals(String.valueOf(words.getText())));
            }
        });
    }
    
    public void initializeBot() {
        //update progress based on set cpm
    }
    
    public boolean isPlayer() {
        return user != null;
    }
    
    public void setRace(Race r) {
        
    }
    
    public void setText(Text t) {
        words = t;
    }
    
    public void setDefaultCpm(int c) {
        defaultCpm = c;
    }
    
    private void startTiming() {
        
    }
}

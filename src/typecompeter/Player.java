/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

/**
 *
 * @author dylan
 */
class Player {
    Profile user;
    Text words;
    int progress;
    int mistakes;
    int defaultCpm;
    Long startTime;
    Long completionTime;
    
    public Player() {
        user = null;
        words = null;
        progress = 0;
        mistakes = 0;
        defaultCpm  = 500;
        startTime = 0L;
        completionTime = 0L;
    }
    
    public Player(Profile p) {
        user = p;
        words = null;
        progress = 0;
        mistakes = 0;
        startTime = 0L;
        completionTime = 0L;
    }
    
    public void setText(Text t) {
        words = t;
    }
}

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
public class Result {
    int wpm;
    public int cnt = 0;
    double accuracy;

    public Result(int wpm, Double accuracy) {
        this.wpm = wpm;
        this.accuracy = accuracy;
    }
    
    public void addWpm(int wpm) {
        cnt++;
        this.wpm = ((this.wpm * cnt) + wpm) / cnt;
    }


    public int getWpm() {
        return wpm;
    }

    public void setWpm(int wpm) {
        this.wpm = wpm;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }
}

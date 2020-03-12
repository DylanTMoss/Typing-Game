/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.Serializable;

/**
 *
 * @author dylan
 */
public class Result implements Comparable, Serializable{
    int wpm;
    double accuracy;
    int day;

    public Result(int wpm, Double accuracy, int day) {
        this.wpm = wpm;
        this.accuracy = accuracy;
        this.day = day;
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
    
    @Override
    public int compareTo(Object o) {
        Result r = (Result) o;
        int rWpm = r.getWpm();
        if (wpm > r.getWpm()) {
            return 1;
        } else if (wpm == r.getWpm()) {
            return 0;
        } else {
            return -1;
        }
    }
    
    public String toString() {
        return "wpm: " + wpm;
    }

    public int getDay() {
        return day;
    }
}

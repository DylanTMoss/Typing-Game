/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeSet;

/**
 *
 * @author dylan
 */
public class Profile implements Serializable{
    String name;
    int key;
    TreeSet<Result> results;
    int played;
    int wins;

    public Profile (String name, int key) {
        results = new TreeSet();
        this.name = name;
        this.key = key;
    }

    public Result getTop() {
        return results.last();
    }
    
    public void incrementWins() {
        wins++;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }
    
    public int getCnt() {
        return results.size();
    }
    
    public int getAverage() {
        double sum = 0;
        for (Result r : results) {
            sum += r.getWpm();
        }
        return (int) (sum / getCnt());
    }

    public TreeSet<Result> getResults() {
        return results;
    }
    
    public int getWins() {
        return wins;
    }
    
    public String getName() {
	return name;
    }

    public int getKey() {
	return key;
    }

    public Map<Integer, Integer> getDailyAverages() {
	Map<Integer,Integer> dailyAvgs = new HashMap();
        Map<Integer,Integer> uniqueDays = new HashMap();
        Map<Integer,Integer> sums = new HashMap();
        for (Result r : results) {
            if (uniqueDays.containsKey(r.getDay())) {
                uniqueDays.replace(r.getDay(), uniqueDays.get(r.getDay())+1);
            } else {
                uniqueDays.put(r.getDay(), 1);
            }
        }
        for (Result r : results) {
            if (sums.containsKey(r.getDay())) {
                sums.replace(r.getDay(), sums.get(r.getDay())+1);
                
            } else {
               sums.put(r.getDay(), r.getWpm());
            }
        }
        for (int d : uniqueDays.keySet()) {
            dailyAvgs.put(d,(int)(sums.get(d) * 1.0/uniqueDays.get(d)));
        }
        return dailyAvgs;
    }
    
    public void addScore(Result r) {
        for (Result ra : results) {
            System.out.println(ra);
        }
        results.add(r);
    }
}

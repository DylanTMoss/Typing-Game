/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

/**
 *
 * @author dylan
 */
public class Profile implements Serializable{
    String name;
    int key;
    Map<Integer, Result> dailyscores;
    
    public Profile (String name, int key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
	return name;
    }

    public int getKey() {
	return key;
    }

    public Map<Integer, Result> getDailyscores() {
	return dailyscores;
    }
    
    public void addScore(Result r) {
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int date = c.get(Calendar.DATE);
        System.out.println(date);
    }
}

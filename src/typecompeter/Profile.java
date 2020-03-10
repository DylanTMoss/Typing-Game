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

/**
 *
 * @author dylan
 */
public class Profile implements Serializable{
    String name;
    int key;
    Map<Integer, Result> dailyscores;
    
    public Profile (String name, int key) {
        dailyscores = new HashMap();
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
        if (dailyscores != null) {
            if (dailyscores.containsKey(date)){
            r.cnt++;
            dailyscores.get(date).addWpm(r.getWpm());
            } else {
                r.cnt++;
                dailyscores.put(date, r);
            }
        }
        
    }
}

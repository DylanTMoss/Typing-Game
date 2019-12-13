/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import java.io.Serializable;
import java.util.TreeSet;

/**
 *
 * @author dylan
 */
public class Text implements Serializable{
    char[] text;
    TreeSet<Result> highScores;
    
    public Text(String text) {
        this.text = text.toCharArray();
    }
    
    public Text(String text, TreeSet<Result> highScores) {
        this.text = text.toCharArray();
        this.highScores = highScores;
    }
}

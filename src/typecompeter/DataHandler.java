/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package typecompeter;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;

/**
 *
 * @author dylanmoss
 */
public class DataHandler {
    
    //extensions for data whose names are generated automatically
    private final ImmutableMap<Object,String> extensions = ImmutableMap.of(
        Text.class, "tctx", 
        Profile.class, "tcpf"
    );
    
    public static ArrayList<Text> getSavedTexts() {
        return null;
        //iterate through "texts" folder and return all
    }
    
    public static ArrayList<Profile> getSavedProfiles() {
        return null;
        //iterate through "profiles" folder and return all
    }
    
    public static Text generateText(String s) {
        return null;
        //create a text object given a string
    }

    public static void saveObject(Object o) {
        if (o instanceof Profile) {
            Profile toSave = (Profile)o;
            //save using the extensions immutablemap, saving profiels based on key(######.tcpf)
        }
        //same thing for texts
    }
}

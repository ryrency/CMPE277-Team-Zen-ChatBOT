package edu.sjsu.zen.models;

/**
 * Created by sajujoseph on 4/30/18.
 */

public class Suggestions {
    String suggestion;
    String icon;
    Boolean displayed;

    public Suggestions(String suggestion, String icon){
        this.suggestion = suggestion;
        this.icon = icon;
        this.displayed = false;
    }

    public String getSuggestion(){
        return this.suggestion;
    }

    public String getIcon(){
        return this.icon;
    }

    public boolean getDisplayed(){
        return this.displayed;
    }
}

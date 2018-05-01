package edu.sjsu.zen.models;



public class Messages {

    private int id;
    private String message;
    private Boolean user; //user = 0 -> chatbot, user = 1 -> User

    public Messages(){

    }

    public Messages(int i, String string, String cursorString){

    }
    public Messages(int id, String date, String message, Boolean user){
        this.id = id;
        this.message = message;
        this.user = user;
    }

    public void setId(int id){
        this.id  =id;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setUser(Boolean user){
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getUser() {
        return user;
    }
}


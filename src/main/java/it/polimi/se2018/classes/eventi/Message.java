package it.polimi.se2018.classes.eventi;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    public Message (String message){
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
}

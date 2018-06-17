package it.polimi.se2018.classes.events;

import it.polimi.se2018.classes.visitor.ModelViewEventVisitor;

import java.io.Serializable;

public class Message implements Serializable,ModelViewEvent{
    private String message;
    private String player;
    public Message (String message){
        this.message=message;
    }
    public String getMessage(){
        return message;
    }

    @Override
    public void accept(ModelViewEventVisitor visitor) {
        visitor.visit(this);
    }

    public String getPlayer() {
        return player;
    }
}

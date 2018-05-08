package it.polimi.se2018.classes;

public class Player {

    private String playerName;
    private String color;
    private PrivateObjCard privateObj;
    private WindowSide chosenSide;
    private int favorToken;

    public Player( String playerName, String color, PrivateObjCard privateObj){
        this.playerName = playerName;
        this.color = color;
        this.privateObj = privateObj;

    }

    public void setSide( WindowCard chosenCard, boolean chosen){
        if (chosen) {
            this.chosenSide = chosenCard.getBack();
        }
        else {
            this.chosenSide = chosenCard.getFront();
        }
        this.setToken(chosenSide.getDifficult());
    }

    public void setToken(int favorToken){
        this.favorToken = favorToken;

    }

    public String getName(){
        return playerName;

    }

    public PrivateObjCard getPrivateObj(){
        return privateObj;

    }

    public WindowSide getSide(){
        return chosenSide;

    }

    public int getToken(){
        return favorToken;

    }

    public String getColor(){
        return color;

    }


}

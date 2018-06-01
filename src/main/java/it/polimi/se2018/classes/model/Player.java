package it.polimi.se2018.classes.model;

/**
 * @author Leonard Gasi
 */
public class Player {

    private String playerName;
    private String color;
    private PrivateObjCard privateObj;
    private WindowSide chosenSide;
    private int favorToken;

    /**
     * Constructor
     * @param playerName the name of the player
     * @param color the color to identify the player
     * @param privateObj the private objective card associated with the player
     */
    public Player( String playerName, String color, PrivateObjCard privateObj){
        this.playerName = playerName;
        this.color = color;
        this.privateObj = privateObj;

    }

    /**
     * @param window the window card given to the player
     */
    public void setSide(WindowSide window){
        this.chosenSide = window;

        this.setToken(chosenSide.getDifficult());
    }

    /**
     * @param favorToken the tokens the player get from the window
     */
    public void setToken(int favorToken){
        this.favorToken = favorToken;

    }

    /**
     * @return the name of the player
     */
    public String getName(){
        return playerName;

    }

    /**
     * @return the private objective card associated to the player
     */
    public PrivateObjCard getPrivateObj(){
        return privateObj;

    }

    /**
     * @return the window of the player
     */
    public WindowSide getSide(){
        return chosenSide;

    }

    /**
     * @return the number of the tokens of the player
     */
    public int getToken(){
        return favorToken;

    }

    /**
     * @return the color to identify the player
     */
    public String getColor(){
        return color;

    }


}

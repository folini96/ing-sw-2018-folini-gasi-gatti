package it.polimi.se2018.classes.model;

import it.polimi.se2018.classes.view.VirtualView;

/**
 * @author Leonard Gasi
 */
public class Player implements ModelViewEvent{

    private String playerName;
    private PrivateObjCard privateObj;
    private WindowSide window;
    private int favorToken;

    /**
     * Constructor
     * @param playerName the name of the player
     * @param privateObj the private objective card associated with the player
     */
    public Player( String playerName, PrivateObjCard privateObj){
        this.playerName = playerName;
        this.privateObj = privateObj;

    }



    /**
     * @param favorToken the tokens the player get from the window
     */
    public void setToken(int favorToken){
        this.favorToken = favorToken;

    }
    public void setWindow(WindowSide window){
        this.window=window;
        setToken(window.getDifficult());
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
    public WindowSide getWindow(){
        return window;

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

    public void accept(VirtualView visitor){
        visitor.visit(this);
    }

}

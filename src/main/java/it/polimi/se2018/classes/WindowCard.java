package it.polimi.se2018.classes;

/**
 * @author Leonard Gasi
 */
public class WindowCard {

    private int name;
    private WindowSide front;
    private WindowSide back;

    /**
     * costruttore della classe
     * @param name the name to identify the card
     * @param front the window set in the front of the card
     * @param back the window set in the back of the card
     */
    public WindowCard(int name, WindowSide front, WindowSide back){
        this.name = name;
        this.front = front;
        this.back = back;
    }

    /**
     * @return the name of the card
     */
    public int getName(){
        return name;

    }

    /**
     * @return the window in the front
     */
    public WindowSide getFront(){
        return front;

    }

    /**
     * @return the window in the back
     */
    public WindowSide getBack(){
        return back;

    }


}

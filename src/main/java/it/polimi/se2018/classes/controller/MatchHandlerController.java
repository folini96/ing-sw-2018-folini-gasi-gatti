package it.polimi.se2018.classes.controller;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.model.Box;
import it.polimi.se2018.classes.view.VirtualView;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;

import javax.swing.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class MatchHandlerController implements Observer,ViewControllerVisitor {
    private int playerNumber=0;
    private ArrayList<ChoseWindowEvent> chosenWindow=new ArrayList<>();
    private ArrayList<String> playerNames;
    private WindowSide[] windowSides;
    private MatchHandlerModel matchHandlerModel;
    private VirtualView view;
    private int round;
    private int firstPlayer;
    private int currentPlayer;
    private int turnPassed;
    public MatchHandlerController(VirtualView view){

        this.view =view;
    }

    public void handleStartMatch(ArrayList<String> usernames){
        playerNumber=usernames.size();
        round=0;
        Random random = new Random();
        int randomInt = random.nextInt(playerNumber-1);
        firstPlayer = randomInt;
        playerNames=usernames;
        matchHandlerModel=new MatchHandlerModel(view, parsePublicObjCard(), parsePrivateObjCard());
        matchHandlerModel.prepareMatch(playerNumber,playerNames);
        handleWindowCreation();
    }
    public PublicObjCard[] parsePublicObjCard(){
        int i;
        int randomInt;
        String[] publicObjCards = new String[10];
        PublicObjCard[] chosenCards = new PublicObjCard[3];
        ArrayList<Integer> cardNotAvailable = new ArrayList<>();
        JsonParser publicObjParser = new JsonParser();
        try{
            JsonObject cardArray = (JsonObject) publicObjParser.parse(new FileReader("src/main/resources/PublicObjCards.json"));
            JsonArray cards= cardArray.get("publicobjcards").getAsJsonArray();
            int currentCard=0;
            for (Object o: cards){
                JsonObject card = (JsonObject) o;
                publicObjCards[currentCard]=card.get("name").getAsString();
                currentCard++;
            }
        }catch (FileNotFoundException e){
            System.out.println("File JSON non trovato");
        }
        for (i=0; i<3; i++){
            do{
                Random random = new Random();
                randomInt = random.nextInt(10);
            }while (cardNotAvailable.contains(randomInt));
            switch(publicObjCards[randomInt]){
                case "coloridiversicolonna":
                    chosenCards[i]=new PublicObjCardColoriDiversiColonna();
                    break;
                case "coloridiversiriga":
                    chosenCards[i]=new PublicObjCardColoriDiversiRiga();
                    break;
                case "diagonalicolorate":
                    chosenCards[i]=new PublicObjCardDiagonaliColorate();
                    break;
                case "sfumaturechiare":
                    chosenCards[i]=new PublicObjCardSfumatureChiare();
                    break;
                case "sfumaturediverse":
                    chosenCards[i]=new PublicObjCardSfumatureDiverse();
                    break;
                case "sfumaturediversecolonna":
                    chosenCards[i]=new PublicObjCardSfumatureDiverseColonna();
                    break;
                case "sfumaturediverseriga":
                    chosenCards[i]=new PublicObjCardSfumatureDiverseRiga();
                    break;
                case "sfumaturemedie":
                    chosenCards[i]=new PublicObjCardSfumatureMedie();
                    break;
                case "sfumaturescure":
                    chosenCards[i]=new PublicObjCardSfumatureScure();
                    break;
                case "varietadicolore":
                    chosenCards[i]=new PublicObjCardVarietaDiColore();
                    break;

            }
            cardNotAvailable.add(randomInt);
        }
        return chosenCards;
    }
    public PrivateObjCard[] parsePrivateObjCard(){
        int i;
        int randomInt;
        String[] privateObjCards = new String[5];
        PrivateObjCard[] chosenCards = new PrivateObjCard[playerNumber];
        ArrayList<Integer> cardNotAvailable = new ArrayList<>();
        JsonParser privateObjParser = new JsonParser();
        try{
            JsonObject cardArray = (JsonObject) privateObjParser.parse(new FileReader("src/main/resources/PrivateObjCards.json"));
            JsonArray cards= cardArray.get("privateobjcards").getAsJsonArray();
            int currentCard=0;
            for (Object o: cards){
                JsonObject card = (JsonObject) o;
                privateObjCards[currentCard]=card.get("color").getAsString();
                currentCard++;
            }
        }catch (FileNotFoundException e){
            System.out.println("File JSON non trovato");
        }
        for (i=0; i<playerNumber; i++){
            do{
                Random random = new Random();
                randomInt = random.nextInt(5);


            }while (cardNotAvailable.contains(randomInt));
            switch(privateObjCards[randomInt]){
                case "rosso":
                    chosenCards[i]=new PrivateObjCard(Color.ROSSO);
                    break;
                case "giallo":
                    chosenCards[i]=new PrivateObjCard(Color.GIALLO);
                    break;
                case "verde":
                    chosenCards[i]=new PrivateObjCard(Color.VERDE);
                    break;
                case "blu":
                    chosenCards[i]=new PrivateObjCard(Color.BLU);
                    break;
                case "viola":
                    chosenCards[i]=new PrivateObjCard(Color.VIOLA);
                    break;
            }
            cardNotAvailable.add(randomInt);
        }
        return chosenCards;
    }
    public WindowSide[] parseWindowSide(){
        int i;
        int j;
        int randomInt;
        WindowSide[] windows = new WindowSide[24];
        WindowSide[] chosenWindows = new WindowSide[playerNumber*4];
        ArrayList<Integer> windowNotAvailable=new ArrayList<>();
        JsonParser windowParser=new JsonParser();
        try{
            JsonObject windowArray = (JsonObject) windowParser.parse(new FileReader("src/main/resources/Windows.json"));
            JsonArray sides = windowArray.get("windows").getAsJsonArray();
            int currentWindow=0;
            for (Object o: sides){
                JsonObject side = (JsonObject) o;
                String name = side.get("name").getAsString();
                int difficult = side.get("difficult").getAsInt();
                JsonArray scheme = side.get("boxScheme").getAsJsonArray();
                Box[][] boxScheme = new Box[4][5];
                for (Object b: scheme){
                    JsonObject box = (JsonObject) b;
                    String color = box.get("color").getAsString();
                    Color boxColor=null;
                    int value = box.get("value").getAsInt();
                    switch (color){
                        case "rosso":
                            boxColor=Color.ROSSO;
                            break;
                        case "giallo":
                            boxColor=Color.GIALLO;
                            break;
                        case "blu":
                            boxColor=Color.BLU;
                            break;
                        case "verde":
                            boxColor=Color.VERDE;
                            break;
                        case "viola":
                            boxColor=Color.VIOLA;
                            break;
                        case "null":
                            boxColor=null;
                            break;
                    }
                    boxScheme[box.get("row").getAsInt()][box.get("column").getAsInt()]= new Box(boxColor,value);
                }
                windows[currentWindow]=new WindowSide(name,difficult,boxScheme);
                currentWindow++;
            }
        }catch(FileNotFoundException e){
            System.out.println("File JSON non trovato");
        }
        for (i=0; i<playerNumber;i++){
            for(j=0;j<2;j++){
                do{
                    Random random = new Random();
                    randomInt = random.nextInt(12);
                }while (windowNotAvailable.contains(randomInt));
                windowNotAvailable.add(randomInt);
                chosenWindows[(i*4)+(j*2)]=windows[randomInt*2];
                chosenWindows[((i*4)+(j*2))+1]=windows[(randomInt*2)+1];
            }
        }
        windowSides=chosenWindows;
        return chosenWindows;
    }
    public void handleWindowCreation(){
        view.windowToChose(parseWindowSide());
    }

    public void  handleStartRound(){
        currentPlayer=firstPlayer;
        turnPassed=0;
        handleStartTurn();
        matchHandlerModel.startRound(round, firstPlayer);

    }
    public void handleStartTurn(){
        matchHandlerModel.startTurn(currentPlayer);
    }
    public void handleEndTurn(){
        turnPassed++;
        if (turnPassed==(playerNumber*2)) {
            handleEndMatch();
        }else {
            if (turnPassed<playerNumber){
                if (currentPlayer==(playerNumber-1)){
                    currentPlayer=0;
                }else{
                    currentPlayer++;
                }
            }else if (turnPassed>playerNumber){
                if (currentPlayer==0){
                    currentPlayer=(playerNumber-1);
                }else{
                    currentPlayer--;
                }
            }
            handleStartTurn();
        }

    }
    public void handleEndRound(){
        matchHandlerModel.endRound(round);
        if (round==9){
            handleEndMatch();
        }else{
            if (firstPlayer==(playerNumber-1)){
                firstPlayer=0;
            }else{
                firstPlayer++;
            }
            round++;
            handleStartRound();
        }
    }

    public void handleEndMatch(){

    }

    public void handlePlaceDice(PlaceDiceEvent placeDiceEvent){
        if(matchHandlerModel.checkCorrectMove(placeDiceEvent, currentPlayer)){
            //place dice;
        }
        else{
           //messaggio errore
        }

    }
    public void handleWindowSelection(ChoseWindowEvent window){
        chosenWindow.add(window);
        if (chosenWindow.size()==playerNumber){
            matchHandlerModel.windowSelection(chosenWindow,windowSides);
            matchHandlerModel.startMatch();
            handleStartRound();
        }
    }
    public void handleToolCardSelection(){

    }
    public void update(Observable view, Object arg) {
        ViewControllerEvent event=(ViewControllerEvent) arg;
        event.accept(this);
    }
    public void visit(ChoseWindowEvent window){
        handleWindowSelection(window);
    }
    public void visit(PlaceDiceEvent placeDiceEvent){

    }
    public void visit(UseToolCardEvent toolCard){

    }
    public void visit(EndTurnEvent endTurnEvent){
        handleEndRound();
    }


}

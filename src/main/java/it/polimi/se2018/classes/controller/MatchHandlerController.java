package it.polimi.se2018.classes.controller;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.se2018.classes.model.effects.*;
import it.polimi.se2018.classes.events.*;
import it.polimi.se2018.classes.events.PlaceDiceEvent;
import it.polimi.se2018.classes.model.*;
import it.polimi.se2018.classes.model.Box;
import it.polimi.se2018.classes.view.VirtualView;
import it.polimi.se2018.classes.visitor.ViewControllerVisitor;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

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
    private int activeToolCard;
    private int modifiedDraftDiceInExchange;
    private boolean noVicinityBound;
    private boolean alreadyPlaced;
    private ArrayList<Integer> noSecondTurn;
    private ArrayList<String> playerReconnecting;
    private ArrayList<String> reconnected;
    private static final String NOT_VALIDE_MOVE_MESSAGE = "Mossa non valida. Fare un'altra mossa o finire il turno";
    private static final String NOT_ENOUGH_TOKEN = "Non hai abbastanza token per usare questa carta";
    private static final String NOT_VALIDE_AFTER_PLACEMENT = "Non puoi utilizzare questa carta dopo aver già piazzato un dado";
    private static final String NOT_VALIDE_BEFORE_PLACEMENT = "Non puoi utilizzare questa carta prima di aver piazzato un dado";
    private static final String NOT_VALIDE_WITH_EMPTY_WINDOW = "Non puoi utilizzare questa carta se la tua vetrata è ancora vuota";
    private static final String NOT_VALIDE_WITH_EMPTY_ROUND_TRACK = "Non puoi utilizzare questa carta se il tracciato del round è vuoto";
    private static final String NOT_VALIDE_WITH_EMPTY_DICE_BAG = "Non puoi utilizzare questa carta se il sacchetto dei dadi è vuoto";
    private static final String NOT_VALIDE_MODIFY ="Modifica non valida";
    private static final String NOT_VALIDE_MOVE ="Spostamento non valido";
    private static final String NOT_VALIDE_IN_FIRST_TURN ="Non puoi utilizzare questa carta durante il primo turno";
    private static final String NOT_VALIDE_IN_SECOND_TURN ="Non puoi utilizzare questa carta durante il secondo turno";


    public void setView(VirtualView view) {
        this.view = view;
    }

    public void handleStartMatch(ArrayList<String> usernames){
        noSecondTurn=new ArrayList<>();
        playerReconnecting=new ArrayList<>();
        reconnected=new ArrayList<>();
        playerNumber=usernames.size();
        round=0;
        Random random = new Random();
        int randomInt = random.nextInt(playerNumber);
        firstPlayer = randomInt;
        playerNames=usernames;
        matchHandlerModel=new MatchHandlerModel(view, parsePublicObjCard(), parsePrivateObjCard(), parseToolCard());
        matchHandlerModel.prepareMatch(playerNumber,playerNames);
        handleWindowCreation();
    }
    private PublicObjCard[] parsePublicObjCard(){
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
    private PrivateObjCard[] parsePrivateObjCard(){
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
    private WindowSide[] parseWindowSide(){
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

    private ToolCard[] parseToolCard(){

        int i, randomInt;
        ToolCard[] toolCards = new ToolCard[12];
        ToolCard[] chosenToolCards = new ToolCard[3];
        ArrayList<Integer> NotAvailable = new ArrayList<>();
        JsonParser toolCardParser = new JsonParser();
        try{
            JsonObject toolCardArray = (JsonObject) toolCardParser.parse(new FileReader("src/main/resources/ToolCards.json"));
            JsonArray cards= toolCardArray.get("toolcards").getAsJsonArray();
            int currentCard=0;
            for (Object o: cards){
                JsonObject card = (JsonObject) o;
                String name=card.get("name").getAsString();
                int number=card.get("number").getAsInt();
                int token=card.get("token").getAsInt();
                String colorCard=card.get("color").getAsString();
                Color color=null;
                switch (colorCard){
                    case "rosso":
                        color=Color.ROSSO;
                        break;
                    case "giallo":
                        color=Color.GIALLO;
                        break;
                    case "blu":
                        color=Color.BLU;
                        break;
                    case "verde":
                        color=Color.VERDE;
                        break;
                    case "viola":
                        color=Color.VIOLA;
                        break;
                }
                boolean blockedAfterPlacement=card.get("blockedAfterPlacement").getAsBoolean();
                String typeOfEffectCard=card.get("typeofeffect").getAsString();
                EffectType typeOfEffect=null;
                switch (typeOfEffectCard){
                    case "upordownvalue":
                        typeOfEffect=EffectType.UPORDOWNVALUEMODIFY;
                        break;
                    case "draftpoolroundtrackexchange":
                        typeOfEffect=EffectType.DRAFTPOOLROUNDTRACKEXCHANGE;
                        break;
                    case "newrandomvalue":
                        typeOfEffect=EffectType.NEWRANDOMVALUEMODIFY;
                        break;
                    case "rotatedice":
                        typeOfEffect=EffectType.ROTATEDICEMODIFY;
                        break;
                    case "draftpoolbagexchange":
                        typeOfEffect=EffectType.DRAFTPOOLBAGEXCHANGE;
                        break;
                    case "nocolorbound":
                        typeOfEffect=EffectType.NOCOLORBOUND;
                        break;
                    case "novaluebound":
                        typeOfEffect=EffectType.NOVALUEBOUND;
                        break;
                    case "movetwodice":
                        typeOfEffect=EffectType.MOVETWODICE;
                        break;
                    case "movetwodiceselectedcolor":
                        typeOfEffect=EffectType.MOVETWODICESELECTEDCOLOR;
                }
                String effectCard=card.get("effect").getAsString();
                ToolCardsEffectsInterface effect=null;
                switch (effectCard){
                    case "modify":
                        effect=new Modify(typeOfEffect);
                        break;
                    case "move":
                        effect=new Move(typeOfEffect);
                        break;
                    case "exchange":
                        effect=new Exchange(typeOfEffect);
                        break;
                    case "rerolldraftpooldices":
                        effect=new RerollDraftPool(typeOfEffect);
                        break;
                    case "secondplacement":
                        effect=new SecondPlacement(typeOfEffect);
                        break;
                    case "placementwithoutvicinity":
                        effect=new PlacementWithoutVicinity(typeOfEffect);
                        break;
                }

                toolCards[currentCard]=new ToolCard(name, number, token, color, effect, blockedAfterPlacement);
                currentCard++;
            }
        }catch (FileNotFoundException e){
            System.out.println("File JSON non trovato");
        }
        for (i=0; i<3; i++){
            do{
                Random random = new Random();
                randomInt = random.nextInt(12);
            }while (NotAvailable.contains(randomInt));
            chosenToolCards[i]=toolCards[randomInt];
            NotAvailable.add(randomInt);
        }

        return chosenToolCards;
    }

    private void handleWindowCreation(){
        view.windowToChose(parseWindowSide());
    }

    private void  handleStartRound(){
        currentPlayer=firstPlayer;
        turnPassed=0;
        handleStartTurn();
        matchHandlerModel.startRound(round, firstPlayer);

    }
    private void handleStartTurn(){
        noVicinityBound=false;
        matchHandlerModel.startTurn(currentPlayer);
    }
    private void handleEndTurn(){
        boolean endRound;
        view.cancelTimer();
        for (String player:playerReconnecting){
            reconnected.add(player);
            matchHandlerModel.sendReconnectionUpdate(player);
        }
        for (String player:reconnected){
            playerReconnecting.remove(player);
        }
        reconnected.clear();
        turnPassed++;
        alreadyPlaced=false;
        if (turnPassed==(playerNumber*2)) {
            endRound=true;
        }else {
            endRound=false;
            if (turnPassed<playerNumber){
                if (currentPlayer==(playerNumber-1)){
                    currentPlayer=0;
                }else{
                    currentPlayer++;
                }
            }else {
                if (turnPassed!=playerNumber){
                    if (currentPlayer==0){
                        currentPlayer=(playerNumber-1);
                    }else{
                        currentPlayer--;
                    }
                }
                while ((noSecondTurn.contains(currentPlayer))&&(!endRound)){
                    System.out.println(noSecondTurn+"   "+currentPlayer);
                    turnPassed++;
                    if (currentPlayer==0){
                        currentPlayer=(playerNumber-1);
                    }else{
                        currentPlayer--;
                    }
                    System.out.println(turnPassed);
                    if (turnPassed==(playerNumber*2)){
                    endRound=true;
                    }
                }

            }

        }
        if (endRound){
            handleEndRound();
        }else{
            handleStartTurn();
        }

    }
    private void handleEndRound(){
        noSecondTurn.clear();
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

    private void handleEndMatch(){
        matchHandlerModel.endMatch();
    }

    private void handlePlaceDice(PlaceDiceEvent placeDiceEvent){
        if(matchHandlerModel.checkCorrectPlacement(placeDiceEvent, currentPlayer, noVicinityBound)){
            matchHandlerModel.placeDice(placeDiceEvent,currentPlayer);
            alreadyPlaced=true;
        }
        else{
            Message message = new Message(NOT_VALIDE_MOVE_MESSAGE, playerNames.get(currentPlayer));
            message.accept(view);
        }

    }
    private void handleWindowSelection(ChoseWindowEvent window){
        chosenWindow.add(window);
        if (chosenWindow.size()==playerNumber){
            matchHandlerModel.windowSelection(chosenWindow,windowSides);
            matchHandlerModel.startMatch();
            handleStartRound();
        }
    }

    private void handleToolCardSelection(int toolCard){
        if ((matchHandlerModel.checkUseSecondTurn(toolCard))&&((turnPassed<playerNumber))){
            Message message = new Message(NOT_VALIDE_IN_FIRST_TURN, playerNames.get(currentPlayer));
            message.accept(view);
        }
        else if ((round==9)&&(playerNumber==4)&&(matchHandlerModel.checkNotUseWithEmptyDiceBag(toolCard))){
            Message message = new Message(NOT_VALIDE_WITH_EMPTY_DICE_BAG, playerNames.get(currentPlayer));
            message.accept(view);
        }
        else if ((matchHandlerModel.checkUseFirstTurn(toolCard))&&(turnPassed>=playerNumber)) {
            Message message = new Message(NOT_VALIDE_IN_SECOND_TURN, playerNames.get(currentPlayer));
            message.accept(view);
        }
        else if ((matchHandlerModel.checkUseFirstTurn(toolCard))&&(!alreadyPlaced)){
            Message message = new Message(NOT_VALIDE_BEFORE_PLACEMENT, playerNames.get(currentPlayer));
            message.accept(view);

        }
        else if ((alreadyPlaced)&&(matchHandlerModel.checkBeforePlacing(toolCard))){
            Message message = new Message(NOT_VALIDE_AFTER_PLACEMENT, playerNames.get(currentPlayer));
            message.accept(view);
        }
        else if (matchHandlerModel.checkNotUseWithEmptyWindow(toolCard,currentPlayer)){
            Message message = new Message(NOT_VALIDE_WITH_EMPTY_WINDOW, playerNames.get(currentPlayer));
            message.accept(view);
        }
        else if (matchHandlerModel.checkNotUseWithEmptyRoundTrack(toolCard)){
            Message message = new Message(NOT_VALIDE_WITH_EMPTY_ROUND_TRACK, playerNames.get(currentPlayer));
            message.accept(view);
        }else{
            if (matchHandlerModel.checkEnoughToken(toolCard,currentPlayer)){
                activeToolCard=toolCard;
                matchHandlerModel.sendEffect(toolCard,currentPlayer);
                if (matchHandlerModel.checkNoVicinityBound(toolCard)){
                    noVicinityBound=true;
                }
                if (matchHandlerModel.checkUseFirstTurn(toolCard)){
                    noVicinityBound=false;
                    noSecondTurn.add(currentPlayer);
                }
            }else {
                Message message = new Message(NOT_ENOUGH_TOKEN, playerNames.get(currentPlayer));
                message.accept(view);
            }
        }
    }
    public void update(Observable view, Object arg) {
        ViewControllerEvent event=(ViewControllerEvent) arg;
        event.accept(this);
    }
    private void handleMoveDice(MoveDiceEvent moveDiceEvent){
        if(!matchHandlerModel.checkCorrectMove(moveDiceEvent,currentPlayer,activeToolCard)){
            Message message = new Message(NOT_VALIDE_MOVE, playerNames.get(currentPlayer));
            message.accept(view);
        }else{
            matchHandlerModel.moveDice(moveDiceEvent,currentPlayer);
        }
    }
    private void handleModifiyDice(ModifyDiceEvent modifyDiceEvent){
        if (!matchHandlerModel.modifyDice(modifyDiceEvent,activeToolCard)){
            Message message = new Message(NOT_VALIDE_MODIFY, playerNames.get(currentPlayer));
            message.accept(view);
        }
    }
    private void handleRerollDraft(RerollDraftEvent rerollDraftEvent){
        matchHandlerModel.rerollDraftPool();
    }
    private void handleExchangeDice(ExchangeEvent exchangeEvent){
        modifiedDraftDiceInExchange=exchangeEvent.getDraftDice();
        matchHandlerModel.exchange(activeToolCard,exchangeEvent,currentPlayer);
    }
    private void handleSetValue(SetValueEvent setValueEvent){
        matchHandlerModel.setValueDiceFromBag(setValueEvent,modifiedDraftDiceInExchange);
    }
    private void handleReconnection(ReconnectClientEvent reconnectClientEvent){
        playerReconnecting.add(reconnectClientEvent.getPlayer());
    }
    public void visit(ChoseWindowEvent window){
        handleWindowSelection(window);
    }
    public void visit(PlaceDiceEvent placeDiceEvent){
        handlePlaceDice(placeDiceEvent);
    }
    public void visit(UseToolCardEvent toolCard){
        handleToolCardSelection(toolCard.getToolCard());
    }
    public void visit(EndTurnEvent endTurnEvent){
        handleEndTurn();
    }

    public void visit(MoveDiceEvent moveDiceEvent) {
        handleMoveDice(moveDiceEvent);
    }
    public void visit(ModifyDiceEvent modifyDiceEvent){
        handleModifiyDice(modifyDiceEvent);
    }
    public void visit(RerollDraftEvent rerollDraftEvent){
        handleRerollDraft(rerollDraftEvent);
    }
    public void visit(ExchangeEvent exchangeEvent){
        handleExchangeDice(exchangeEvent);
    }
    public void visit(SetValueEvent setValueEvent){
        handleSetValue(setValueEvent);
    }
    public void visit(ReconnectClientEvent reconnectClientEvent){
        handleReconnection(reconnectClientEvent);
    }
    public void visit(ConnectionErrorEvent connectionErrorEvent){
        int player;
        player=playerNames.indexOf(connectionErrorEvent.getDisconnectedClient());
        if (player==currentPlayer){
            handleEndTurn();
        }
    }
}

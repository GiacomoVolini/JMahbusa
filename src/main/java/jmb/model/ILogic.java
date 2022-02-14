package jmb.model;

public interface ILogic {

    //void placePawnOnTopPoints (int prevRegion, int prevPoint, int whichPoint);

    //void placePawnOnBotPoints (int whichPoint);

    void placePawnOnPoint (int whichPoint);

    boolean getWhichTurn();

    void nextTurn ();

    void initializeBoardLogic ();

    int getBoardPlaceState (int whichPoint, int whichRow);
    //  Metodo che restituisce tre valori a seconda dello stato della casella corrispondente di BoardLogic
    //      Restituisce EMPTY (0) se la casella non contiene alcuna pedina
    //                  WHITE (1) se la casella contiene una pedina del bianco
    //                  BLACK (2) se la casella contiene una pedina del nero

    boolean isLastOnPoint (int whichPoint, int whichRow);
    //  Metodo che restituisce true se non ci sono altre pedine al di sopra di essa in quella punta

    void createMoveBuffer (int whichPoint);




}

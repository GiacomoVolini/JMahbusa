package jmb.model;

public interface ILogic {

    //void placePawnOnTopPoints (int prevRegion, int prevPoint, int whichPoint);

    //void placePawnOnBotPoints (int whichPoint);

    boolean placePawnOnPoint (int prevPoint, int prevRow, int whichPoint);

    boolean getWhichTurn();

    void placePawnOnWHTExit();

    void placePawnOnBLKExit();

    void nextTurn ();

    void initializeBoardLogic ();

    boolean checkIfMovable (int whichPoint, int whichRow);




}

package jmb.model;


public class MoveRecord {

    private int pointInit;                          //  La punta da cui è partita la mossa
    private int pointFin;                           //  La punta verso cui è effettuata la mossa
    private boolean[] diceUsed = new boolean[4];    //  Quali dadi sono stati usati per la mossa
    private boolean opensWhiteExit;                 //  Se la mossa ha aperto la zona di uscita del bianco
    private boolean opensBlackExit;                 //  Se la mossa ha aperto la zona di uscita del nero

    public MoveRecord (int from, int to, boolean[] diceUsed) {
        this.pointInit = from;
        this.pointFin = to;
        for (int i = 0; i<4; i++)
            this.diceUsed[i] = diceUsed[i];
        this.opensBlackExit = false;
        this.opensWhiteExit = false;
    }

    public int getPointInit() {
        return pointInit;
    }

    public int getPointFin() {
        return pointFin;
    }

    public boolean[] getDiceUsed() {
        return diceUsed;
    }

    public boolean getOpensWhiteExit() {
        return opensWhiteExit;
    }

    public boolean getOpensBlackExit() {
        return opensBlackExit;
    }

    public void moveOpensBlackExit() {
        opensBlackExit = true;
    }

    public void moveOpensWhiteExit() {
        opensWhiteExit = true;
    }

    @Override
    public String toString() {
        String out = "Mossa da " + pointInit + " a " + pointFin;
        return out;
    }
}

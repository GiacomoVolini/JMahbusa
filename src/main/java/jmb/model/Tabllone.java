package jmb.model;

public class Tabllone {

    Pedina [][] caselle= new Pedina[16][25];
    private boolean WhiteExit;
    private boolean BlackExit;
    private boolean WhiteTurn;


    public boolean mossaPossibile(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){

        boolean possibile = true;

        if(WhiteTurn) {
            if (caselle[puntaInizR][puntaInizC].getisBlockedW() || caselle[puntaFinR][puntaFinC].getisBlockedW()) {
              possibile = false;
            }
        } else if (caselle[puntaFinR][puntaFinC].getisBlockedB() || caselle[puntaInizR][puntaInizC].getisBlockedB()){
              possibile = false;
        }
        return possibile;
    }

    public void spostaPedina(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){
        if(mossaPossibile(puntaInizC, puntaInizR, puntaFinR, puntaFinC)){
            caselle [puntaFinR][puntaFinC]= caselle[puntaInizR][puntaInizC];
            caselle[puntaInizR][puntaInizC]= null;
        }
    }
}

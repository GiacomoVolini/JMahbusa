package jmb.model;
import static jmb.ConstantsClass.*;

public class Tabllone {

    Pedina[][] caselle= new Pedina[16][25];
    private boolean whiteExit;
    private boolean blackExit;
    private boolean whiteTurn;
    private Dice dice;



    public Tabllone(){

    this.blackExit = false;
    this.whiteExit = false;
    dice = new Dice();


    for (int i=0; i<=14;i++){
        caselle [i][COL_WHITE]= new Pedina(false, true, true);
        caselle [i][COL_BLACK]= new Pedina(true, false, false);
       }

    whiteTurn = dice.InitialToss();

    }


    public boolean mossaPossibile(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){

        boolean possibile = true;

        //SI DA PER SCONTATO CHE IL COLORE DELLA PEDINA SIA QUELLO GIUSTO

        if (versoGiusto(puntaInizC, puntaInizR, puntaFinC)) {    //controlla che la mossa sia nel verso giusto

            if (COL_BLACK_EXIT < puntaFinC && puntaFinC < COL_WHITE_EXIT) { //controlla che la mossa non sia per l'uscita della pedina
                if (whiteTurn) {
                    if (caselle[puntaFinR][puntaFinC].getisBlockedW()) {    //controlla che la posizione di arrivo non sia preclusa al bianco
                        possibile = false;
                    }
                } else if (caselle[puntaFinR][puntaFinC].getisBlockedB()) { //controlla che la posizione di arrivo non sia preclusa al nero
                    possibile = false;
                }
            } else if (((!this.blackExit) && !whiteTurn) || ((!this.whiteExit) && whiteTurn)) {
                possibile = false;
            }
        } else possibile = false;

        return possibile;
    }

    public void spostaPedina(int puntaInizC, int puntaInizR, int puntaFinR, int puntaFinC){
        if(mossaPossibile(puntaInizC, puntaInizR, puntaFinR, puntaFinC)){
            caselle [puntaFinR][puntaFinC]= caselle[puntaInizR][puntaInizC];
            caselle[puntaInizR][puntaInizC]= null;
            if (puntaFinR==0) {
                caselle[puntaFinR][puntaFinC].setisBlockedB(false);
                caselle[puntaFinR][puntaFinC].setisBlockedW(false);
            } else if (caselle[puntaFinR][puntaFinC].getisWhite()) {
                caselle[puntaFinR][puntaFinC].setisBlockedB(true);
                caselle[puntaFinR][puntaFinC].setisBlockedW(false);
            } else {
                caselle[puntaFinR][puntaFinC].setisBlockedB(false);
                caselle[puntaFinR][puntaFinC].setisBlockedW(true);
            }

            if (whiteTurn) {                            //
               this.isWhiteExit();                      //  Controlla se il giocatore di turno può far uscire le proprie
            } else {                                    //  pedine e aggiorna di conseguenza la variabile relativa
                this.isBlackExit();                     //
            }                                           //

        }
    }

    public boolean versoGiusto(int puntaInizC, int puntaInizR, int puntaFinC) {

        boolean giusto = (caselle[puntaInizR][puntaInizC].getisWhite() && (puntaFinC > puntaInizC)) ||
                (!caselle[puntaInizR][puntaInizC].getisWhite() && (puntaFinC < puntaInizC));
        return giusto;

    }
    public void isBlackExit() {

        // i=colnne  j=righe


            if (!this.blackExit){
                boolean trovato = false;

                for (int i=1; i<=18 && !trovato; i++){
                    for (int j=0; j<=1 && !trovato; j++){
                        if (caselle [j][i] != null && !caselle [j][i].getisWhite()){
                            trovato = true;
                        }
                    }
                }
                if (!trovato){
                    blackExit = true;
                }
            }
    }
    public void isWhiteExit() {

        // i=colonne  j=righe


        if (!this.whiteExit){
            boolean trovato = false;

            for (int i=7; i<=24 && !trovato; i++){
                for (int j=0; j<=1 && !trovato; j++){
                    if (caselle [j][i] != null && caselle [j][i].getisWhite()){
                        trovato = true;
                    }
                }
            }
            if (!trovato){
                whiteExit = true;
            }
        }
    }
}

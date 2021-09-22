package jmb.model;
import static jmb.ConstantsClass.*;

public class Tabllone {

    Pedina [][] caselle= new Pedina[16][25];
    private boolean WhiteExit;
    private boolean BlackExit;
    private boolean WhiteTurn;



    public Tabllone (){

    this.BlackExit = false;
    this.WhiteExit = false;


   for (int i=0; i<=14;i++){
        caselle [i][COL_WHITE]= new Pedina(false, true, true);
        caselle [i][COL_BLACK]= new Pedina(true, false, false);
       }
    }


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

    public void isBlackExit() {

        // i=colnne  j=righe


            if (!this.BlackExit){
                boolean trovato = false;

                for (int i=1; i<=18 && !trovato; i++){
                    for (int j=0; j<=1 && !trovato; j++){
                        if (caselle [j][i] != null && !caselle [j][i].getisWhite()){
                            trovato = true;
                        }
                    }
                }
                if (!trovato){
                    BlackExit = true;
                }
            }
    }
    public void isWhiteExit() {

        // i=colnne  j=righe


        if (!this.WhiteExit){
            boolean trovato = false;

            for (int i=7; i<=24 && !trovato; i++){
                for (int j=0; j<=1 && !trovato; j++){
                    if (caselle [j][i] != null && caselle [j][i].getisWhite()){
                        trovato = true;
                    }
                }
            }
            if (!trovato){
                WhiteExit = true;
            }
        }
    }
}

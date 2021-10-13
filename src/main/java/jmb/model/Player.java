package jmb.model;

import static jmb.ConstantsClass.*;


public class Player {

    /** La classe Player modella e gestisce la logica di un giocatore
     */
    //VARIABILI DI ISTANZA
    private String name;
    private boolean isWhite;
    private int level;
    private String imgName;

    //COSTRUTTORE CON NOME IMMAGINE SPECIFICATO
    public Player( String name, String imgName, boolean isWhite)  {

        this.name = name;
        //TODO implementare logica presenza file o meno
        this.imgName = imgName;
        this.level = 1;
        this.isWhite = isWhite;

    }

    //COSTRUTTORE CON IMMAGINE DI DEFAULT
    public Player ( String name, boolean isWhite){

        this.name = name;
        this.level = 1;
        this.isWhite = isWhite;
        if (this.isWhite) {
            this.imgName = IMG_DEF_WHITE;
        } else {
            this.imgName = IMG_DEF_BLACK;
        }

    }

    //----------------------
    //METODI GETTER E SETTER
    //----------------------

    public boolean getIsWhite() {
        return this.isWhite;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }

    public String getImgName() {
        return this.imgName;
    }

    public void setLevel( int level) {
        if (level<0) {
            level = level*-1;
        }
        this.level=level;
    }
}

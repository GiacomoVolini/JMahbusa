package jmb.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import static jmb.model.ConstantsLogic.*;


public class Player {

    /** La classe Player modella e gestisce la logica di un giocatore
     */
    //VARIABILI DI ISTANZA
    private SimpleStringProperty name;
    //private String name;
    private SimpleIntegerProperty wins;
    //private int wins;
    private SimpleIntegerProperty losses;
    //private int losses;
    private SimpleDoubleProperty winRate;
    //private double winRate;



    //COSTRUTTORE
    public Player (String name)  {
        this.name = new SimpleStringProperty(name);
        this.wins = new SimpleIntegerProperty(0);
        this.losses = new SimpleIntegerProperty(0);
        this.winRate = new SimpleDoubleProperty(0);
    }

    public Player (String name, String wins, String losses) {
        this.name = new SimpleStringProperty(name);
        this.wins = new SimpleIntegerProperty(Integer.parseInt(wins));
        this.losses = new SimpleIntegerProperty(Integer.parseInt(losses));
        double winRate = (double)this.wins.getValue() / (double)this.losses.getValue();
        this.winRate = new SimpleDoubleProperty(winRate);
        //this.name = name;
        //this.wins = Integer.parseInt(wins);
        //this.losses = Integer.parseInt(losses);
        //this.winRate = (double)this.wins/(double)this.losses ;
    }



    //----------------------
    //METODI GETTER E SETTER
    //----------------------

    public String getName () {
        return this.name.get();
    }

    public int getWins() {
        return this.wins.get();
    }

    public int getLosses() {
        return this.losses.get();
    }

    public double getWinRate() {
        return this.winRate.get();
    }

    //TODO forse metodi set?

}

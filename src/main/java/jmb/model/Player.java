package jmb.model;

import javafx.beans.property.*;

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
        this.setWinRate();
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

    public SimpleStringProperty nameProperty() { return this.name; }

    public int getWins() {
        return this.wins.get();
    }

    public SimpleIntegerProperty winsProperty() {
        return this.wins;
    }

    public int getLosses() {
        return this.losses.get();
    }

    public SimpleIntegerProperty lossesProperty() {
        return this.losses;
    }

    public double getWinRate() {
        return this.winRate.get();
    }

    public SimpleDoubleProperty winRateProperty() {
        return this.winRate;
    }

    public void addWins (int points) {
        this.wins = new SimpleIntegerProperty( this.getWins() + points);
    }

    public void addLosses (int points) {
        this.losses = new SimpleIntegerProperty( this.getLosses() + points);
    }

    public void setWinRate() {
        double winRate;
        if (this.losses.get()!=0)
            winRate = (double)this.wins.getValue() / (double)this.losses.getValue();
        else
            winRate = this.wins.getValue();
        this.winRate = new SimpleDoubleProperty(winRate);
    }

    @Override
    public String toString() {
        String out = this.getName()+";"+this.getWins()+";"+this.getLosses();
        return out;
    }

}

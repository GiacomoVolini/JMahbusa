package jmb.logic;

import javafx.beans.property.*;


public class Player {

    /* La classe Player modella e gestisce la logica di un giocatore
     */
    //VARIABILI DI ISTANZA
    private SimpleStringProperty name;
    private SimpleIntegerProperty wins;
    private SimpleIntegerProperty losses;
    private SimpleDoubleProperty winRate;

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
    }

    @Override
    public String toString() {
        String out = getName() + ";" + getWins() + ";" + getLosses();
        return out;
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
}

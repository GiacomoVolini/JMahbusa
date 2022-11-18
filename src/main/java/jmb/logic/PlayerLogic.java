package jmb.logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class PlayerLogic implements Player {

    private SimpleStringProperty name;
    private SimpleIntegerProperty wins;
    private SimpleIntegerProperty losses;
    private SimpleDoubleProperty winRate;

    public PlayerLogic(String name)  {
        this.name = new SimpleStringProperty(name);
        this.wins = new SimpleIntegerProperty(0);
        this.losses = new SimpleIntegerProperty(0);
        this.winRate = new SimpleDoubleProperty(0);
    }

    public PlayerLogic(String name, String wins, String losses) {
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

    @Override
    public String getName() {
        return this.name.get();
    }
    @Override
    public int getWins() {
        return this.wins.get();
    }
    @Override
    public int getLosses() {
        return this.losses.get();
    }
    @Override
    public double getWinRate() {
        return this.winRate.get();
    }
    @Override
    public void addWins(int points) {
        this.wins = new SimpleIntegerProperty( this.getWins() + points);
    }
    @Override
    public void addLosses(int points) {
        this.losses = new SimpleIntegerProperty( this.getLosses() + points);
    }
    @Override
    public void setWinRate() {
        double winRate;
        if (this.losses.get()!=0)
            winRate = (double)this.wins.getValue() / (double)this.losses.getValue();
        else
            winRate = this.wins.getValue();
        this.winRate = new SimpleDoubleProperty(winRate);
    }
}

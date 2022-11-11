package jmb.logic;

public interface Player {
    @Override
    String toString();

    String getName();

    int getWins();

    int getLosses();

    double getWinRate();

    void addWins(int points);

    void addLosses(int points);

    void setWinRate();
}

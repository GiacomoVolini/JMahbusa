package jmb.model;

public class Pedina {

    private boolean isWhite;
    private boolean isBlocked;

public Pedina (boolean isBlocked, boolean isWhite) {
    this.isBlocked = isBlocked;
    this.isWhite = isWhite;

}

    public boolean getisWhite() {
        return isWhite;
    }
    public boolean getisBlocked(){
        return isBlocked;
    }

    public void setisBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }
}
package jmb.model;

public class Pedina {

    private boolean isWhite;
    private boolean isBlockedW;
    private boolean isBlockedB;

    public Pedina (boolean isBlockedW, boolean isBlockedB, boolean isWhite) {
        this.isBlockedW = isBlockedW;
        this.isBlockedB = isBlockedB;
        this.isWhite = isWhite;

    }

    public boolean getisWhite() {
        return isWhite;
    }
    public boolean getisBlockedW() {
        return isBlockedW;
    }

    public boolean getisBlockedB() {
        return isBlockedB;
    }

    public void setisBlockedW(boolean isBlockedW) {
        this.isBlockedW = isBlockedW;
    }

    public void setisBlockedB(boolean isBlockedB) {
        this.isBlockedB = isBlockedB;
    }

}
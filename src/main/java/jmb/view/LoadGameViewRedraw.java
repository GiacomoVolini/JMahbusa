package jmb.view;

public class LoadGameViewRedraw extends GameBoardRedraw{

    protected static void redrawAll(LoadGameView loadGameView) {
        GameBoardRedraw.resizeAll(loadGameView);
        /* TODO
            I metodi di resize non rispondono in tempo quando si pigia su massimizza o ripristina
            Capire come risolvere
         */

    }
}

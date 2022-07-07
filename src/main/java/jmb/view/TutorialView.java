package jmb.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class TutorialView extends GameBoard{

    private final static double HORIZONTAL_RESIZE_FACTOR = 0.45;    //TODO MODIFICARE
    private final static double VERTICAL_RESIZE_FACTOR = 0.55;      //TODO MODIFICARE
    @FXML
    private Label textboxLabel1;
    @FXML
    private Label textboxLabel2;
    @FXML
    private Rectangle textboxRectangle1;
    @FXML
    private Rectangle textboxRectangle2;
    @FXML
    private AnchorPane windowPane;

    /*TODO
        Idea generale di TutorialView
            - Tutorial con delle TextBox che compaiono e scompaiono di volta in volta e del testo che spiega cosa fare
            - Magari interattivo per quanto possibile
            -
            - Idea
                - Si parte con solo tabellone, si da benvenuto
                - Si piazzano le pedine, si spiega obiettivo del gioco
                - Si evidenziano le punte, spiegando cosa sono
                - Si aprono le zone di uscita, spiegando cosa sono
                - Chiudi zone di uscita, apri cassetto dei dadi
                - Fa partire animazione dei dadi, in loop infinito, spiega dadi
                - Ferma animazione in risultato, permetti a giocatore di muovere
                - Dopo la mossa del giocatore, fai tiro per altro giocatore. Risultati fissi in base alla mossa del giocatore
                    - Se si riesce mostrare tiro doppio
                - Prepara situazione per far bloccare la pedina al giocatore, informalo
                - Fagli vedere che può impilare quante pedine vuole se non è già bloccata dall'altro
                - Cambia situazione pedine, manca solo una per aprire la zona di uscita
                    - Fare in modo che si possa muovere solo quella pedina
                - Mostrare apertura zona uscita, spiegare
                - Spiegare metodo per vittoria singola
                - Spiegare metodi per vittoria doppia
                - Far sparire pedine gradualmente
                - Chiudere cassetti
                - Aprire menu: si vuole cominciare una partita o tornare al menu?
     */

    public void initialize() {
        this.boardAnchor = windowPane;
        addChildrenToAnchor();

    }


}

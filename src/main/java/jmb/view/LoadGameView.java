import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

import static jmb.view.View.logic;

public class LoadGameView {

    @FXML
    private TitledPane saveDetailTitledPane;

    @FXML
    private AnchorPane saveDetailView;

    @FXML
    private AnchorPane savesAnchorPane;

    @FXML
    private ListView<?> savesListView;

    @FXML
    private TitledPane savesTitlePane;

    @FXML
    private SplitPane window;

    public void initialize() {
        System.out.println("Sto per andare in logic");
        ObservableList saveList = FXCollections.observableList(logic.getSaveList());
        System.out.println("Sono tornato in LoadGameView\nOra stampo la lista ricevuta");
        System.out.println(saveList.toString());
        savesListView.setItems(saveList);

        /* TODO
            Quando si crea il salvataggio bisogna anche generare un'immagine del tabellone
                - Usare snapshot, usando come viewport un rettangolo che vada a coprire solo il tabellone
                - Una volta ottenuto lo snapshot (dovrebbe generare una WritableImage), convertirlo in ByteArray
                - Convertire il ByteArray in Stringa tramite Base64.Encoder
                - Aggiungere la stringa nel salvataggio
            Quando si sceglie un salvataggio della lista:
                - Andare a recuperare tutti i dati tramite SaveGameReader
                - Utilizzare Base64.Decoder per recuperare il ByteArray dell'immagine
                - Rigenerare l'immagine da mettere in un'ImageView
                - Pescare e visualizzare i dati del salvataggio
                - Renderizzare l'ImageView
          */

    }

}
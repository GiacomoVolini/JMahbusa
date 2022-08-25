package jmb.view;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageListCell extends ListCell<Image> {
    private final ImageView view;

    ImageListCell() {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        view = new ImageView();
    }

    @Override protected void updateItem(Image item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setGraphic(null);
        } else {
            view.setImage(item);
            view.setFitWidth(25);
            view.setFitHeight(25);
            setGraphic(view);
        }
    }

}
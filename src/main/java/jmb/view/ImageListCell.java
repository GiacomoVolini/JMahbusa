package jmb.view;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageListCell extends ListCell<Image> {
    private final ImageView imageView;

    ImageListCell() {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        imageView = new ImageView();
    }

    @Override protected void updateItem(Image item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setGraphic(null);
        } else {
            imageView.setImage(item);
            imageView.setFitWidth(25);
            imageView.setFitHeight(25);
            setGraphic(imageView);
        }
    }

}

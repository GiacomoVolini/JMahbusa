module jMahbusa {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.fxml;
    requires json.simple;
    requires ini4j;
    requires org.reflections;

    opens jmb to javafx.graphics, javafx.fxml;
    opens jmb.view to javafx.graphics, javafx.fxml;
    opens jmb.view.settings to javafx.graphics, javafx.fxml;
    opens jmb.logic to javafx.base;
    opens jmb.view.utilities to javafx.fxml, javafx.graphics;

}
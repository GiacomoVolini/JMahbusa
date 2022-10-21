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
    opens jmb.logic to javafx.base;

}
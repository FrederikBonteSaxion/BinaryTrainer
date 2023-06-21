module nl.saxion.fundamentals.binary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens nl.saxion.fundamentals.binary to javafx.fxml;
    exports nl.saxion.fundamentals.binary;
    exports nl.saxion.fundamentals.utils;
    opens nl.saxion.fundamentals.utils to javafx.fxml;
}
package nl.saxion.fundamentals.binary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BinaryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BinaryApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        scene.getStylesheets().add("stylesheet.css");
        stage.setTitle("Binary trainer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String... args) {
        launch();
    }
}
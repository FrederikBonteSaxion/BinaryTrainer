package nl.saxion.fundamentals.utils;

import javafx.scene.control.Alert;

public class MessageBoxUtils {
    public static void showErrorMessage(String title, String message) {
        showAlertBox(Alert.AlertType.ERROR, title, null, message);
    }

    public static void showWarningMessage(String title, String message) {
        showAlertBox(Alert.AlertType.WARNING, title, null, message);
    }

    public static void showMessage(String title, String message) {
        showAlertBox(Alert.AlertType.INFORMATION, title, null, message);
    }

    public static void showErrorMessage(String title, String header, String message) {
        showAlertBox(Alert.AlertType.ERROR, title, header, message);
    }

    public static void showWarningMessage(String title, String header, String message) {
        showAlertBox(Alert.AlertType.WARNING, title, header, message);
    }

    public static void showMessage(String title, String header, String message) {
        showAlertBox(Alert.AlertType.INFORMATION, title, header, message);
    }

    public static void showAlertBox(Alert.AlertType type, String title, String header, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

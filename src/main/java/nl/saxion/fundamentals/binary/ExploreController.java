package nl.saxion.fundamentals.binary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import nl.saxion.fundamentals.utils.HexBinUtils;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ExploreController implements Initializable {
    private static final Random RNG = new Random();
    @FXML
    public TextField txtDecimal;
    @FXML
    public TextField txtBinary;
    @FXML
    public TextField txtHexadecimal;
    @FXML
    public Button btnRandom;
    @FXML
    public TextField txtError;

    public void showRandom(MouseEvent mouseEvent) {
        int value = RNG.nextInt(256*256);
        txtBinary.setText(HexBinUtils.toBinary(value, 16));
        txtDecimal.setText(HexBinUtils.toDecimal(value, 16));
        txtHexadecimal.setText(HexBinUtils.toHexadecimal(value, 16));
    }

    public void updateHexadecimal(KeyEvent keyEvent) {
        try {
            int value = HexBinUtils.parseHexadecimal(txtHexadecimal.getText());
            clearErrors();
            txtBinary.setText(HexBinUtils.toBinary(value, 16));
            txtDecimal.setText(HexBinUtils.toDecimal(value, 16));
        } catch (NumberFormatException ex) {
            txtHexadecimal.setStyle("-fx-background-color: red");
            txtError.setText(ex.getMessage());
        }
    }

    private void clearErrors() {
        txtDecimal.setStyle("-fx-background-color: white");
        txtBinary.setStyle("-fx-background-color: white");
        txtHexadecimal.setStyle("-fx-background-color: white");
        txtError.setText("");
    }

    public void updateBinary(KeyEvent keyEvent) {
        try {
            int value = HexBinUtils.parseBinaryAnswer(txtBinary.getText());
            clearErrors();
            txtHexadecimal.setText(HexBinUtils.toHexadecimal(value, 16));
            txtDecimal.setText(HexBinUtils.toDecimal(value, 16));
        } catch (NumberFormatException ex) {
            txtBinary.setStyle("-fx-background-color: red");
            txtError.setText(ex.getMessage());
        }
    }

    public void updateDecimal(KeyEvent keyEvent) {
        try {
            int value = HexBinUtils.parseDecimalAnswer(txtDecimal.getText());
            clearErrors();
            txtHexadecimal.setText(HexBinUtils.toHexadecimal(value, 16));
            txtBinary.setText(HexBinUtils.toBinary(value, 16));
        } catch (NumberFormatException ex) {
            txtDecimal.setStyle("-fx-background-color: red");
            txtError.setText(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showRandom(null);
    }
}

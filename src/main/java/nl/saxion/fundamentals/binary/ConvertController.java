package nl.saxion.fundamentals.binary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Function;

public class ConvertController implements Initializable {
    private String LEADING = "0000000000000000000000000";
    private String answer;
    private int question = Integer.MIN_VALUE;

    private static final Random RNG = new Random();
    private static final int[] LEVEL = {16, 256, 256 * 256, 999999};
    private static final int[] LENGTH = {4, 8, 16, 24};
    @FXML
    public TextField txtQuestion;
    @FXML
    public TextField txtAnswer;
    @FXML
    public ComboBox<String> cmbType;
    @FXML
    public ComboBox<String> cmbLevel;
    @FXML
    public TextField txtResult;

    public void resetGame(MouseEvent mouseEvent) {
        generateQuestion();
    }

    private void generateQuestion() {
        int level = LEVEL[cmbLevel.getSelectionModel().getSelectedIndex()];
        switch (cmbType.getSelectionModel().getSelectedIndex()) {
            case 0: {
                generateQuestion(level, this::toDecimal, this::toBinary);
                txtAnswer.setPromptText(getBinaryPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 1: {
                generateQuestion(level, this::toBinary, this::toDecimal);
                txtAnswer.setPromptText("0000");
                break;
            }
            case 2: {
                generateQuestion(level, this::toHexadecimal, this::toBinary);
                txtAnswer.setPromptText(getBinaryPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 3: {
                generateQuestion(level, this::toBinary, this::toHexadecimal);
                txtAnswer.setPromptText(getHexadecimalPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 4: {
                generateQuestion(level, this::toDecimal, this::toHexadecimal);
                txtAnswer.setPromptText(getHexadecimalPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 5: {
                generateQuestion(level, this::toHexadecimal, this::toDecimal);
                txtAnswer.setPromptText("0000");
                break;
            }
            default: {

            }
        }
    }

    private String getHexadecimalPrompt(int length) {
        return "0x"+LEADING.substring(0, length/4);
    }

    private String getBinaryPrompt(int length) {
        return "0b"+LEADING.substring(0, length);
    }

    private String toBinary(int integer) {
        String result = Integer.toBinaryString(integer);
        int length = LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()];
        return "0b"+LEADING.substring(0, length-result.length())+result;
    }

    private String toDecimal(int integer) {
        return "" + integer;
    }

    private String toHexadecimal(int integer) {
        String result = Integer.toHexString(integer);
        int length = LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]/4;
        return "0x"+LEADING.substring(0, length-result.length())+result;
    }


    private void generateQuestion(int level, Function<Integer, String> question, Function<Integer, String> answer) {
        this.question = RNG.nextInt((level<=256)?0:500, level);
        this.txtQuestion.setText(question.apply(this.question));
        this.answer = answer.apply(this.question);
        this.txtAnswer.setText("");
    }


    public void checkAnswer(MouseEvent mouseEvent) {
        int value;
        if (answer.startsWith("0b")) {
            value = checkBinaryAnswer();
        } else if (answer.startsWith("0x")) {
            value = checkHexadecimalAnswer();
        } else {
            value = checkDecimalAnswer();
        }
        if (value==question) {
            txtResult.setText("CORRECT!!!");
        } else {
            txtResult.setText("WRONG!!!");
        }
        generateQuestion();
    }

    private int checkHexadecimalAnswer() {
        String answer = txtAnswer.getText();
        if (answer.startsWith("0x")) {
            answer = answer.substring(2);
        }
        try {
            return Integer.parseInt(answer, 16);
        } catch (NumberFormatException ex) {
            showWarningMessage("Not hexadecimal", "Your answer is not recognised as a hex value", ex.getMessage());
            return Integer.MIN_VALUE;
        }
    }

    private int checkDecimalAnswer() {
        String answer = txtAnswer.getText();
        try {
            return Integer.parseInt(answer);
        } catch (NumberFormatException ex) {
            showWarningMessage("Not decimal", "Your answer is not recognised as a decimal value", ex.getMessage());
            return Integer.MIN_VALUE;
        }
    }

    private int checkBinaryAnswer() {
        String answer = txtAnswer.getText();
        if (answer.startsWith("0b")) {
            answer = answer.substring(2);
        }
        try {
            return Integer.parseInt(answer, 2);
        } catch (NumberFormatException ex) {
            showWarningMessage("Not binary", "Your answer is not recognised as a binary value", ex.getMessage());
            return Integer.MIN_VALUE;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbLevel.getSelectionModel().select(0);
        cmbType.getSelectionModel().select(0);
    }

    private void showErrorMessage(String title, String message) {
        showAlertBox(Alert.AlertType.ERROR, title, null, message);
    }

    private void showWarningMessage(String title, String message) {
        showAlertBox(Alert.AlertType.WARNING, title, null, message);
    }

    private void showMessage(String title, String message) {
        showAlertBox(Alert.AlertType.INFORMATION, title, null, message);
    }

    private void showErrorMessage(String title, String header, String message) {
        showAlertBox(Alert.AlertType.ERROR, title, header, message);
    }

    private void showWarningMessage(String title, String header, String message) {
        showAlertBox(Alert.AlertType.WARNING, title, header, message);
    }

    private void showMessage(String title, String header, String message) {
        showAlertBox(Alert.AlertType.INFORMATION, title, header, message);
    }

    private void showAlertBox(Alert.AlertType type, String title, String header, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

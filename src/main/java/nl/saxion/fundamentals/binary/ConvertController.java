package nl.saxion.fundamentals.binary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import nl.saxion.fundamentals.utils.HexBinUtils;
import nl.saxion.fundamentals.utils.MessageBoxUtils;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

public class ConvertController implements Initializable {
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
        int selection = cmbType.getSelectionModel().getSelectedIndex();
        if (selection==6) {
            selection = RNG.nextInt(6);
        }
        switch (selection) {
            case 0: {
                generateQuestion(level, HexBinUtils::toDecimal, HexBinUtils::toBinary);
                txtAnswer.setPromptText(HexBinUtils.getBinaryPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 1: {
                generateQuestion(level, HexBinUtils::toBinary, HexBinUtils::toDecimal);
                txtAnswer.setPromptText("0000");
                break;
            }
            case 2: {
                generateQuestion(level, HexBinUtils::toHexadecimal, HexBinUtils::toBinary);
                txtAnswer.setPromptText(HexBinUtils.getBinaryPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 3: {
                generateQuestion(level, HexBinUtils::toBinary, HexBinUtils::toHexadecimal);
                txtAnswer.setPromptText(HexBinUtils.getHexadecimalPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 4: {
                generateQuestion(level, HexBinUtils::toDecimal, HexBinUtils::toHexadecimal);
                txtAnswer.setPromptText(HexBinUtils.getHexadecimalPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()]));
                break;
            }
            case 5: {
                generateQuestion(level, HexBinUtils::toHexadecimal, HexBinUtils::toDecimal);
                txtAnswer.setPromptText("0000");
                break;
            }
            default: {

            }
        }
    }

    private void generateQuestion(int level, BiFunction<Integer, Integer, String> question, BiFunction<Integer, Integer, String> answer) {
        this.question = RNG.nextInt((level<=256)?0:500, level);
        int length = LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()];
        this.txtQuestion.setText(question.apply(this.question, length));
        this.answer = answer.apply(this.question, length);
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
        if (answer.equals(txtAnswer.getText())) {
            txtResult.setText("CORRECT!!!");
        } else {
            txtResult.setText("WRONG!!!");
        }
        generateQuestion();
    }

    private int checkHexadecimalAnswer() {
        try {
            return HexBinUtils.parseHexadecimal(txtAnswer.getText());
        } catch (NumberFormatException ex) {
            MessageBoxUtils.showWarningMessage("Not hexadecimal", "Your answer is not recognised as a hex value", ex.getMessage());
            return Integer.MIN_VALUE;
        }
    }

    private int checkDecimalAnswer() {
        try {
            return HexBinUtils.parseDecimalAnswer(txtAnswer.getText());
        } catch (NumberFormatException ex) {
            MessageBoxUtils.showWarningMessage("Not decimal", "Your answer is not recognised as a decimal value", ex.getMessage());
            return Integer.MIN_VALUE;
        }
    }

    private int checkBinaryAnswer() {
        try {
            return HexBinUtils.parseBinaryAnswer(txtAnswer.getText());
        } catch (NumberFormatException ex) {
            MessageBoxUtils.showWarningMessage("Not binary", "Your answer is not recognised as a binary value", ex.getMessage());
            return Integer.MIN_VALUE;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbLevel.getSelectionModel().select(0);
        cmbType.getSelectionModel().select(0);
    }
}

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

public class IncDecController implements Initializable {
        private String answer;
        private int question = Integer.MIN_VALUE;

        private static final Random RNG = new Random();
        private static final int[] LEVEL = {16, 256, 256 * 256};
        private static final int[] LENGTH = {4, 8, 16};
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
            if (selection==4) {
                selection = RNG.nextInt(4);
            }
            switch (selection) {
                case 0: {
                    generateQuestion(level, true, HexBinUtils::toBinary);
                    txtAnswer.setPromptText(HexBinUtils.getBinaryPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()])+" + 1");
                    break;
                }
                case 1: {
                    generateQuestion(level, false, HexBinUtils::toBinary);
                    txtAnswer.setPromptText(HexBinUtils.getBinaryPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()])+" - 1");
                    break;
                }
                case 2: {
                    generateQuestion(level, true, HexBinUtils::toHexadecimal);
                    txtAnswer.setPromptText(HexBinUtils.getHexadecimalPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()])+" + 1");
                    break;
                }
                case 3: {
                    generateQuestion(level, false, HexBinUtils::toHexadecimal);
                    txtAnswer.setPromptText(HexBinUtils.getHexadecimalPrompt(LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()])+" - 1");
                    break;
                }
                default: {

                }
            }
        }

        private void generateQuestion(int level, boolean increase, BiFunction<Integer, Integer, String> question) {
            this.question = RNG.nextInt((level<=256)?0:500, level);
            int length = LENGTH[cmbLevel.getSelectionModel().getSelectedIndex()];
            this.txtQuestion.setText(question.apply(this.question, length));
            this.answer = question.apply(this.question + (increase?+1:-1), length);
            this.txtAnswer.setText("");
        }


        public void checkAnswer(MouseEvent mouseEvent) {
            int value;
            if (answer.startsWith("0b")) {
                value = checkBinaryAnswer();
            } else {
                value = checkHexadecimalAnswer();
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

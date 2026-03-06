import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CalculatorController {

    @FXML private TextField number1Field;
    @FXML private TextField number2Field;
    @FXML private Label resultLabel;

    @FXML
    private void onCalculateClick() {
        try {
            double num1 = Double.parseDouble(number1Field.getText().trim());
            double num2 = Double.parseDouble(number2Field.getText().trim());

            double sum = num1 + num2;
            double product = num1 * num2;
            double subtract = num1 - num2;

            Double division = null;
            String divisionText;

            if (num2 == 0) {
                divisionText = "Cannot divide by zero";
            } else {
                division = num1 / num2;
                divisionText = String.valueOf(division);
            }

            resultLabel.setText(
                    "Sum: " + sum +
                            "\nProduct: " + product +
                            "\nSubtract: " + subtract +
                            "\nDivision: " + divisionText
            );

            ResultService.saveResult(num1, num2, sum, product, subtract, division);

        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter valid numbers!");
        } catch (Exception e) {
            resultLabel.setText("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
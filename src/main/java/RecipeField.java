import javax.swing.*;

/**
 * Author: Oliver Hanson-Bragg
 * Created on: 29/05/2014
 */
public class RecipeField {

    private static int numberFields = 0;
    JTextField textField;

    RecipeField(JPanel parentPanel) {

        JLabel label = new JLabel("URL of recipe: ");
        textField = new JTextField(50);

        parentPanel.add(label, "cell 1" + " " + numberFields + ", gapleft 10");
        parentPanel.add(textField, "cell 1" + " " + numberFields);

        parentPanel.revalidate();
        numberFields++;
    }

    public String getTextFieldContent() {
        return textField.getText();
    }
}

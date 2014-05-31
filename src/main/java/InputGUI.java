import net.miginfocom.swing.MigLayout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Oliver Hanson-Bragg
 * Created on: 29/05/2014
 */
public class InputGUI extends JFrame {
    JPanel mainPanel;
    ArrayList<RecipeField> recipeFieldArrayList = new ArrayList<RecipeField>();
    ArrayList<Document> docArrayList = new ArrayList<Document>();

    InputGUI() {
        setTitle("AllRecipes.co.uk ingredient parser");
        build();

        setVisible(true);
        pack();
    }

    public void build() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());

        mainPanel.add(getParseButton(), "cell 0 0");
        mainPanel.add(getAddBtn(), "cell 0 1");

        recipeFieldArrayList.add(new RecipeField(mainPanel));

        add(mainPanel);
    }

    public JButton getAddBtn() {
        JButton addFieldBtn = new JButton("Add field");
        addFieldBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recipeFieldArrayList.add(new RecipeField(mainPanel));
                pack();
            }
        });

        return addFieldBtn;
    }

    public JButton getParseButton() {
        JButton parseButton = new JButton("Get ingredients");
        parseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (RecipeField recipeField : recipeFieldArrayList) {
                    try {
                        Document doc = Jsoup.connect(recipeField.getTextFieldContent()).get();
                        docArrayList.add(doc);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                new OutputGUI(docArrayList);
            }
        });
        return parseButton;
    }
}

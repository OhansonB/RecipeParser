import net.miginfocom.swing.MigLayout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Oliver Hanson-Bragg
 * Created on: 29/05/2014
 */
public class GUI extends JFrame {
    JPanel mainPanel;
    ArrayList<RecipeField> recipeFieldArrayList = new ArrayList<RecipeField>();

    GUI() {
        setTitle("AllRecipes.co.uk ingredient parser");
        build();
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
                        Elements elements = doc.select("meta[name=title]");
                        Elements content = doc.getElementsByClass("recipeIngredients");
                        Elements content2 = content.select("ul li span");

                        for (Element element : elements) {
                            final String title = element.attr("content");
                            System.out.format("%s\n", title);

                            for (Element element2 : content2) {
                                System.out.format("%s %s\n", element2.attr("Ingredient"), element2.text());
                            }

                            System.out.println();
                        }


                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        return parseButton;
    }
}

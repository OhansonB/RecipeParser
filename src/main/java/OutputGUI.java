import net.miginfocom.swing.MigLayout;
import org.apache.commons.io.FileUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Oliver Hanson-Bragg
 * Created on: 31/05/2014
 */
public class OutputGUI extends JFrame {

    JTextArea textArea;
    ArrayList<Document> documentArray;
    String output;

    OutputGUI(ArrayList<Document> docArray) {
        documentArray = docArray;

        setTitle("Your ingredients list");
        build();
        setVisible(true);
        pack();
    }

    public void build() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());

        mainPanel.add(getTextArea(documentArray), "cell 0 1, wrap");
        mainPanel.add(getSaveBtn(), "cell 0 0");
        mainPanel.add(getEmailBtn(), "cell 0 0");

        add(mainPanel);
    }

    public JButton getEmailBtn() {
        JButton emailBtn = new JButton("Send to email");
        emailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmailUI(output);
            }
        });
        return emailBtn;
    }

    public JButton getSaveBtn() {
        JButton saveBtn = new JButton("Save to file");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileUtils.writeStringToFile(new File("AllRecipes.co.uk ingredients list.txt"), output);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        return saveBtn;
    }

    public JTextArea getTextArea(ArrayList<Document> documentArray) {
        if (textArea == null) {
            textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.append(parseDocuments(documentArray));
        }

        return textArea;
    }

    public String parseDocuments(ArrayList<Document> arrayList) {
        output = new String();

        for (Document doc : arrayList) {
            Elements elements = doc.select("meta[name=title]");
            Elements content = doc.getElementsByClass("recipeIngredients").select("ul li span");

            for (Element element : elements) {
                final String title = element.attr("content");
                output += (title + "\n");
                //System.out.format("%s\n", title);

                for (Element element2 : content) {
                    //System.out.format("%s %s\n\n", element2.attr("Ingredient"), element2.text());
                    output += "   " + element2.text() + "\n";
                }

                output += "\n";
            }
        }
        return output;
    }
}

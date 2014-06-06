import net.miginfocom.swing.MigLayout;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author: Oliver Hanson-Bragg
 * Created on: 06/06/2014
 */
public class EmailUI extends JFrame {
    JTextField emailRecipientTF, emailSenderTF, emailSenderPWF;
    String output;

    EmailUI(String output) {
        this.output = output;
        setTitle("Send ingredient list to email address");
        build();

        setVisible(true);
        setResizable(false);
        pack();
    }

    public void build() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());

        mainPanel.add(getEmailRecipient(), "cell 0 1");
        mainPanel.add(getSendEmailBtn(), "cell 0 1");
        mainPanel.add(getEmailSenderTF(), "cell 0 0, wrap");
        mainPanel.add(getEmailSenderPWF(), "cell 0 0");

        add(mainPanel);
    }

    public JTextField getEmailRecipient() {
        emailRecipientTF = new JTextField(50);
        emailRecipientTF.setText("Email recipient");
        return emailRecipientTF;
    }

    public JTextField getEmailSenderTF() {
        emailSenderTF = new JTextField(50);
        emailSenderTF.setText("Your email address");
        return emailSenderTF;
    }

    public JTextField getEmailSenderPWF() {
        emailSenderPWF = new JPasswordField(25);
        emailSenderPWF.setText("Email password");
        return emailSenderPWF;
    }

    public JButton getSendEmailBtn() {
        JButton sendEmailBtn = new JButton("Send email");
        sendEmailBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Email email = new SimpleEmail();
                email.setHostName("imap.gmail.com");
                email.setSmtpPort(465);
                email.setAuthentication(emailSenderTF.getText(), emailSenderPWF.getText());
                email.setSSLOnConnect(true);

                try {
                    email.setFrom(emailSenderTF.getText());
                    email.setMsg(output);
                    email.setSubject("Your AllRecipes.co.uk ingredients list");
                    email.addTo(emailRecipientTF.getText());
                    email.send();
                    System.out.println("Sent");
                } catch (EmailException e1) {
                    e1.printStackTrace();
                }
            }
        });
        return sendEmailBtn;
    }
}

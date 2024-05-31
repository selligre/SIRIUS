package edu.cgl.sirius.application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SubscribeView {

    public SubscribeView() {
        JFrame frame = new JFrame("MyPanel");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Ville partagée");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        // construct components
        JLabel titleLabel = new JLabel("INSCRIPTION");
        JLabel firstNameLabel = new JLabel("Prénom :");
        JTextField firstNameTextField = new JTextField(5);
        JLabel lastNameLabel = new JLabel("Nom :");
        JTextField lastNameTextField = new JTextField(5);
        JLabel pseudoLabel = new JLabel("Pseudo :");
        JTextField pseudoTextField = new JTextField(5);
        JLabel emailLabel = new JLabel("Email :");
        JTextField emailTextField = new JTextField(5);
        JLabel passwordLabel = new JLabel("Mot de passe :");
        JPasswordField passwordPasswordField = new JPasswordField(5);
        JButton subscribeButton = new JButton("S'INSCRIRE");
        subscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "Prénom : " + firstNameTextField.getText()
                        + ", Nom : " + lastNameTextField.getText()
                        + ", Pseudo : " + pseudoTextField.getText()
                        + ", Email : " + emailTextField.getText()
                        + ", Mot de passe : " + new String(passwordPasswordField.getPassword());
                JOptionPane.showMessageDialog(null, message);
            }
        });

        // adjust size and set layout
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);

        // add components
        panel.add(titleLabel);
        panel.add(firstNameLabel);
        panel.add(firstNameTextField);
        panel.add(lastNameLabel);
        panel.add(lastNameTextField);
        panel.add(pseudoLabel);
        panel.add(pseudoTextField);
        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(passwordLabel);
        panel.add(passwordPasswordField);
        panel.add(subscribeButton);

        // set component bounds (only needed by Absolute Positioning)
        titleLabel.setBounds(350, 50, 100, 25);
        firstNameLabel.setBounds(300, 150, 100, 25);
        firstNameTextField.setBounds(400, 150, 200, 25);
        lastNameLabel.setBounds(300, 200, 100, 25);
        lastNameTextField.setBounds(400, 200, 200, 25);
        pseudoLabel.setBounds(300, 250, 100, 25);
        pseudoTextField.setBounds(400, 250, 200, 25);
        emailLabel.setBounds(300, 300, 100, 25);
        emailTextField.setBounds(400, 300, 200, 25);
        passwordLabel.setBounds(300, 350, 100, 25);
        passwordPasswordField.setBounds(400, 350, 200, 25);
        subscribeButton.setBounds(300, 450, 200, 25);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SubscribeView();
    }
}

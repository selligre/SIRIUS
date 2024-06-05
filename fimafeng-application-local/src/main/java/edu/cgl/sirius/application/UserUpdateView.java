package edu.cgl.sirius.application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserUpdateView {
    public UserUpdateView() {
        JFrame frame = new JFrame("MyPanel");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Ville partagée");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        // construct components
        JLabel titleLabel = new JLabel("UPDATE USER : " + Application.getConnectedUser().getDisplay_name());
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

        final String SELECT_ITEM = "<Sélectionner>";

        JLabel locationLabel = new JLabel("Quartier favori :");
        String locations[] = { SELECT_ITEM, "Plaza", "Court", "Plaza", "Pass", "Place", "Park", "Bar St Patricks",
                "Place de la Mairie", "Parc du chateau", "Salle de fêtes", "Piscine", "Cinéma", "Théâtre", "Mairie" };
        int locations_id[] = { 0, 9, 10, 11, 12, 13, 14, 4, 5, 6, 7, 2, 3, 1, 8 };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox locationComboBox = new JComboBox(locations);

        JLabel tagLabel = new JLabel("Tag favori :");
        String tags[] = { SELECT_ITEM, "Concert", "Festival", "Séniors", "Couple", "Tout public", "Musée", "Peinture",
                "Théatre", "Visite", "Adultes", "Chorale", "Enfants", "Jeunes" };
        String tags_id[] = { null, "1", "3", "7", "8", "9", "10", "11", "12", "13", "6", "2", "4", "5" };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox tagComboBox = new JComboBox(tags);

        JButton subscribeButton = new JButton("UPDATE");
        subscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String message = "Prénom : " + firstNameTextField.getText()
                // + ", Nom : " + lastNameTextField.getText()
                // + ", Pseudo : " + pseudoTextField.getText()
                // + ", Email : " + emailTextField.getText()
                // + ", Mot de passe : " + new String(passwordPasswordField.getPassword())
                // + ", Quartier favori ID : " + locations[locationComboBox.getSelectedIndex()]
                // + ", Tag favori ID : " + tags[tagComboBox.getSelectedIndex()];
                // JOptionPane.showMessageDialog(null, message);

                // MainInsertUser mainInsertUser = new MainInsertUser("INSERT_USER",
                // firstNameTextField.getText(),
                // lastNameTextField.getText(), pseudoTextField.getText(),
                // emailTextField.getText(),
                // new String(passwordPasswordField.getPassword()));

            }
        });

        firstNameTextField.setText("user_first_name");
        firstNameTextField.setText(Application.getConnectedUser().getFirst_name());
        lastNameTextField.setText(Application.getConnectedUser().getLast_name());
        pseudoTextField.setText(Application.getConnectedUser().getDisplay_name());
        emailTextField.setText(Application.getConnectedUser().getEmail());
        passwordPasswordField.setText(Application.getConnectedUser().getPassword());
        locationComboBox.setSelectedIndex(Application.getConnectedUser().getLocation());
        tagComboBox.setSelectedIndex(Application.getConnectedUser().getTag());

        // adjust size and set layout
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);

        titleLabel.setEnabled(true);
        firstNameLabel.setEnabled(true);
        firstNameTextField.setEnabled(true);
        lastNameLabel.setEnabled(true);
        lastNameTextField.setEnabled(true);
        pseudoLabel.setEnabled(true);
        pseudoTextField.setEnabled(true);
        emailLabel.setEnabled(true);
        emailTextField.setEnabled(true);
        passwordLabel.setEnabled(true);
        passwordPasswordField.setEnabled(true);
        locationLabel.setEnabled(true);
        locationComboBox.setEnabled(true);
        tagLabel.setEnabled(true);
        tagComboBox.setEnabled(true);
        subscribeButton.setEnabled(false);

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
        panel.add(locationLabel);
        panel.add(subscribeButton);
        panel.add(tagLabel);
        panel.add(locationComboBox);
        panel.add(tagComboBox);

        // set component bounds (only needed by Absolute Positioning)
        titleLabel.setBounds(350, 50, 500, 25);
        firstNameLabel.setBounds(100, 150, 100, 25);
        firstNameTextField.setBounds(200, 150, 200, 25);
        lastNameLabel.setBounds(100, 200, 100, 25);
        lastNameTextField.setBounds(200, 200, 200, 25);
        pseudoLabel.setBounds(100, 250, 100, 25);
        pseudoTextField.setBounds(200, 250, 200, 25);
        emailLabel.setBounds(100, 300, 100, 25);
        emailTextField.setBounds(200, 300, 200, 25);
        passwordLabel.setBounds(100, 350, 100, 25);
        passwordPasswordField.setBounds(200, 350, 200, 25);
        locationLabel.setBounds(450, 150, 100, 25);
        locationComboBox.setBounds(550, 150, 200, 25);
        tagLabel.setBounds(450, 200, 100, 25);
        tagComboBox.setBounds(550, 200, 200, 25);
        subscribeButton.setBounds(300, 450, 200, 25);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public void reorderWithDefaultOnTop(String[] array, String target) {
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(array));
        int posT = list.indexOf(target);
        String pos0 = array[0];
        array[0] = target;
        array[posT] = pos0;
    }

    public static void main(String[] args) {
        new SubscribeView();
    }
}

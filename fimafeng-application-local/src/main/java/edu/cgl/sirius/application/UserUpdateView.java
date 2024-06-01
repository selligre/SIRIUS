package edu.cgl.sirius.application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.cgl.sirius.business.AnnounceParser;
import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.client.MainInsertUser;
import edu.cgl.sirius.client.MainSelectLocations;
import edu.cgl.sirius.client.MainSelectUsersEmails;

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
        JLabel titleLabel = new JLabel("UPDATE USER");
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

        JLabel locationLabel = new JLabel("Quartier favori :");
        AnnounceParser parser = new AnnounceParser();
        HashMap<String, String> map_locationsItems = new HashMap<>();
        try {
            MainSelectLocations mainSelectLocations = new MainSelectLocations("SELECT_ALL_LOCATIONS");
            parser.updateLocations(mainSelectLocations.getLocations());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final String SELECT_ITEM = "<Sélectionner>";
        map_locationsItems.put(SELECT_ITEM, "0");
        Map<String, String> parsedLocations = parser.getParsedLocations();
        for (String key : parsedLocations.keySet()) {
            map_locationsItems.put(parsedLocations.get(key), key);
        }
        String[] cb_locationsItems = (String[]) map_locationsItems.keySet().toArray(new String[0]);
        reorderWithDefaultOnTop(cb_locationsItems, SELECT_ITEM);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox locationComboBox = new JComboBox(cb_locationsItems);
        locationComboBox.setSelectedItem(SELECT_ITEM);

        JLabel tagLabel = new JLabel("Tag favori :");
        String tags[] = { SELECT_ITEM, "Concert", "Festival", "Séniors", "Couple", "Tout public", "Musée", "Peinture",
                "Théatre", "Visite", "Adultes", "Chorale", "Enfants", "Jeunes" };
        String tags_id[] = { null, "1", "3", "7", "8", "9", "10", "11", "12", "13", "6", "2", "4", "5" };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox tagComboBox = new JComboBox(tags);

        JButton subscribeButton = new JButton("S'INSCRIRE");
        subscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (firstNameTextField.getText().equals("")
                        || lastNameTextField.getText().equals("")
                        || pseudoTextField.getText().equals("")
                        || emailTextField.getText().equals("")
                        || !emailTextField.getText().contains("@")
                        || new String(passwordPasswordField.getPassword()).equals("")
                        || locationComboBox.getSelectedItem().equals(SELECT_ITEM)
                        || lastNameTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "ERROR: Missing data.");
                    return;
                }
                try {
                    MainSelectUsersEmails mainSelectUsersMails = new MainSelectUsersEmails("SELECT_ALL_USERS_EMAILS");
                    Boolean testEmail = false;
                    for (User user : mainSelectUsersMails.getUsers().getUsers()) {
                        if (user.getEmail().equals(emailTextField.getText())) {
                            testEmail = true;
                        }
                    }
                    if (testEmail) {
                        JOptionPane.showMessageDialog(null, "ERROR: Email already used.");
                    } else {
                        String message = "Prénom : " + firstNameTextField.getText()
                                + ", Nom : " + lastNameTextField.getText()
                                + ", Pseudo : " + pseudoTextField.getText()
                                + ", Email : " + emailTextField.getText()
                                + ", Mot de passe : " + new String(passwordPasswordField.getPassword())
                                + ", Quartier favori : " + locationComboBox.getSelectedItem()
                                + ", Tag favori : " + tags[tagComboBox.getSelectedIndex()];
                        JOptionPane.showMessageDialog(null, message);
                        // TODO: INSERT request
                        MainInsertUser mainInsertUser = new MainInsertUser("INSERT_USER", firstNameTextField.getText(),
                                lastNameTextField.getText(), pseudoTextField.getText(), emailTextField.getText(),
                                new String(passwordPasswordField.getPassword()));

                    }
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
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
        panel.add(locationLabel);
        panel.add(subscribeButton);
        panel.add(tagLabel);
        panel.add(locationComboBox);
        panel.add(tagComboBox);

        // set component bounds (only needed by Absolute Positioning)
        titleLabel.setBounds(350, 50, 100, 25);
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

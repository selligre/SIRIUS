package edu.cgl.sirius.application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.business.dto.UserLocation;
import edu.cgl.sirius.business.dto.UserTag;
import edu.cgl.sirius.client.MainSelectUsers;
import edu.cgl.sirius.client.MainSelectUsersLocations;
import edu.cgl.sirius.client.MainSelectUsersTags;

public class LoginView {

    private final static Logger logger = LoggerFactory.getLogger("L o g i n - V i e w");

    public LoginView() {
        JFrame frame = new JFrame("Ville partagée");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        // construct components
        JTextField idTextField = new JTextField(5);
        JLabel idLabel = new JLabel("Adresse mail :");
        JLabel pwdLabel = new JLabel("Mot de passe :");
        JPasswordField pwdPasswordField = new JPasswordField(5);
        JLabel titleLabel = new JLabel("VILLE PARTAGEE");
        titleLabel.setFont(new Font("Calibri", Font.ROMAN_BASELINE, 60));
        JButton connexionButton = new JButton("SE CONNECTER");
        // set par défaut des identifiants pour gagner du temps
        idTextField.setText("villepartagee@mairie.fr");
        pwdPasswordField.setText("ceci est un mdp");

        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String message = "Adresse mail : " + idTextField.getText()
                // + ", Mot de passe : " + new String(pwdPasswordField.getPassword());
                // JOptionPane.showMessageDialog(null, message);
                try {
                    logger.info("Launch querry (user login)");
                    MainSelectUsers selectCredentialsUsers = new MainSelectUsers("SELECT_USER_TO_LOGIN",
                            idTextField.getText(), new String(pwdPasswordField.getPassword()));
                    logger.info("Querry ended!");
                    if (!selectCredentialsUsers.getUsers().getUsers().isEmpty()) {
                        logger.info("User exist, login in!");
                        new Application();
                        ArrayList<User> userList = new ArrayList<>(selectCredentialsUsers.getUsers().getUsers());
                        Application.connectedUser = userList.get(0);
                        frame.setVisible(false);
                        frame.setEnabled(false);
                        frame.repaint();

                        MainSelectUsersTags mainSelectUsersTags = new MainSelectUsersTags(
                                "SELECT_ALL_USERS_TAGS");
                        for (UserTag userTag : mainSelectUsersTags.getUserTags().getUserTags()) {
                            if (userTag.getRef_user_id().equals(Application.connectedUser.getUser_id())) {
                                Application.connectedUser.setTag(Integer.valueOf(userTag.getRef_tag_id()));
                            }
                        }
                        MainSelectUsersLocations mainSelectUsersLocations = new MainSelectUsersLocations(
                                "SELECT_ALL_USERS_LOCATIONS");
                        for (UserLocation userLocation : mainSelectUsersLocations.getUserLocations()
                                .getUserLocations()) {
                            if (userLocation.getRef_user_id().equals(Application.connectedUser.getUser_id())) {
                                Application.connectedUser
                                        .setLocation(Integer.valueOf(userLocation.getRef_location_id()));
                            }
                        }
                    } else if (selectCredentialsUsers.getUsers().getUsers().isEmpty()) {
                        logger.info("User mail x pswd doesn't exist");
                        JOptionPane.showMessageDialog(null, "Compte inexistant ou mauvais indentifants");
                    } else {
                        logger.warn("THAT SHOULD NOT HAPPEN, WHAT'S GOING ON HERE?!");
                        JOptionPane.showMessageDialog(null, "ERREUR TECHNIQUE");
                    }
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }

            }
        });
        JButton subscribeButton = new JButton("S'INSCRIRE");
        subscribeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logger.info("Opening register view");
                new SubscribeView();
            }
        });

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(300, 250, 700, 25);
        panel.add(separator);
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setBounds(300, 485, 700, 25);
        panel.add(separator2);
        JSeparator separator3 = new JSeparator(SwingConstants.VERTICAL);
        separator3.setBounds(300, 250, 25, 235);
        panel.add(separator3);
        JSeparator separator4 = new JSeparator(SwingConstants.VERTICAL);
        separator4.setBounds(1000, 250, 25, 235);
        panel.add(separator4);

        // set component bounds (only needed by Absolute Positioning)
        idTextField.setBounds(600, 300, 200, 25);
        idLabel.setBounds(500, 300, 100, 25);
        pwdLabel.setBounds(500, 350, 100, 25);
        pwdPasswordField.setBounds(600, 350, 200, 25);
        titleLabel.setBounds(450, 150, 600, 100);
        connexionButton.setBounds(550, 425, 200, 25);
        subscribeButton.setBounds(550, 525, 200, 25);

        // adjust size and set layout
        panel.setPreferredSize(new Dimension(1280, 720));
        panel.setLayout(null);

        // add components
        panel.add(idTextField);
        panel.add(idLabel);
        panel.add(pwdLabel);
        panel.add(pwdPasswordField);
        panel.add(titleLabel);
        panel.add(connexionButton);
        panel.add(subscribeButton);

        frame.add(panel);

        // frame.pack();
        frame.validate();
        frame.repaint();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginView();
    }

}

package edu.cgl.sirius.application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.business.dto.UserLocation;
import edu.cgl.sirius.business.dto.UserTag;
import edu.cgl.sirius.client.MainSelectUsersEmails;
import edu.cgl.sirius.client.MainSelectUsersLocations;
import edu.cgl.sirius.client.MainSelectUsersTags;

/*
 * La création d'un profil utilisateur (avec des règles métiers pour vérifier le format de son e-mail, 
 * l'unicité de son e-mail, etc.) ainsi que la modification de son profil (notamment pour modifier les tags). 
 * La home page de l'utilisateur devra remonter des suggestions d'activités (peut être mettre en place une 
 * règle pour éviter de se retrouver avec 5000 suggestions) et de pouvoir s'y inscrire.
 * 
 * par rapport à ma proposition de UC, je te propose de simplifier au maximum les informations d'un profil utilisateur 
 * (prénom, adresse email) + des tags + proposition d'activités en fonction de son profil 
 * + un bouton inscription (à faire en dernier temps)
 * 
 * 1) avoir une gestion des utilisateurs (donc création, insertion dans la BDD, ainsi que identification/connexion au client local)
 * 2) la suggestion d'activités en fonction de l'utilisateur connecté
 * 3) un moyen pour un utilisateur de s'inscrire à une activité (en lien avec le UC de Clément)
 */

public class LoginView {

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
        JButton connexionButton = new JButton("SE CONNECTER");
        connexionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String message = "Adresse mail : " + idTextField.getText()
                // + ", Mot de passe : " + new String(pwdPasswordField.getPassword());
                // JOptionPane.showMessageDialog(null, message);
                try {
                    MainSelectUsersEmails mainSelectUsersMails = new MainSelectUsersEmails("SELECT_ALL_USERS_EMAILS");
                    Boolean testEmail = false;
                    Boolean testPassword = false;
                    for (User user : mainSelectUsersMails.getUsers().getUsers()) {
                        if (user.getEmail().equals(idTextField.getText())) {
                            if (user.getPassword().equals(new String(pwdPasswordField.getPassword()))) {
                                Application.setUserMail(idTextField.getText());
                                Application.setUserId(user.getUser_id());
                                MainSelectUsersTags mainSelectUsersTags = new MainSelectUsersTags(
                                        "SELECT_ALL_USERS_TAGS");
                                for (UserTag userTag : mainSelectUsersTags.getUserTags().getUserTags()) {
                                    if (userTag.getRef_user_id().equals(user.getUser_id())) {
                                        Application.setUserTagId(userTag.getRef_tag_id());
                                    }
                                }
                                MainSelectUsersLocations mainSelectUsersLocations = new MainSelectUsersLocations(
                                        "SELECT_ALL_USERS_LOCATIONS");
                                for (UserLocation userLocation : mainSelectUsersLocations.getUserLocations()
                                        .getUserLocations()) {
                                    if (userLocation.getRef_user_id().equals(user.getUser_id())) {
                                        Application.setUserLocationId(userLocation.getRef_location_id());
                                    }
                                }
                                new Application();
                                testPassword = true;
                                frame.setVisible(false);
                                frame.setEnabled(false);
                                frame.repaint();
                            }
                            testEmail = true;
                        }
                    }
                    if (!testEmail) {
                        JOptionPane.showMessageDialog(null, "ERROR: Email not found.");
                    } else if (!testPassword) {
                        JOptionPane.showMessageDialog(null, "ERROR: Wrong password.");
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
                new SubscribeView();
            }
        });

        // set component bounds (only needed by Absolute Positioning)
        idTextField.setBounds(550, 300, 200, 25);
        idLabel.setBounds(450, 300, 100, 25);
        pwdLabel.setBounds(450, 350, 100, 25);
        pwdPasswordField.setBounds(550, 350, 200, 25);
        titleLabel.setBounds(480, 150, 300, 25);
        connexionButton.setBounds(500, 400, 200, 25);
        subscribeButton.setBounds(500, 500, 200, 25);

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

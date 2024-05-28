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
        JFrame frame = new JFrame("MyPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Ville partagée");
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        // construct components
        JTextField jcomp1 = new JTextField(5);
        JLabel jcomp2 = new JLabel("Identifiant :");
        JLabel jcomp3 = new JLabel("Mot de passe :");
        JPasswordField jcomp4 = new JPasswordField(5);
        JLabel jcomp5 = new JLabel("VILLE PARTAGEE");
        JButton jcomp6 = new JButton("SE CONNECTER");
        jcomp6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Verification des champs et connexion.");
            }
        });
        JButton jcomp7 = new JButton("S'INSCRIRE");
        jcomp7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SubscribeView();
            }
        });

        // adjust size and set layout
        panel.setPreferredSize(new Dimension(1280, 720));
        panel.setLayout(null);

        // construct components
        jcomp1 = new JTextField(5);
        jcomp2 = new JLabel("Identifiant :");
        jcomp3 = new JLabel("Mot de passe :");
        jcomp4 = new JPasswordField(5);
        jcomp5 = new JLabel("VILLE PARTAGEE - PAGE DE CONNEXION");
        jcomp6 = new JButton("SE CONNECTER");
        jcomp7 = new JButton("S'INSCRIRE");

        // adjust size and set layout
        panel.setPreferredSize(new Dimension(1280, 720));
        panel.setLayout(null);

        // add components
        panel.add(jcomp1);
        panel.add(jcomp2);
        panel.add(jcomp3);
        panel.add(jcomp4);
        panel.add(jcomp5);
        panel.add(jcomp6);
        panel.add(jcomp7);

        // set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds(600, 300, 100, 25);
        jcomp2.setBounds(500, 300, 100, 25);
        jcomp3.setBounds(500, 350, 100, 25);
        jcomp4.setBounds(600, 350, 100, 25);
        jcomp5.setBounds(480, 150, 300, 25);
        jcomp6.setBounds(500, 400, 200, 25);
        jcomp7.setBounds(500, 500, 200, 25);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginView();
    }

}

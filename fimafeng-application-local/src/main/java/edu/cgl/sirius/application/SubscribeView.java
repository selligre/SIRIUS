package edu.cgl.sirius.application;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
        JLabel jcomp1 = new JLabel("INSCRIPTION");
        JLabel jcomp2 = new JLabel("Prénom :");
        JTextField jcomp3 = new JTextField(5);
        JLabel jcomp4 = new JLabel("Nom :");
        JTextField jcomp5 = new JTextField(5);
        JLabel jcomp6 = new JLabel("Pseudo :");
        JTextField jcomp7 = new JTextField(5);
        JLabel jcomp8 = new JLabel("Email :");
        JTextField jcomp9 = new JTextField(5);
        JLabel jcomp10 = new JLabel("Mot de passe :");
        JPasswordField jcomp11 = new JPasswordField(5);
        JButton jcomp12 = new JButton("S'INSCRIRE");

        // adjust size and set layout
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);

        // add components
        panel.add(jcomp1);
        panel.add(jcomp2);
        panel.add(jcomp3);
        panel.add(jcomp4);
        panel.add(jcomp5);
        panel.add(jcomp6);
        panel.add(jcomp7);
        panel.add(jcomp8);
        panel.add(jcomp9);
        panel.add(jcomp10);
        panel.add(jcomp11);
        panel.add(jcomp12);

        // set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds(350, 50, 100, 25);
        jcomp2.setBounds(300, 150, 100, 25);
        jcomp3.setBounds(400, 150, 200, 25);
        jcomp4.setBounds(300, 200, 100, 25);
        jcomp5.setBounds(400, 200, 200, 25);
        jcomp6.setBounds(300, 250, 100, 25);
        jcomp7.setBounds(400, 250, 200, 25);
        jcomp8.setBounds(300, 300, 100, 25);
        jcomp9.setBounds(400, 300, 200, 25);
        jcomp10.setBounds(300, 350, 100, 25);
        jcomp11.setBounds(400, 350, 200, 25);
        jcomp12.setBounds(300, 450, 200, 25);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new SubscribeView();
    }
}

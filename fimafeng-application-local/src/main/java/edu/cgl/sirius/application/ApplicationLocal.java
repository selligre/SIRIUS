package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.cgl.sirius.client.MainSelectClient;
import edu.cgl.sirius.client.MainInsertClient;

public class ApplicationLocal {
    private JFrame frame;
    private final int HEAD_LABEL_SIZE = 20;
    private final int VALUE_LABEL_SIZE = 14;

    public static void main(String[] args) {
        try {
            ApplicationLocal app = new ApplicationLocal();
            app.selectView();
        } catch (Exception e) {
            System.out.println("ERROR: Error starting app.HomeView().");
        }
    }

    public ApplicationLocal() {
        this.frame = new JFrame();
        this.frame.setTitle("Ville partagée");
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void homeView() {
        JPanel panel = new JPanel();

        // construct preComponents
        String[] jcomp12Items = { "Tag1", "Tag2", "Tag3" };
        String[] jcomp13Items = { "Annonce1", "Annonce2", "Annonce3" };

        // construct components
        JButton jcomp1 = new JButton("LOGO");
        JButton jcomp2 = new JButton("(+) Proposer");
        JButton jcomp3 = new JButton("Deconnexion");
        JButton jcomp4 = new JButton("Compte");
        JTextField jcomp5 = new JTextField(5);
        JButton jcomp6 = new JButton("Rechercher");
        JButton jcomp7 = new JButton("Activités");
        JButton jcomp8 = new JButton("Matériels");
        JButton jcomp9 = new JButton("Services");
        JButton jcomp10 = new JButton("Autour de moi");
        JLabel jcomp11 = new JLabel("Filtrer par :");
        JComboBox jcomp12 = new JComboBox(jcomp12Items);
        // JList jcomp13 = new JList(jcomp13Items);
        JPanel jcomp13 = new JPanel();
        JButton jcomp14 = new JButton("Filtrer");

        // adjust size and set layout
        panel.setPreferredSize(new Dimension(1270, 720));
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
        panel.add(jcomp13);
        panel.add(jcomp14);

        // enable / disable components
        jcomp1.setEnabled(false);
        jcomp2.setEnabled(true);
        jcomp3.setEnabled(false);
        jcomp4.setEnabled(false);
        jcomp5.setEnabled(false);
        jcomp6.setEnabled(false);
        jcomp7.setEnabled(true);
        jcomp8.setEnabled(false);
        jcomp9.setEnabled(false);
        jcomp10.setEnabled(true);
        jcomp11.setEnabled(true);
        jcomp12.setEnabled(true);
        jcomp13.setEnabled(true);
        jcomp14.setEnabled(true);

        // set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds(25, 25, 125, 50);
        jcomp2.setBounds(175, 25, 125, 50);
        jcomp3.setBounds(1120, 25, 125, 50);
        jcomp4.setBounds(970, 25, 125, 50);
        jcomp5.setBounds(325, 25, 495, 50);
        jcomp6.setBounds(820, 25, 125, 50);
        jcomp7.setBounds(100, 100, 250, 50);
        jcomp8.setBounds(375, 100, 250, 50);
        jcomp9.setBounds(650, 100, 250, 50);
        jcomp10.setBounds(925, 100, 250, 50);
        jcomp11.setBounds(100, 175, 125, 50);
        jcomp12.setBounds(225, 175, 125, 50);
        jcomp13.setBounds(100, 250, 1070, 445);
        jcomp14.setBounds(350, 175, 125, 50);

        this.frame.add(panel);
        this.frame.setVisible(true);
    }

    public void selectView() throws IOException, InterruptedException, SQLException {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3));

        MainSelectClient client = new MainSelectClient("SELECT_ALL_STUDENTS");
        String selectResult = client.getStudents().toString();

        System.out.println(selectResult);

        String[] studentData = selectResult.split("Student\\{");

        JLabel firstNameLabel = new JLabel("Prénom");
        firstNameLabel.setFont(new Font("Poppins", Font.BOLD, HEAD_LABEL_SIZE));
        panel.add(firstNameLabel);
        JLabel lastNameLabel = new JLabel("Nom");
        lastNameLabel.setFont(new Font("Poppins", Font.BOLD, HEAD_LABEL_SIZE));
        panel.add(lastNameLabel);
        JLabel groupLabel = new JLabel("Groupe");
        groupLabel.setFont(new Font("Poppins", Font.BOLD, HEAD_LABEL_SIZE));
        panel.add(groupLabel);

        for (String data : studentData) {
            if (data.contains("name=")) {
                String name = data.split("name='")[1].split("'")[0];
                String firstname = data.split("firstname='")[1].split("'")[0];
                String group = data.split("group='")[1].split("'")[0];

                JLabel labelName = new JLabel(name);
                labelName.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelName);

                JLabel labelFirstname = new JLabel(firstname);
                labelFirstname.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelFirstname);

                JLabel labelGroup = new JLabel(group);
                labelGroup.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelGroup);

            }
        }

        this.frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.setVisible(true);
    }

    public void selectTagView() throws IOException, InterruptedException, SQLException {
        JScrollPane scrollPane = new JScrollPane();
        this.frame.add(scrollPane);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3));
        scrollPane.add(panel);

        // MainSelectClient client = new MainSelectClient("SELECT_ALL_ACTIVITIES");
        MainSelectClient client = new MainSelectClient("SELECT_ALL_STUDENTS");
        String selectResult = client.getStudents().toString();

        System.out.println(selectResult);

        String[] studentData = selectResult.split("Student\\{");

        panel.add(new JLabel("Prénom"));
        panel.add(new JLabel("Nom"));
        panel.add(new JLabel("Groupe"));

        for (String data : studentData) {
            if (data.contains("name=")) {
                String name = data.split("name='")[1].split("'")[0];
                String firstname = data.split("firstname='")[1].split("'")[0];
                String group = data.split("group='")[1].split("'")[0];

                JLabel labelName = new JLabel(name);
                labelName.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelName);

                JLabel labelFirstname = new JLabel(firstname);
                labelFirstname.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelFirstname);

                JLabel labelGroup = new JLabel(group);
                labelGroup.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelGroup);

            }
        }

        // frame.pack();
        this.frame.add(panel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.setVisible(true);
    }

    public void insertView() throws IOException, InterruptedException, SQLException {
        MainInsertClient client = new MainInsertClient();
    }

}
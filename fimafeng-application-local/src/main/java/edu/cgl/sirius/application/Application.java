package edu.cgl.sirius.application;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cgl.sirius.client.MainSelectAnnounces;

public class Application {
    private final int LABEL_SIZE = 10;
    private final int FRAME_WIDTH = 1280;
    private final int FRAME_HEIGHT = 720;

    private JFrame frame;
    private JPanel page;

    private JButton logoButton;
    private JButton createButton;
    private JButton logOutButton;
    private JButton accountButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton activitiesButton;
    private JButton materialsButton;
    private JButton servicesButton;
    private JButton aroundMeButton;
    private JPanel pageContent;

    private String chosenLocation;

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        configFrame();
        configHomePage();
        this.frame.setVisible(true);
    }

    public void configFrame() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setTitle("Ville partagée");
        this.frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
    }

    public void configHomePage() {
        this.page = new JPanel();
        this.page.setLayout(null);
        this.page.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // add instances
        this.logoButton = new JButton();
        this.createButton = new JButton();
        this.logOutButton = new JButton();
        this.accountButton = new JButton();
        this.searchField = new JTextField();
        this.searchButton = new JButton();
        this.activitiesButton = new JButton();
        this.materialsButton = new JButton();
        this.servicesButton = new JButton();
        this.aroundMeButton = new JButton();
        this.pageContent = new JPanel();
        // add texts
        this.logoButton.setText("LOGO");
        this.createButton.setText("(+) Proposer");
        this.logOutButton.setText("Déconnexion");
        this.accountButton.setText("Compte");
        this.searchButton.setText("Rechercher");
        this.activitiesButton.setText("Activités");
        this.materialsButton.setText("Matériels");
        this.servicesButton.setText("Services");
        this.aroundMeButton.setText("Autour de moi");
        // add component functions
        this.logoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Retour à la page d'accueil.");
            }
        });
        this.createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ajout d'une nouvelle entrée.");
            }
        });
        this.logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Deconnexion de l'utilisateur.");
            }
        });
        this.accountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Accès aux détails de l'utilisateur.");
            }
        });
        this.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String content = "Recherche du terme \"" + searchField.getText() + "\" dans les annonces.";
                JOptionPane.showMessageDialog(null, content);
            }
        });
        this.activitiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Affichage des annonces d'activités.");
                selectActivities();
            }
        });
        this.materialsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Affichage des annonces de matériels.");
            }
        });
        this.servicesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Affichage des annonces de services.");
            }
        });
        this.aroundMeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Affichage des annonces autour d'un quartier.");
            }
        });
        // add border for content area
        this.pageContent.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        // set components locations
        this.logoButton.setBounds(25, 25, 125, 50);
        this.createButton.setBounds(175, 25, 125, 50);
        this.logOutButton.setBounds(1120, 25, 125, 50);
        this.accountButton.setBounds(970, 25, 125, 50);
        this.searchField.setBounds(325, 25, 495, 50);
        this.searchButton.setBounds(820, 25, 125, 50);
        this.activitiesButton.setBounds(100, 100, 250, 50);
        this.materialsButton.setBounds(375, 100, 250, 50);
        this.servicesButton.setBounds(650, 100, 250, 50);
        this.aroundMeButton.setBounds(925, 100, 250, 50);
        this.pageContent.setBounds(25, 175, 1220, 490);
        // enable or disable components
        this.logoButton.setEnabled(false);
        this.createButton.setEnabled(true);
        this.logOutButton.setEnabled(false);
        this.accountButton.setEnabled(false);
        this.searchField.setEnabled(false);
        this.searchButton.setEnabled(false);
        this.activitiesButton.setEnabled(true);
        this.materialsButton.setEnabled(false);
        this.servicesButton.setEnabled(false);
        this.aroundMeButton.setEnabled(true);
        this.pageContent.setEnabled(true);
        // add components
        this.page.add(this.logoButton);
        this.page.add(this.createButton);
        this.page.add(this.logOutButton);
        this.page.add(this.accountButton);
        this.page.add(this.searchField);
        this.page.add(this.searchButton);
        this.page.add(this.activitiesButton);
        this.page.add(this.materialsButton);
        this.page.add(this.servicesButton);
        this.page.add(this.aroundMeButton);
        this.page.add(this.pageContent);

        this.frame.add(this.page);
    }

    public void selectActivities() {
        try {
            MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_ANNOUNCES");
            String result = client.getAnnounces().toString();
            String[] data = result.split("Announce\\{");

            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.setBounds(25, 175, 1220, 490);

            panel.setLayout(new GridLayout(0, 11));
            panel.add(new JLabel("announce_id"));
            panel.add(new JLabel("ref_author_id"));
            panel.add(new JLabel("publicationDate"));
            panel.add(new JLabel("status"));
            panel.add(new JLabel("type"));
            panel.add(new JLabel("title"));
            panel.add(new JLabel("description"));
            panel.add(new JLabel("dateTimeStart"));
            panel.add(new JLabel("duration"));
            panel.add(new JLabel("dateTimeEnd"));
            panel.add(new JLabel("isRecurrent"));

            for (String d : data) {
                if (d.contains("announce_id=")) {
                    String announceId = d.split("announce_id='")[1].split("'")[0];
                    String refAuthorId = d.split("ref_author_id='")[1].split("'")[0];
                    String publicationDate = d.split("publication_date='")[1].split("'")[0];
                    String status = d.split("status='")[1].split("'")[0];
                    String type = d.split("type='")[1].split("'")[0];
                    String title = d.split("title='")[1].split("'")[0];
                    String description = d.split("description='")[1].split("'")[0];
                    String dateTimeStart = d.split("date_time_start='")[1].split("'")[0];
                    String duration = d.split("duration='")[1].split("'")[0];
                    String dateTimeEnd = d.split("date_time_end='")[1].split("'")[0];
                    String isRecurrent = d.split("is_recurrent='")[1].split("'")[0];

                    JLabel labelAnnounceId = new JLabel(announceId);
                    labelAnnounceId.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelAnnounceId);

                    JLabel labelRefAuthorId = new JLabel(refAuthorId);
                    labelRefAuthorId.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelRefAuthorId);

                    JLabel labelPublicationDate = new JLabel(publicationDate);
                    labelPublicationDate.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelPublicationDate);

                    JLabel labelStatus = new JLabel(status);
                    labelStatus.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelStatus);

                    JLabel labelType = new JLabel(type);
                    labelType.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelType);

                    JLabel labelTitle = new JLabel(title);
                    labelTitle.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelTitle);

                    JLabel labelDescription = new JLabel(description);
                    labelDescription.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelDescription);

                    JLabel labelDateTimeStart = new JLabel(dateTimeStart);
                    labelDateTimeStart.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelDateTimeStart);

                    JLabel labelDuration = new JLabel(duration);
                    labelDuration.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelDuration);

                    JLabel labelDateTimeEnd = new JLabel(dateTimeEnd);
                    labelDateTimeEnd.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelDateTimeEnd);

                    JLabel labelIsRecurrent = new JLabel(isRecurrent);
                    labelIsRecurrent.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    panel.add(labelIsRecurrent);
                }
            }

            // this.pageContent.add(panel);
            this.page.remove(this.pageContent);
            this.pageContent = panel;
            this.page.add(this.pageContent);
            this.page.validate();
            this.page.repaint();
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

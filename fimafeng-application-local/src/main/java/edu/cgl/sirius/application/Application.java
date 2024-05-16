package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.DefaultTableModel;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.client.MainSelectAnnounces;
import edu.cgl.sirius.client.MainSelectAnnouncesLocation;
import edu.cgl.sirius.client.MainSelectAnnouncesTag;
import edu.cgl.sirius.client.SelectAllAnnouncesClientRequest;

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

    public static Announces requestResult;

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

    public void changeViewToInsert() {
        InsertView.start(this.frame);
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
                // JOptionPane.showMessageDialog(null, "Ajout d'une nouvelle entrée.");
                changeViewToInsert();
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
            Application.requestResult = client.getAnnounces();

            JPanel activitiesPanel = new JPanel();
            activitiesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            activitiesPanel.setBounds(25, 175, 1220, 490);
            activitiesPanel.setLayout(new BorderLayout());

            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new FlowLayout());

            JButton filter_by_tag = new JButton("Filtrer par tag");
            filter_by_tag.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // JOptionPane.showMessageDialog(null, "Filtrage par tag.");
                    // SelectTagView selectTagView = new SelectTagView();
                    try {
                        MainSelectAnnouncesTag client = new MainSelectAnnouncesTag("SELECT_ANNOUNCES_FOR_TAG_ID", "1");
                        Application.requestResult = client.getAnnounces();
                    } catch (IOException | InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            headerPanel.add(filter_by_tag);
            JButton filter_by_location = new JButton("Filtrer par quartier");
            filter_by_location.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // JOptionPane.showMessageDialog(null, "Filtrage par quartier.");
                    try {
                        MainSelectAnnouncesLocation client = new MainSelectAnnouncesLocation(
                                "SELECT_ANNOUNCES_FOR_LOCATION",
                                "Théâtre");
                        Application.requestResult = client.getAnnouncesLocation();
                    } catch (JsonProcessingException e1) {
                        e1.printStackTrace();
                    }

                }
            });
            headerPanel.add(filter_by_location);

            activitiesPanel.add(headerPanel, BorderLayout.NORTH);

            

            JScrollPane contentPanel = new JScrollPane();
            contentPanel.setLayout(new ScrollPaneLayout());
            contentPanel.setBounds(25, 175, 1220, 490);

            activitiesPanel.add(contentPanel, BorderLayout.CENTER);

            this.page.remove(this.pageContent);
            this.pageContent = activitiesPanel;
            this.page.add(activitiesPanel);
            this.page.validate();
            this.page.repaint();
        } catch (

        Exception e) {
            e.printStackTrace();
        }

    }

}

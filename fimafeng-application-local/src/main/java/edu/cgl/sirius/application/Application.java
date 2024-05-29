package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Taskbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.naming.ldap.SortKey;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneLayout;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.client.MainSelectAnnounces;
import edu.cgl.sirius.client.MainSelectAnnouncesLocation;
import edu.cgl.sirius.client.MainSelectAnnouncesTag;
import edu.cgl.sirius.client.SelectAllAnnouncesClientRequest;
import edu.cgl.sirius.commons.AnnounceParser;

public class Application {
    private final int LABEL_SIZE = 10;
    private final int FRAME_WIDTH = 1280;
    private final int FRAME_HEIGHT = 720;

    private JFrame frame;
    public static JPanel page;

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

    public static Announces requestResult;
    public static JScrollPane scrollPane;

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
        Application.page = new JPanel();
        Application.page.setLayout(null);
        Application.page.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
        this.aroundMeButton.setEnabled(false);
        // add components
        Application.page.add(this.logoButton);
        Application.page.add(this.createButton);
        Application.page.add(this.logOutButton);
        Application.page.add(this.accountButton);
        Application.page.add(this.searchField);
        Application.page.add(this.searchButton);
        Application.page.add(this.activitiesButton);
        Application.page.add(this.materialsButton);
        Application.page.add(this.servicesButton);
        Application.page.add(this.aroundMeButton);

        this.frame.add(Application.page);
    }

    public void selectActivities() {
        this.activitiesButton.setBackground(Color.DARK_GRAY);
        this.activitiesButton.setEnabled(false);

        AnnounceParser parser = new AnnounceParser();

        try {
            MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_ANNOUNCES");
            Application.requestResult = client.getAnnounces();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel pagePanel = new JPanel();
        pagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pagePanel.setBounds(25, 175, 1220, 490);
        pagePanel.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        String tags[] = { "-", "Concert", "Festival", "Séniors", "Couple", "Tout public", "Musée", "Peinture",
                "Théatre", "Visite", "Adultes", "Chorale", "Enfants", "Jeunes" };
        String tags_id[] = { null, "1", "3", "7", "8", "9", "10", "11", "12", "13", "6", "2", "4", "5" };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox tagList1 = new JComboBox(tags);
        headerPanel.add(tagList1);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox tagList2 = new JComboBox(tags);
        headerPanel.add(tagList2);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox tagList3 = new JComboBox(tags);
        headerPanel.add(tagList3);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox tagList4 = new JComboBox(tags);
        headerPanel.add(tagList4);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox tagList5 = new JComboBox(tags);
        headerPanel.add(tagList5);

        JButton filter_by_tag = new JButton("Filtrer par tag(s)");
        filter_by_tag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedTagId1 = tags_id[tagList1.getSelectedIndex()];
                    String selectedTagId2 = tags_id[tagList2.getSelectedIndex()];
                    String selectedTagId3 = tags_id[tagList3.getSelectedIndex()];
                    String selectedTagId4 = tags_id[tagList4.getSelectedIndex()];
                    String selectedTagId5 = tags_id[tagList5.getSelectedIndex()];

                    ArrayList<String> selectedTagIds = new ArrayList<>();
                    selectedTagIds.add(selectedTagId1);
                    selectedTagIds.add(selectedTagId2);
                    selectedTagIds.add(selectedTagId3);
                    selectedTagIds.add(selectedTagId4);
                    selectedTagIds.add(selectedTagId5);

                    MainSelectAnnouncesTag client = new MainSelectAnnouncesTag("SELECT_ANNOUNCES_FOR_TAG_ID",
                            selectedTagIds);
                    Application.requestResult = client.getAnnounces();
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
                JTable table = new JTable();
                DefaultTableModel model = new DefaultTableModel(
                        new String[] { "Titre", "Date et Heure", "Durée", "Places restantes", "Prix",
                                "Quartier" },
                        0);
                table.setModel(model);
                table.setEnabled(false);
                for (Announce announce : Application.requestResult.getAnnounces()) {
                    String[] rowData = {
                            announce.getTitle(),
                            parser.parseDateTime(announce.getDate_time_start()),
                            parser.parseDuration(announce.getDuration()),
                            announce.getSlots_available().toString(),
                            parser.parsePrice(announce.getPrice()),
                            announce.getRef_location_id()
                    };
                    model.addRow(rowData);
                }
                pagePanel.remove(Application.scrollPane);
                Application.scrollPane = new JScrollPane(table);
                pagePanel.add(Application.scrollPane, BorderLayout.CENTER);

                Application.page.add(pagePanel);
                Application.page.revalidate();
                Application.page.repaint();
            }
        });
        headerPanel.add(filter_by_tag);

        String locations[] = { "-", "Plaza", "Court", "Pass", "Place", "Park", "Bar St Patricks", "Place de la Mairie",
                "Parc du chateau", "Salle de fêtes", "Piscine", "Cinéma", "Théâtre", "Mairie" };
        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox locationList = new JComboBox(locations);
        headerPanel.add(locationList);

        JButton filter_by_location = new JButton("Filtrer par quartier");
        filter_by_location.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedLocation = locationList.getSelectedItem().toString();
                    if (!selectedLocation.equals("-")) {
                        MainSelectAnnouncesLocation client = new MainSelectAnnouncesLocation(
                                "SELECT_ANNOUNCES_FOR_LOCATION",
                                selectedLocation);
                        Application.requestResult = client.getAnnouncesLocation();
                    }
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }
                JTable table = new JTable();
                DefaultTableModel model = new DefaultTableModel(
                        new String[] { "Titre", "Date et Heure", "Durée", "Places restantes", "Prix",
                                "Quartier" },
                        0);
                table.setModel(model);
                table.setEnabled(false);
                for (Announce announce : Application.requestResult.getAnnounces()) {
                    String[] rowData = {
                            announce.getTitle(),
                            parser.parseDateTime(announce.getDate_time_start()),
                            parser.parseDuration(announce.getDuration()),
                            announce.getSlots_available().toString(),
                            parser.parsePrice(announce.getPrice()),
                            announce.getRef_location_id()
                    };
                    model.addRow(rowData);
                }
                pagePanel.remove(Application.scrollPane);
                Application.scrollPane = new JScrollPane(table);
                pagePanel.add(scrollPane, BorderLayout.CENTER);

                Application.page.add(pagePanel);
                Application.page.revalidate();
                Application.page.repaint();
            }
        });
        headerPanel.add(filter_by_location);
        pagePanel.add(headerPanel, BorderLayout.NORTH);

        JButton remove_filters = new JButton("Retirer tous les filtres");
        remove_filters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_ANNOUNCES");
                    Application.requestResult = client.getAnnounces();
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
                JTable table = new JTable();
                DefaultTableModel model = new DefaultTableModel(
                        new String[] { "Titre", "Date et Heure", "Durée", "Places restantes", "Prix",
                                "Quartier" },
                        0);
                table.setModel(model);
                table.setEnabled(false);
                for (Announce announce : Application.requestResult.getAnnounces()) {
                    String[] rowData = {
                            announce.getTitle(),
                            parser.parseDateTime(announce.getDate_time_start()),
                            parser.parseDuration(announce.getDuration()),
                            announce.getSlots_available().toString(),
                            parser.parsePrice(announce.getPrice()),
                            announce.getRef_location_id()
                    };
                    model.addRow(rowData);
                }
                pagePanel.remove(Application.scrollPane);
                Application.scrollPane = new JScrollPane(table);
                pagePanel.add(scrollPane, BorderLayout.CENTER);

                Application.page.add(pagePanel);
                Application.page.revalidate();
                Application.page.repaint();
            }
        });
        headerPanel.add(remove_filters);
        pagePanel.add(headerPanel, BorderLayout.NORTH);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                new String[] { "Titre", "Date et Heure", "Durée", "Places restantes", "Prix",
                        "Quartier" },
                0);
        table.setModel(model);
        table.setEnabled(false);
        for (Announce announce : Application.requestResult.getAnnounces()) {
            String[] rowData = {
                    announce.getTitle(),
                    parser.parseDateTime(announce.getDate_time_start()),
                    parser.parseDuration(announce.getDuration()),
                    announce.getSlots_available().toString(),
                    parser.parsePrice(announce.getPrice()),
                    announce.getRef_location_id()
            };
            model.addRow(rowData);
        }
        Application.scrollPane = new JScrollPane(table);
        pagePanel.add(Application.scrollPane, BorderLayout.CENTER);

        Application.page.add(pagePanel);
        Application.page.revalidate();
        Application.page.repaint();
    }
}

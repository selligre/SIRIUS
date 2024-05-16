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
import com.fasterxml.jackson.databind.util.JSONPObject;

import edu.cgl.sirius.client.MainSelectAnnounces;
import edu.cgl.sirius.client.MainSelectAnnouncesLocation;
import edu.cgl.sirius.client.MainSelectAnnouncesTag;

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

    public static String data;

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
            String result = client.getAnnounces().toString();
            Application.data = result;

            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.setBounds(25, 175, 1220, 490);
            panel.setLayout(new BorderLayout());

            JPanel header = new JPanel();
            header.setLayout(new FlowLayout());

            JButton filter_by_tag = new JButton("Filtrer par tag");
            filter_by_tag.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // JOptionPane.showMessageDialog(null, "Filtrage par tag.");
                    // SelectTagView selectTagView = new SelectTagView();
                    try {
                        MainSelectAnnouncesTag client = new MainSelectAnnouncesTag("SELECT_ANNOUNCES_FOR_TAG_ID", "1");
                        String result = client.getAnnounces().toString();
                        Application.data = result;
                    } catch (IOException | InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            header.add(filter_by_tag);
            JButton filter_by_location = new JButton("Filtrer par quartier");
            filter_by_location.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // JOptionPane.showMessageDialog(null, "Filtrage par quartier.");
                    try {
                        MainSelectAnnouncesLocation client = new MainSelectAnnouncesLocation(
                                "SELECT_ANNOUNCES_FOR_LOCATION",
                                "Théâtre");
                        String result = client.getAnnouncesLocation().toString();
                        Application.data = result;
                    } catch (JsonProcessingException e1) {
                        e1.printStackTrace();
                    }

                }
            });
            header.add(filter_by_location);

            panel.add(header, BorderLayout.NORTH);

            JScrollPane request_result_pane = new JScrollPane();
            request_result_pane.setLayout(new ScrollPaneLayout());
            request_result_pane.setBounds(25, 175, 1220, 490);

            String requestResult = Application.data;
            // String[] columnNames = { "Titre", "Date", "Places restantes", "Prix",
            // "Quartier" };
            // requestResult = requestResult.substring("Announces{announces=[".length());
            // String[] requestResultSplited = requestResult.split("Announce");

            // for (String s : requestResultSplited) {
            // System.out.println(s);
            // }

            // Remove the unwanted starting part
            String jsonData = requestResult.substring("Announces{announces=[".length());

            // Parse the JSON data (assuming it's valid JSON)
            String[][] announceData = parseAnnounceData(jsonData);

            // Create a table model with column names
            String[] columnNames = { "announce_id", "title", "description", "date_time_start", "duration",
                    "date_time_end" };
            DefaultTableModel model = new DefaultTableModel(announceData, columnNames);

            // Create a JTable and set the model
            JTable table = new JTable(model);

            // Wrap the table in a JScrollPane for scrolling
            JScrollPane scrollPane = new JScrollPane(table);

            // Create a JFrame and add the scroll pane
            JFrame frame = new JFrame("Announcements");
            frame.setSize(800, 400); // Adjust size as needed
            frame.add(scrollPane);
            frame.setVisible(true);

            panel.add(request_result_pane, BorderLayout.CENTER);

            this.page.remove(this.pageContent);
            this.pageContent = panel;
            this.page.add(panel);
            this.page.validate();
            this.page.repaint();
        } catch (

        Exception e) {
            e.printStackTrace();
        }

    }

    // This method parses the JSON data and extracts relevant announcement
    // information
    private static String[][] parseAnnounceData(String jsonData) {
        // You can use a JSON parsing library like Gson or org.json to parse the data
        // Here's an example using org.json (replace with your preferred library)
        System.out.println("jsonDatajsonDatajsonDatajsonDatajsonData");
        System.out.println("jsonDatajsonDatajsonDatajsonDatajsonData");
        System.out.println("jsonDatajsonDatajsonDatajsonDatajsonData");
        System.out.println("jsonDatajsonDatajsonDatajsonDatajsonData");
        System.out.println("jsonDatajsonDatajsonDatajsonDatajsonData");
        System.out.println(jsonData);
        JSONArray announces = new JSONArray("[" + jsonData + "]");
        String[][] data = new String[announces.length()][]; // Assuming 6 columns (adjust as needed)

        for (int i = 0; i < announces.length(); i++) {
            JSONObject announce = announces.getJSONObject(i);
            data[i] = new String[] {
                    announce.getString("title"),
                    announce.getString("date_time_start"),
                    announce.getString("slots_available"),
                    announce.getString("price"),
                    announce.getString("ref_location_id")
            };
        }

        return data;
    }

}

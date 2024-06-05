package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.cgl.sirius.business.AnnounceParser;
import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.Announces;
import edu.cgl.sirius.business.dto.NumberCount;
import edu.cgl.sirius.business.dto.User;
import edu.cgl.sirius.client.MainSelectAnnounces;
import edu.cgl.sirius.client.MainSelectAnnouncesLocation;
import edu.cgl.sirius.client.MainSelectAnnouncesTag;
import edu.cgl.sirius.client.MainSelectAnnouncesTagLocation;
import edu.cgl.sirius.client.MainSelectLocations;
import edu.cgl.sirius.client.MainSelectNumberCount;
import edu.cgl.sirius.client.MainSelectTags;
import edu.cgl.sirius.client.commons.UtilsManager;

public class Application {
    protected static User connectedUser;

    public static User getConnectedUser() {
        return connectedUser;
    }

    public static void setConnectedUser(User connectedUser) {
        Application.connectedUser = connectedUser;
    }

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
    private JPanel pagePanel;

    private static Logger logger;

    private static AnnounceParser parser;

    int online;
    int offline;

    MainSelectNumberCount count;

    String status[] = { "Statut", "En ligne : " + online, "Hors ligne : " + offline};
    final JComboBox<String> statusCombox = new JComboBox<>(status);

    public static void main(String[] args) {
        new Application();
    }

    public Application() {
        Application.logger = LoggerFactory.getLogger("A p p l i c a t i o n - L o c a l");
        configFrame();
        configHomePage();
        this.frame.setVisible(true);
    }

    public void configFrame() {
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setTitle("Ville partagée - Home");
        this.frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
    }

    public void changeViewToInsert() {
        InsertView.start(this.frame);
    }

    public void changeViewToFocus(Announce ann) {
        FocusView.start(this.frame, ann);
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
                logger.info("Logo Button clicked");
                // JOptionPane.showMessageDialog(null, "Retour à la page d'accueil.");
                selectSuggestions();
            }
        });
        this.createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Create Button clicked");
                // JOptionPane.showMessageDialog(null, "Ajout d'une nouvelle entrée.");
                changeViewToInsert();
            }
        });
        this.logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("LogOut Button clicked");
                // JOptionPane.showMessageDialog(null, "Deconnexion de l'utilisateur.");
                frame.setVisible(false);
                frame.setEnabled(false);
                frame.repaint();
                new LoginView();
            }
        });
        this.accountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Account Button clicked");
                // JOptionPane.showMessageDialog(null, "Accès aux détails de l'utilisateur : " +
                // connectedUser.getEmail());
                new UserUpdateView();
            }
        });
        this.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Search Button clicked");
                try {
                    MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_MATCHING_ANNOUNCES",
                            searchField.getText());
                    requestResult = client.getAnnounces();
                    displayResult(pagePanel, requestResult);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.activitiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Activites Button clicked");
                // JOptionPane.showMessageDialog(null, "Affichage des annonces d'activités.");
                selectActivities();
            }
        });
        this.materialsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Materials Button clicked");
                JOptionPane.showMessageDialog(null, "Affichage des annonces de matériels.");
            }
        });
        this.servicesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Services Button clicked");
                JOptionPane.showMessageDialog(null, "Affichage des annonces de services.");
            }
        });
        this.aroundMeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("ArroundMe Button clicked");
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
        this.logoButton.setEnabled(true);
        this.createButton.setEnabled(true);
        this.logOutButton.setEnabled(true);
        this.accountButton.setEnabled(true);
        this.searchField.setEnabled(true);
        this.searchButton.setEnabled(true);
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

        pagePanel = new JPanel();
        pagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pagePanel.setBounds(25, 175, 1220, 490);
        pagePanel.setLayout(new BorderLayout());

        parser = new AnnounceParser();

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        // Update tags from DB for filters
        HashMap<String, String> map_tagsItems = new HashMap<>();
        map_tagsItems.put("-", null);
        Map<String, String> tagsMap;
        try {
            logger.info("Start querry (tags for filters)");
            MainSelectTags tagsClient = new MainSelectTags("SELECT_ALL_TAGS");
            tagsMap = tagsClient.getTags().getTagsMap();
            for (String key : tagsMap.keySet()) {
                map_tagsItems.put(tagsMap.get(key), key);
            }
            logger.info("Queery ended!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        headerPanel.add(statusCombox);

        String[] tags = (String[]) map_tagsItems.keySet().toArray(new String[0]);
        UtilsManager.reorderWithDefaultOnTop(tags, "-");

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
                    String selectedTagId1 = map_tagsItems.get(tagList1.getSelectedItem());
                    String selectedTagId2 = map_tagsItems.get(tagList2.getSelectedItem());
                    String selectedTagId3 = map_tagsItems.get(tagList3.getSelectedItem());
                    String selectedTagId4 = map_tagsItems.get(tagList4.getSelectedItem());
                    String selectedTagId5 = map_tagsItems.get(tagList5.getSelectedItem());

                    ArrayList<String> selectedTagIds = new ArrayList<>();
                    selectedTagIds.add(selectedTagId1);
                    selectedTagIds.add(selectedTagId2);
                    selectedTagIds.add(selectedTagId3);
                    selectedTagIds.add(selectedTagId4);
                    selectedTagIds.add(selectedTagId5);

                    MainSelectAnnouncesTag client = new MainSelectAnnouncesTag("SELECT_ANNOUNCES_FOR_TAG_ID",
                            selectedTagIds);
                    requestResult = client.getAnnounces();
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
                displayResult(pagePanel, requestResult);
            }
        });
        headerPanel.add(filter_by_tag);

        try {
            logger.info("Launch querry (locations for filters)");
            MainSelectLocations locationClient = new MainSelectLocations("SELECT_ALL_LOCATIONS");
            parser.updateLocations(locationClient.getLocations());
            String nbUsers = "";
            String nbLocations = "";
            String nbTags = "";
            
            count = new MainSelectNumberCount("SELECT_NB_USERS");
            for (final NumberCount numberCount : count.getNumberCounts().getNumberCounts()){
                nbUsers = numberCount.getCount();
            }

            count = new MainSelectNumberCount("SELECT_NB_LOCATIONS");
            for (final NumberCount numberCount : count.getNumberCounts().getNumberCounts()){
                nbLocations = numberCount.getCount();
            }

            count = new MainSelectNumberCount("SELECT_NB_TAGS");
            for (final NumberCount numberCount : count.getNumberCounts().getNumberCounts()){
                nbTags = numberCount.getCount();
            }

            String message = "Nombre d'utilisateurs : " + nbUsers + "\n" +
                            "Nombre de localisations : "+ nbLocations + "\n" + 
                            "Nombre de tags : " + nbTags;
            JOptionPane.showMessageDialog(null, message, "Informations", JOptionPane.INFORMATION_MESSAGE);

            logger.info("Query ended!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, String> map_locationsItems = new HashMap<>();
        map_locationsItems.put("-", "0");
        Map<String, String> parsedLocations = parser.getParsedLocations();
        for (String key : parsedLocations.keySet()) {
            map_locationsItems.put(parsedLocations.get(key), key);
        }

        String[] locationsItems = (String[]) map_locationsItems.keySet().toArray(new String[0]);
        UtilsManager.reorderWithDefaultOnTop(locationsItems, "-");

        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox locationList = new JComboBox(locationsItems);
        headerPanel.add(locationList);

        JButton filter_by_location = new JButton("Filtrer par lieu");
        filter_by_location.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("Filtering by neighboorhoods");
                try {
                    String selectedLocation = map_locationsItems.get(locationList.getSelectedItem());
                    if (!selectedLocation.equals("0")) {
                        MainSelectAnnouncesLocation client = new MainSelectAnnouncesLocation(
                                "SELECT_ANNOUNCES_FOR_LOCATION",
                                selectedLocation);
                        requestResult = client.getAnnouncesLocation();
                    }
                } catch (JsonProcessingException e1) {
                    e1.printStackTrace();
                }
                displayResult(pagePanel, requestResult);
            }
        });
        headerPanel.add(filter_by_location);

        JButton cross_filter = new JButton("Filtrer par tag(s) et lieu");
        cross_filter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedTagId1 = map_tagsItems.get(tagList1.getSelectedItem());
                    String selectedTagId2 = map_tagsItems.get(tagList2.getSelectedItem());
                    String selectedTagId3 = map_tagsItems.get(tagList3.getSelectedItem());
                    String selectedTagId4 = map_tagsItems.get(tagList4.getSelectedItem());
                    String selectedTagId5 = map_tagsItems.get(tagList5.getSelectedItem());

                    ArrayList<String> selectedTagIds = new ArrayList<>();
                    selectedTagIds.add(selectedTagId1);
                    selectedTagIds.add(selectedTagId2);
                    selectedTagIds.add(selectedTagId3);
                    selectedTagIds.add(selectedTagId4);
                    selectedTagIds.add(selectedTagId5);

                    String selectedLocation = map_locationsItems.get(locationList.getSelectedItem());
                    logger.debug(selectedLocation);
                    if (selectedLocation.equals("0")) {
                        filter_by_location.doClick();
                    } else {
                        MainSelectAnnouncesTagLocation client = new MainSelectAnnouncesTagLocation(
                                "SELECT_ANNOUNCES_FOR_TAG_AND_LOCATION",
                                selectedTagIds, selectedLocation);
                        requestResult = client.getAnnounces();
                        displayResult(pagePanel, requestResult);
                    }
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        headerPanel.add(cross_filter);

        JButton remove_filters = new JButton("RESET");
        remove_filters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_ANNOUNCES");
                    requestResult = client.getAnnounces();
                } catch (IOException | InterruptedException e1) {
                    e1.printStackTrace();
                }
                tagList1.setSelectedIndex(0);
                tagList2.setSelectedIndex(0);
                tagList3.setSelectedIndex(0);
                tagList4.setSelectedIndex(0);
                tagList5.setSelectedIndex(0);
                locationList.setSelectedIndex(0);

                displayResult(pagePanel, requestResult);
            }
        });
        headerPanel.add(remove_filters);
        pagePanel.add(headerPanel, BorderLayout.NORTH);
        pagePanel.setVisible(false);
    }

    public void selectActivities() {
        // this.activitiesButton.setBackground(Color.LIGHT_GRAY);
        // this.activitiesButton.setEnabled(false);

        parser = new AnnounceParser();

        try {
            logger.info("Launch querry (all anounces)");
            MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_ANNOUNCES");
            Application.requestResult = client.getAnnounces();
            logger.info("Querry ended!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        displayResult(pagePanel, requestResult);
    }

    public void selectSuggestions() {
        // this.activitiesButton.setBackground(Color.LIGHT_GRAY);
        // this.activitiesButton.setEnabled(false);

        parser = new AnnounceParser();

        try {
            logger.info("Launch querry (all anounces)");
            // MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_ANNOUNCES");
            // Application.requestResult = client.getAnnounces();
            ArrayList<String> ref_tag_id = new ArrayList<>();
            ref_tag_id.add(Integer.toString(Application.connectedUser.getTag()));
            ref_tag_id.add(null);
            ref_tag_id.add(null);
            ref_tag_id.add(null);
            ref_tag_id.add(null);
            MainSelectAnnouncesTag mainSelectAnnouncesTag = new MainSelectAnnouncesTag("SELECT_ANNOUNCES_FOR_TAG_ID",
                    ref_tag_id);
            Announces announcesWithUserLocationAndUserTag = new Announces();
            for (Announce announce : mainSelectAnnouncesTag.getAnnounces().getAnnounces()) {
                if (announce.getRef_location_id().equals(Integer.toString(Application.connectedUser.getLocation())))
                    announcesWithUserLocationAndUserTag.add(announce);
            }
            requestResult = announcesWithUserLocationAndUserTag;
            logger.info("Querry ended!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        displayResult(pagePanel, requestResult);
    }

    private void displayResult(JPanel pagePanel, Announces resultAnnounces) {

        pagePanel.setVisible(true);

        if (parser == null) {
            parser = new AnnounceParser();
        }

        try {
            logger.info("Launch querry (display location)");
            MainSelectLocations locationClient = new MainSelectLocations("SELECT_ALL_LOCATIONS");
            parser.updateLocations(locationClient.getLocations());
            logger.info("Query ended!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                new String[] { "Titre", "Date et Heure", "Durée", "Places restantes", "Prix",
                        "Quartier", "Actions" },
                0);

        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        table.setModel(model);
        table.setEnabled(false);
        online = 0;
        offline = 0;
        for (Announce announce : resultAnnounces.getAnnounces()) {
            switch (announce.getStatus()) {
                case "online":
                    online += 1;
                    break;
                case "offline":
                    offline += 1;
                    break;
            }
            JButton btn = new JButton("Voir");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    logger.info("Opening announce view of: " + announce.toString());
                    changeViewToFocus(announce);
                }
            });
            Object[] rowData = {
                    announce.getTitle(),
                    parser.parseDateTime(announce.getDate_time_start()),
                    parser.parseDuration(announce.getDuration()),
                    announce.getSlots_available().toString(),
                    parser.parsePrice(announce.getPrice()),
                    parser.parseLocation(announce.getRef_location_id()),
                    btn,
            };
            model.addRow(rowData);
        }

        statusCombox.removeAllItems();
        statusCombox.addItem("Statut");
        statusCombox.addItem("En ligne : " + online);
        statusCombox.addItem("Hors ligne : " + offline);

        table.getColumnModel().getColumn(0).setPreferredWidth(540);
        table.getColumnModel().getColumn(1).setPreferredWidth(145);
        table.getColumnModel().getColumn(2).setPreferredWidth(65);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(180);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);

        TableCellRenderer btnRenderer = new JTableButtonRenderer();
        table.getColumnModel().getColumn(6).setCellRenderer(btnRenderer);

        table.addMouseListener(new JTableButtonMouseListener(table));

        try {
            pagePanel.remove(Application.scrollPane);
        } catch (Exception e) {
            // Do nothing
        }
        Application.scrollPane = new JScrollPane(table);
        pagePanel.add(Application.scrollPane, BorderLayout.CENTER);

        Application.page.add(pagePanel);
        Application.page.revalidate();
        Application.page.repaint();

    }

}

class JTableButtonMouseListener extends MouseAdapter {
    private final JTable table;

    public JTableButtonMouseListener(JTable table) {
        this.table = table;
    }

    public void mouseClicked(MouseEvent e) {
        int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
        int row = e.getY() / table.getRowHeight(); // get the row of the button

        // *Checking the row or column is valid or not
        if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
            Object value = table.getValueAt(row, column);
            if (value instanceof JButton) {
                // perform a click event
                ((JButton) value).doClick();
            }
        }
    }
}

class JTableButtonRenderer implements TableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
        JButton button = (JButton) value;
        return button;
    }
}

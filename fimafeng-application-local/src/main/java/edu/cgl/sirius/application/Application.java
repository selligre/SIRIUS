package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;

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
            panel.setLayout(new BorderLayout());

            JPanel header = new JPanel();
            header.setLayout(new GridLayout(0, 15));

            header.add(new JLabel("announce_id"));
            header.add(new JLabel("ref_author_id"));
            header.add(new JLabel("publication_date"));
            header.add(new JLabel("status"));
            header.add(new JLabel("type"));
            header.add(new JLabel("title"));
            header.add(new JLabel("description"));
            header.add(new JLabel("date_time_start"));
            header.add(new JLabel("duration"));
            header.add(new JLabel("date_time_end"));
            header.add(new JLabel("is_recurrent"));
            header.add(new JLabel("slots_number"));
            header.add(new JLabel("slots_available"));
            header.add(new JLabel("price"));
            header.add(new JLabel("ref_location_id"));
            JButton filter_by_tag = new JButton("Filter par Tag 1");
            filter_by_tag.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Filtrage par tag.");
                    try {
                        MainSelectAnnouncesTag client = new MainSelectAnnouncesTag("SELECT_ANNOUNCES_FOR_TAG_ID", "1");
                        String result = client.getAnnounces().toString();
                        String[] data = result.split("Announce\\{");
                        System.out.println("TAG TAG TAG TAG TAG TAG TAG TAG TAG TAG");
                        System.out.println(data);
                        System.out.println("TAG TAG TAG TAG TAG TAG TAG TAG TAG TAG");
                    } catch (IOException | InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            header.add(filter_by_tag);
            JButton filter_by_location = new JButton("Filter par Quartier 1");
            filter_by_location.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Filtrage par quartier.");
                    try {
                        MainSelectAnnouncesLocation client = new MainSelectAnnouncesLocation("SELECT_ALL_ANNOUNCES",
                                "Théâtre");
                        String result = client.getAnnounces().toString();
                        String[] data = result.split("Announce\\{");
                        System.out.println(
                                "LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION");
                        System.out.println(data);
                        System.out.println(
                                "LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION LOCATION");
                    } catch (IOException | InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            });
            header.add(filter_by_location);

            panel.add(header, BorderLayout.NORTH);

            // JScrollPane request_result_pane = new JScrollPane();
            // request_result_pane.setLayout(new ScrollPaneLayout());

            JPanel request_result = new JPanel();
            request_result.setLayout(new GridLayout(0, 15));

            for (String d : data) {
                if (d.contains("announce_id=")) {
                    String announce_id = d.split("announce_id='")[1].split("'")[0];
                    String ref_author_id = d.split("ref_author_id='")[1].split("'")[0];
                    String publication_date = d.split("publication_date='")[1].split("'")[0];
                    String status = d.split("status='")[1].split("'")[0];
                    String type = d.split("type='")[1].split("'")[0];
                    String title = d.split("title='")[1].split("'")[0];
                    String description = d.split("description='")[1].split("'")[0];
                    String date_time_start = d.split("date_time_start='")[1].split("'")[0];
                    String duration = d.split("duration='")[1].split("'")[0];
                    String date_time_end = d.split("date_time_end='")[1].split("'")[0];
                    String is_recurrent = d.split("is_recurrent='")[1].split("'")[0];
                    String slots_number = d.split("slots_number='")[1].split("'")[0];
                    String slots_available = d.split("slots_available='")[1].split("'")[0];
                    String price = d.split("price='")[1].split("'")[0];
                    String ref_location_id = d.split("ref_location_id='")[1].split("'")[0];

                    JLabel label_announce_id = new JLabel(announce_id);
                    label_announce_id.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_announce_id);

                    JLabel label_ref_author_id = new JLabel(ref_author_id);
                    label_ref_author_id.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_ref_author_id);

                    JLabel label_publication_date = new JLabel(publication_date);
                    label_publication_date.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_publication_date);

                    JLabel label_status = new JLabel(status);
                    label_status.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_status);

                    JLabel label_type = new JLabel(type);
                    label_type.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_type);

                    JLabel label_title = new JLabel(title);
                    label_title.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_title);

                    JLabel label_description = new JLabel(description);
                    label_description.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_description);

                    JLabel label_date_time_start = new JLabel(date_time_start);
                    label_date_time_start.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_date_time_start);

                    JLabel label_duration = new JLabel(duration);
                    label_duration.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_duration);

                    JLabel label_date_time_end = new JLabel(date_time_end);
                    label_date_time_end.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_date_time_end);

                    JLabel label_is_recurrent = new JLabel(is_recurrent);
                    label_is_recurrent.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_is_recurrent);

                    JLabel label_slots_number = new JLabel(slots_number);
                    label_slots_number.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_slots_number);

                    JLabel label_slots_available = new JLabel(slots_available);
                    label_slots_available.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_slots_available);

                    JLabel label_price = new JLabel(price);
                    label_price.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_price);

                    JLabel label_ref_location_id = new JLabel(ref_location_id);
                    label_ref_location_id.setFont(new Font("Arial", Font.BOLD, LABEL_SIZE));
                    request_result.add(label_ref_location_id);
                }
            }

            // request_result_pane.add(request_result);
            panel.add(request_result, BorderLayout.CENTER);

            this.page.remove(this.pageContent);
            this.pageContent = panel;
            this.page.add(this.pageContent);
            this.page.validate();
            this.page.repaint();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import edu.cgl.sirius.client.MainSelectUsers;
import edu.cgl.sirius.client.MainSelectAnnounces;
import edu.cgl.sirius.client.MainSelectAnnouncesLocation;

public class ApplicationOld {
    // private static DefaultTableModel defaultTableModel;
    private static String choosedLocation = "piscine";
    // private final int HEAD_LABEL_SIZE = 10;
    private final int VALUE_LABEL_SIZE = 8;

    private JFrame frame;
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
    JPanel jcomp11 = new JPanel();

    public static void main(String[] args) {
        try {
            ApplicationOld app = new ApplicationOld();
            // new ApplicationLocal();
            // app.homeView();
            app.defaultView();
        } catch (Exception e) {
            System.out.println("ERROR: Error starting app.homeView().");
        }
    }

    public ApplicationOld() {
        this.frame = new JFrame();
        this.frame.setTitle("Ville partagée");
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this.homeView();
        // this.defaultView();
    }

    public void defaultView() {
        JButton selectUsersButton = new JButton("Selection Des Utilisateurs");
        selectUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Popup de sélection");
                try {
                    selectUsers();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        JButton selectAnnouncesButton = new JButton("Selection Des Annonces");
        selectAnnouncesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Popup de sélection");
                try {
                    selectAnnounces();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        JButton SelectPerLocationButton = new JButton("Annonces Par Lieu");
        SelectPerLocationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Popup d'insertion");
                try {
                    SelectPerLocationView();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        });

        String location[] = { "Piscine", "Cinéma" };
        JComboBox<String> locationChoice = new JComboBox<>(location);
        locationChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getItem().equals("Piscine")) {
                    choosedLocation = "piscine";
                }
                if (e.getItem().equals("Cinéma")) {
                    choosedLocation = "cinema";
                }
            }
        });

        JPanel panel = new JPanel();
        this.frame.setLayout(new BorderLayout());

        panel.add(selectUsersButton);
        panel.add(selectAnnouncesButton);
        panel.add(SelectPerLocationButton);
        panel.add(locationChoice);

        this.frame.add(panel, BorderLayout.NORTH);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setVisible(true);
    }

    public void selectUsers() throws IOException, InterruptedException, SQLException {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 6));

        MainSelectUsers client = new MainSelectUsers("SELECT_ALL_USERS");
        String selectResult = client.getUsers().toString();

        System.out.println(selectResult);

        String[] userData = selectResult.split("User\\{");

        panel.add(new JLabel("Prénom"));
        panel.add(new JLabel("Nom"));
        panel.add(new JLabel("Pseudo"));
        panel.add(new JLabel("Type"));
        panel.add(new JLabel("Email"));
        panel.add(new JLabel("Mot de passe"));

        for (String data : userData) {
            if (data.contains("first_name=")) {
                String firstName = data.split("first_name='")[1].split("'")[0];
                String lastName = data.split("last_name='")[1].split("'")[0];
                String displayName = data.split("display_name='")[1].split("'")[0];
                String userType = data.split("user_type='")[1].split("'")[0];
                String email = data.split("email='")[1].split("'")[0];
                String password = data.split("password='")[1].split("'")[0];

                JLabel labelFirstName = new JLabel(firstName);
                labelFirstName.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelFirstName);

                JLabel labelLastName = new JLabel(lastName);
                labelLastName.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelLastName);

                JLabel labelDisplayName = new JLabel(displayName);
                labelDisplayName.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelDisplayName);

                JLabel labelUserType = new JLabel(userType);
                labelUserType.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelUserType);

                JLabel labelEmail = new JLabel(email);
                labelEmail.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelEmail);

                JLabel labelPassword = new JLabel(password);
                labelPassword.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelPassword);

            }
        }

        frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void selectAnnounces() throws IOException, InterruptedException, SQLException {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 11));

        MainSelectAnnounces client = new MainSelectAnnounces("SELECT_ALL_ANNOUNCES");
        String selectResult = client.getAnnounces().toString();

        System.out.println(selectResult);

        String[] announceData = selectResult.split("Announce\\{");

        panel.add(new JLabel("announceId"));
        panel.add(new JLabel("refAuthorId"));
        panel.add(new JLabel("publicationDate"));
        panel.add(new JLabel("status"));
        panel.add(new JLabel("type"));
        panel.add(new JLabel("title"));
        panel.add(new JLabel("description"));
        panel.add(new JLabel("dateTimeStart"));
        panel.add(new JLabel("duration"));
        panel.add(new JLabel("dateTimeEnd"));
        panel.add(new JLabel("isRecurrent"));

        for (String data : announceData) {
            if (data.contains("announce_id=")) {
                String announceId = data.split("announce_id='")[1].split("'")[0];
                String refAuthorId = data.split("ref_author_id='")[1].split("'")[0];
                String publicationDate = data.split("publication_date='")[1].split("'")[0];
                String status = data.split("status='")[1].split("'")[0];
                String type = data.split("type='")[1].split("'")[0];
                String title = data.split("title='")[1].split("'")[0];
                String description = data.split("description='")[1].split("'")[0];
                String dateTimeStart = data.split("date_time_start='")[1].split("'")[0];
                String duration = data.split("duration='")[1].split("'")[0];
                String dateTimeEnd = data.split("date_time_end='")[1].split("'")[0];
                String isRecurrent = data.split("is_recurrent='")[1].split("'")[0];

                JLabel labelAnnounceId = new JLabel(announceId);
                labelAnnounceId.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelAnnounceId);

                JLabel labelRefAuthorId = new JLabel(refAuthorId);
                labelRefAuthorId.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelRefAuthorId);

                JLabel labelPublicationDate = new JLabel(publicationDate);
                labelPublicationDate.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelPublicationDate);

                JLabel labelStatus = new JLabel(status);
                labelStatus.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelStatus);

                JLabel labelType = new JLabel(type);
                labelType.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelType);

                JLabel labelTitle = new JLabel(title);
                labelTitle.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelTitle);

                JLabel labelDescription = new JLabel(description);
                labelDescription.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelDescription);

                JLabel labelDateTimeStart = new JLabel(dateTimeStart);
                labelDateTimeStart.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelDateTimeStart);

                JLabel labelDuration = new JLabel(duration);
                labelDuration.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelDuration);

                JLabel labelDateTimeEnd = new JLabel(dateTimeEnd);
                labelDateTimeEnd.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelDateTimeEnd);

                JLabel labelIsRecurrent = new JLabel(isRecurrent);
                labelIsRecurrent.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelIsRecurrent);

            }
        }

        frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void SelectPerLocationView() throws IOException, InterruptedException, SQLException {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4));

        MainSelectAnnouncesLocation client = new MainSelectAnnouncesLocation("SELECT_ANNOUNCES_FOR_LOCATION",
                choosedLocation);
        String selectResult = client.getAnnouncesLocation().toString();

        System.out.println(selectResult);

        String[] announceLocations = selectResult.split("AnnounceLocation\\{");

        panel.add(new JLabel("announceId"));
        panel.add(new JLabel("title"));
        panel.add(new JLabel("locationId"));
        panel.add(new JLabel("name"));

        for (String data : announceLocations) {
            if (data.contains("announce_id=")) {
                String announceId = data.split("announce_id='")[1].split("'")[0];
                String title = data.split("title='")[1].split("'")[0];
                String locationId = data.split("location_id='")[1].split("'")[0];
                String name = data.split("name='")[1].split("'")[0];

                JLabel labelAnnounceId = new JLabel(announceId);
                labelAnnounceId.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelAnnounceId);

                JLabel labelTitle = new JLabel(title);
                labelTitle.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelTitle);

                JLabel labelLocationId = new JLabel(locationId);
                labelLocationId.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelLocationId);

                JLabel labelName = new JLabel(name);
                labelName.setFont(new Font("Arial", Font.BOLD, VALUE_LABEL_SIZE));
                panel.add(labelName);
            }
        }

        frame.add(new JScrollPane(panel), BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public void homeView() {
        JPanel panel = new JPanel();

        // add component functions
        this.jcomp1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Retour à la page d'accueil.");
            }
        });
        this.jcomp2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Ajout d'une nouvelle entrée.");
            }
        });
        this.jcomp3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Deconnexion de l'utilisateur.");
            }
        });
        this.jcomp4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Accès aux détails de l'utilisateur.");
            }
        });
        this.jcomp6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String content = "Recherche du terme \"" + jcomp5.getText() + "\" dans les annonces.";
                JOptionPane.showMessageDialog(null, content);
            }
        });
        this.jcomp7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Affichage des annonces d'activités.");
                try {
                    selectAnnounces();
                    frame.repaint();
                } catch (IOException | InterruptedException | SQLException e1) {
                    System.out.println("ERROR: jcomp7.actionPerformed(), selectUsers(jcomp11);");
                }
            }
        });
        this.jcomp8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Affichage des annonces de matériels.");
            }
        });
        this.jcomp9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Affichage des annonces de services.");
            }
        });
        this.jcomp10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Affichage des annonces autour d'un quartier.");
            }
        });
        this.jcomp11.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // adjust size and set layout
        // panel.setPreferredSize(new Dimension(1270, 720));
        // panel.setLayout(null);

        // add components
        panel.add(this.jcomp1);
        panel.add(this.jcomp2);
        panel.add(this.jcomp3);
        panel.add(this.jcomp4);
        panel.add(this.jcomp5);
        panel.add(this.jcomp6);
        panel.add(this.jcomp7);
        panel.add(this.jcomp8);
        panel.add(this.jcomp9);
        panel.add(this.jcomp10);
        panel.add(this.jcomp11);

        // enable / disable components
        this.jcomp1.setEnabled(true);
        this.jcomp2.setEnabled(true);
        this.jcomp3.setEnabled(true);
        this.jcomp4.setEnabled(true);
        this.jcomp5.setEnabled(true);
        this.jcomp6.setEnabled(true);
        this.jcomp7.setEnabled(true);
        this.jcomp8.setEnabled(true);
        this.jcomp9.setEnabled(true);
        this.jcomp10.setEnabled(true);
        this.jcomp11.setEnabled(true);

        // set component bounds (only needed by Absolute Positioning)
        this.jcomp1.setBounds(25, 25, 125, 50);
        this.jcomp2.setBounds(175, 25, 125, 50);
        this.jcomp3.setBounds(1120, 25, 125, 50);
        this.jcomp4.setBounds(970, 25, 125, 50);
        this.jcomp5.setBounds(325, 25, 495, 50);
        this.jcomp6.setBounds(820, 25, 125, 50);
        this.jcomp7.setBounds(100, 100, 250, 50);
        this.jcomp8.setBounds(375, 100, 250, 50);
        this.jcomp9.setBounds(650, 100, 250, 50);
        this.jcomp10.setBounds(925, 100, 250, 50);
        this.jcomp11.setBounds(25, 175, 1220, 490);

        this.frame.add(panel);
        this.frame.setVisible(true);
    }

}
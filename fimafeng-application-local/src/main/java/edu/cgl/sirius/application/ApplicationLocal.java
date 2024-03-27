package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.cgl.sirius.client.MainSelectUsers;
import edu.cgl.sirius.business.dto.Announce;
import edu.cgl.sirius.business.dto.AnnounceLocation;
import edu.cgl.sirius.client.MainInsertClient;
import edu.cgl.sirius.client.MainSelectAnnounces;
import edu.cgl.sirius.client.MainSelectAnnouncesLocation;

public class ApplicationLocal {
    private JFrame frame;
    public static String choosedLocation;
    private final int HEAD_LABEL_SIZE = 20;
    private final int VALUE_LABEL_SIZE = 14;
    JPanel panel;

    public static void main(String[] args) {
        try {
            ApplicationLocal app = new ApplicationLocal();
            app.defaultView();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public ApplicationLocal() {
        this.frame = new JFrame();
        this.frame.setTitle("Ville partagée");
        this.frame.setSize(1280, 720);
        this.frame.setLocationRelativeTo(null);
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

        String location[] = {"Piscine", "Cinéma"};
        JComboBox<String> locationChoice = new JComboBox<>(location);
        locationChoice.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){ 
                if (e.getItem().equals("Piscine")){
                    choosedLocation = "piscine";
                }
                if (e.getItem().equals("Cinéma")){
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

        panel = new JPanel();
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

        panel = new JPanel();
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

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 4));

        MainSelectAnnouncesLocation client = new MainSelectAnnouncesLocation("SELECT_ANNOUNCES_FOR_LOCATION");
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

}
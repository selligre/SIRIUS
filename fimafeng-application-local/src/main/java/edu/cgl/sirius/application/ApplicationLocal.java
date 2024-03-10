package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.cgl.sirius.client.MainSelectClient;
import edu.cgl.sirius.client.MainInsertClient;

public class ApplicationLocal {
    private JFrame frame;
    private final int HEAD_LABEL_SIZE = 20;
    private final int VALUE_LABEL_SIZE = 14;

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
        JButton selectViewButton = new JButton("Selection");
        selectViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Popup de sélection");
                try {
                    selectView();
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

        JButton selectTagViewButton = new JButton("Selection avec filtres");
        selectTagViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Popup de sélection");
                try {
                    selectTagView();
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

        JButton insertButton = new JButton("Insertion");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "Popup d'insertion");
                try {
                    insertView();
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

        JPanel panel = new JPanel();
        this.frame.setLayout(new BorderLayout());

        panel.add(selectViewButton);
        panel.add(selectTagViewButton);
        panel.add(insertButton);

        this.frame.add(panel, BorderLayout.NORTH);

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
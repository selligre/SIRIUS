package edu.cgl.sirius.application;

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

    public static void main(String[] args) {
        try {
            ApplicationLocal app = new ApplicationLocal();
            // app.tagFilterView();
            app.defaultView();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public ApplicationLocal() {

    }

    public void defaultView() {
        JFrame frame = new JFrame();
        frame.setTitle("Ville partagée");
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(new ActionListener() {
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

        JButton insertButton = new JButton("Insert");
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
        frame.setLayout(new FlowLayout());
        panel.add(selectButton);
        panel.add(insertButton);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void selectView() throws IOException, InterruptedException, SQLException {
        JFrame frame = new JFrame();
        frame.setSize(1280, 720);
        frame.setTitle("Tag-Filtered View");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane = new JScrollPane();
        frame.add(scrollPane);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,3));
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
                labelName.setFont(new Font("Arial", Font.BOLD, 16));
                panel.add(labelName);

                JLabel labelFirstname = new JLabel(firstname);
                labelFirstname.setFont(new Font("Arial", Font.BOLD, 16));
                panel.add(labelFirstname);
                
                JLabel labelGroup = new JLabel(group);
                labelGroup.setFont(new Font("Arial", Font.BOLD, 16));
                panel.add(labelGroup);
                
            }
        }

        // frame.pack();
        frame.add(panel);
        frame.repaint();
        frame.setVisible(true);
    }

    public void insertView() throws IOException, InterruptedException, SQLException {

        MainInsertClient client = new MainInsertClient("INSERT_USER");

    }
}
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

        MainSelectClient client = new MainSelectClient("SELECT_ALL_USERS");
        String selectResult = client.getUsers().toString();

        System.out.println(selectResult);

        String[] UserData = selectResult.split("User\\{");

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
        MainSelectClient client = new MainSelectClient("SELECT_ALL_USERS");
        String selectResult = client.getUsers().toString();

        System.out.println(selectResult);

        String[] UserData = selectResult.split("User\\{");


        // frame.pack();
        this.frame.add(panel, BorderLayout.CENTER);
        this.frame.repaint();
        this.frame.setVisible(true);
    }

    public void insertView() throws IOException, InterruptedException, SQLException {
        MainInsertClient client = new MainInsertClient();
    }

}
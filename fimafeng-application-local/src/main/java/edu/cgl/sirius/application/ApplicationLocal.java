package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cgl.sirius.client.MainSelectClient;

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
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // MainSelectClient client = new MainSelectClient("SELECT_ALL_ACTIVITIES");
        MainSelectClient client = new MainSelectClient("SELECT_ALL_USERS");
        String selectResult = client.getStudents().toString();
        selectResult = selectResult.replaceAll(",", ",<br>");
        System.out.println(selectResult);

        JLabel label = new JLabel("<html>" + selectResult + "</html>");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(label, BorderLayout.NORTH);
        // frame.pack();

        frame.setVisible(true);
    }

    public void insertView() throws IOException, InterruptedException, SQLException {

        // MainSelectClient client = new MainSelectClient("SELECT_ALL_ACTIVITIES");
        MainSelectClient client = new MainSelectClient("INSERT_USER");
    }
}
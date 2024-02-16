package edu.cgl.sirius.application;

import edu.cgl.sirius.backend.CoreBackendServer;
import edu.cgl.sirius.commons.Request;
import edu.cgl.sirius.commons.Response;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class ApplicationLocal extends JFrame {

    public static void main(String[] args) {
        try {
            ApplicationLocal app = new ApplicationLocal();
            app.tagFilterView();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public ApplicationLocal() {

    }

    public void defaultView() {
        setTitle("Ville partagée");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Popup de sélection");
            }
        });

        JButton insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Popup d'insertion");
            }
        });

        JPanel panel = new JPanel();
        setLayout(new FlowLayout());
        panel.add(selectButton);
        panel.add(insertButton);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void tagFilterView() {
        setTitle("Tag-Filtered View");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
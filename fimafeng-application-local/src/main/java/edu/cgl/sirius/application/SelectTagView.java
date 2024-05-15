package edu.cgl.sirius.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cgl.sirius.client.MainSelectAnnouncesTag;

public class SelectTagView {

    public static void main(String[] args) {
        new SelectTagView();
    }

    public SelectTagView() {
        JFrame frame = new JFrame();
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel header = new JPanel(new FlowLayout());

        String tags[] = { "Aucun", "concert", "festival", "séniors", "couple", "tout public", "musée", "peinture",
                "théatre", "visite", "adultes", "chorale", "enfants", "jeunes" };

        String tags_id[] = { "null", "1", "3", "7", "8", "9", "10", "11", "12", "13", "6", "2", "4", "5" };

        @SuppressWarnings({ "rawtypes", "unchecked" })
        final JComboBox tagList = new JComboBox(tags);

        JButton filterButton = new JButton();
        filterButton.setText("Filtrer");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Filtrage selon le tag : " + tagList.getSelectedItem() + ".");
                String selectedTagId = tags_id[tagList.getSelectedIndex()];

                JPanel content = selectFilteredData(selectedTagId);

                frame.add(content, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });

        header.add(tagList);
        header.add(filterButton);
        frame.add(header, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    public JPanel selectFilteredData(String tagId) {
        JPanel content = new JPanel();
        content.setSize(1220, 490);
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        try {
            MainSelectAnnouncesTag mainSelectAnnouncesTag = new MainSelectAnnouncesTag("SELECT_ANNOUNCES_FOR_TAG_ID",
                    tagId);
            String result = mainSelectAnnouncesTag.getAnnounces().toString();
            result = "résultat de fou";
            content.add(new JLabel(result));
        } catch (IOException | InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return content;
    }

}

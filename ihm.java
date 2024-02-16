import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

public class ihm {
    static public void main (String[] args) throws InterruptedException, IOException {
        Frame frame = new Frame();
        frame.frame();
    }
}

class Frame extends JFrame{
    JFrame frame = new JFrame();

    public void frame() throws InterruptedException, IOException{

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JToolBar tb = new JToolBar();

        JButton activity = new JButton("Activity");
        activity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });

        JButton tags = new JButton("Tags");


        JButton location = new JButton("Location");
        
        tb.add(activity);
        tb.add(tags);
        tb.add(location);

        frame.add(tb, BorderLayout.NORTH);
        frame.pack();
    }
}
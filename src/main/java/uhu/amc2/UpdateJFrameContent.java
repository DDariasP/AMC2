package uhu.amc2;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateJFrameContent {

    public static int i;

    public static void main(String[] args) {

        i = 0;

        // Create a JFrame
        JFrame frame = new JFrame("Update JFrame Content");

        // Create a JPanel to hold components
        JPanel panel = new JPanel();

        // Create a JLabel to display the message
        JLabel messageLabel = new JLabel("Initial Message");

        // Create a JButton
        JButton updateButton = new JButton("Update Message");

        // Add an ActionListener to the button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action to perform when the button is clicked
                i++;
                messageLabel.setText("Updated Message: " + i);
            }
        });

        // Add components to the panel
        panel.add(messageLabel);
        panel.add(updateButton);

        // Add the panel to the frame
        frame.add(panel);

        // Set default close operation and size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        // Make the frame visible
        frame.setVisible(true);
    }
}

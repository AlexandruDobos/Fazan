package applicationGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FinalPageGUI extends JFrame {
    private String username;
    private JPanel mainPanel;
    private JLabel winner;
    private JButton playAgain;
    private JButton exit;

    public FinalPageGUI(String appName, String username) {
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.username = username;
        this.winner.setText("Castigatorul este: " + username);

        this.mainPanel.setPreferredSize(new Dimension(500, 500));
        this.pack();
        this.playAgain.addActionListener(this::playAgain);
        this.exit.addActionListener(this::exit);
    }

    public void playAgain(ActionEvent event) {
        this.setVisible(false);
        JFrame frame = new PrincipalPageGUI("Principal Page");
        frame.setVisible(true);
    }

    public void exit(ActionEvent event) {
        this.dispose();
        System.exit(0);
    }

}

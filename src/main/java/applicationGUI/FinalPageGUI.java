package applicationGUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
        this.winner.setText("Castigatorul este: " + this.username);

        this.mainPanel.setPreferredSize(new Dimension(1000, 500));
        this.pack();
        this.playAgain.addActionListener(this::playAgain);
        this.exit.addActionListener(this::exit);
        this.getRootPane().setDefaultButton(this.playAgain);
        this.playAgain.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    switchPages();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    public void playAgain(ActionEvent event) {
        this.setVisible(false);
        JFrame frame = new PrincipalPageGUI("Fazan");
        frame.setVisible(true);
    }

    public void exit(ActionEvent event) {
        this.dispose();
        System.exit(0);
    }

    public void switchPages() {
        this.setVisible(false);
        JFrame frame = new PrincipalPageGUI("Fazan");
        frame.setVisible(true);
    }

}

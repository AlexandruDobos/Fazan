package applicationGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PrincipalPageGUI extends JFrame {
    private JPanel mainPanel;
    private JButton start;
    private JLabel name;
    private JLabel welcome;
    private JLabel copyRight;
    private JTextField username;

    public PrincipalPageGUI(String appName) {
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.mainPanel.setPreferredSize(new Dimension(1000, 500));
        this.start.addActionListener(this::startGame);
        this.pack();
        this.getRootPane().setDefaultButton(this.start);
        this.start.addKeyListener(new KeyListener() {
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

    public void startGame(ActionEvent event) {
        switchPages();
    }

    public void switchPages() {
        if (username.getText().equals("")) {
            JFrame frame = new SettingsGUI("Fazan - Settings", "Unknown Player");
            this.setVisible(false);
            frame.setVisible(true);
        } else {
            JFrame frame = new SettingsGUI("Fazan - Settings", username.getText());
            this.setVisible(false);
            frame.setVisible(true);
        }
    }
}

package applicationGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SettingsGUI extends JFrame {
    private String username;
    private JPanel dificultatePanel;
    private JLabel pickDifficulty;
    private JButton mediumDifficulty;
    private JButton hardDifficulty;

    public SettingsGUI(String appName, String username) {
        super(appName);
        this.username = username;
        this.pickDifficulty.setText("Buna, " + username + "! \n Acum alege dificultatea jocului!");
        this.mediumDifficulty.addActionListener(this::mediumDifficultyGame);
        this.hardDifficulty.addActionListener(this::hardDifficultyGame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(dificultatePanel);
        this.dificultatePanel.setPreferredSize(new Dimension(1000, 500));
        this.pack();
        this.getRootPane().setDefaultButton(this.mediumDifficulty);
        this.mediumDifficulty.addKeyListener(new KeyListener() {
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

    public void mediumDifficultyGame(ActionEvent event) {
        this.setVisible(false);
        JFrame frame = new GameBoardGUI("Fazan", username, "medium");
    }

    public void hardDifficultyGame(ActionEvent event) {
        this.setVisible(false);
        JFrame frame = new GameBoardGUI("Fazan", username, "hard");
    }

    public void switchPages() {
        this.setVisible(false);
        JFrame frame = new GameBoardGUI("Fazan", username, "medium");
    }

}

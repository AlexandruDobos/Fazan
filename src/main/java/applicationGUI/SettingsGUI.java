package applicationGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsGUI extends JFrame{
    private String username;
    private JPanel dificultatePanel;
    private JLabel pickDifficulty;
    private JButton mediumDifficulty;
    private JButton hardDifficulty;

    public SettingsGUI(String appName, String username){
        super(appName);
        this.username = username;
        this.pickDifficulty.setText("Buna, " + username + "! \n Acum alege dificultatea jocului!");
        this.mediumDifficulty.addActionListener(this::mediumDifficultyGame);
        this.hardDifficulty.addActionListener(this::hardDifficultyGame);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(dificultatePanel);
        this.dificultatePanel.setPreferredSize(new Dimension(850,500));
        this.pack();

    }
    public void mediumDifficultyGame(ActionEvent event){
        this.setVisible(false);
        JFrame frame = new GameBoardGUI("Settings", username,"medium");
        //frame.setVisible(true);
    }
    public void hardDifficultyGame(ActionEvent event){
        this.setVisible(false);
        JFrame frame = new GameBoardGUI("Settings", username,"hard");
        //frame.setVisible(true);
    }

}

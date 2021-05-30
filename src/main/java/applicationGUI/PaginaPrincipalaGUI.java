package applicationGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PaginaPrincipalaGUI extends JFrame{
    private JPanel mainPanel;
    private JButton start;
    private JLabel name;
    private JLabel welcome;
    private JLabel copyRight;
    private JTextField username;

    public PaginaPrincipalaGUI(String appName){
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.mainPanel.setPreferredSize(new Dimension(850,500));
        this.start.addActionListener(this::startGame);
        this.pack();
    }

    public void startGame(ActionEvent event){
        if(username.getText().equals("")){
           JFrame frame = new SettingsGUI("Settings", "Unknown Player");
           this.setVisible(false);
           frame.setVisible(true);
        }
        else{
            JFrame frame = new SettingsGUI("Settings", username.getText());
            this.setVisible(false);
            frame.setVisible(true);
        }
    }

    public static void main(String[] args){
        JFrame frame = new PaginaPrincipalaGUI("Fazan");
        frame.setVisible(true);
    }
}

package applicationGUI;


import utils.BackgroundPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FinalPageGUI extends JFrame {
    private String username;
    private JPanel mainPanel;
    private JLabel castigator;
    private JButton joacaDinNou;
    private JButton exit;
    public FinalPageGUI(String appName, String username){
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.username = username;
        this.castigator.setText("Castigatorul este: " + username);

        this.mainPanel.setPreferredSize(new Dimension(500,500));
        this.pack();

    }

}

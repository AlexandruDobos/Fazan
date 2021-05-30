package utils;//package utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String file) throws IOException {
        backgroundImage = ImageIO.read(new File(file));
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this);
    }

}
import javax.swing.*;
import java.awt.*;

public class SettingsGUI extends JFrame{
    private JPanel dificultatePanel;
    private JLabel alegeDificultate;
    private JButton dificultateMedie;
    private JButton dificultateGrea;

    public SettingsGUI(String appName){
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(dificultatePanel);
        this.dificultatePanel.setPreferredSize(new Dimension(500,500));
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new SettingsGUI("dificultatePanel");
        frame.setVisible(true);
    }
}

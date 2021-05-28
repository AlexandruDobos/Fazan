import javax.swing.*;
import java.awt.*;

public class PaginaPrincipalaGUI extends JFrame{
    private JPanel mainPanel;
    private JButton start;
    private JLabel name;
    private JLabel bunVenit;
    private JLabel copyRight;
    private JTextField numeUtilizator;

    public PaginaPrincipalaGUI(String appName){
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.mainPanel.setPreferredSize(new Dimension(500,500));
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new PaginaPrincipalaGUI("Fazan");
        frame.setVisible(true);
    }
}

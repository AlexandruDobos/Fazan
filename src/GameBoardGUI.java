import javax.swing.*;
import java.awt.*;

public class GameBoardGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel jucatorPanel;
    private JPanel fazanJucatorPanel;
    private JPanel calculatorPanel;
    private JPanel fazanCalculatorPanel;
    private JLabel raspunsCalculator;
    private JLabel indicatie;
    private JTextField textField1;
    private JButton sendButton;

    public GameBoardGUI(String appName){
        super(appName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.mainPanel.setPreferredSize(new Dimension(500,500));
        this.raspunsCalculator.setText("Calculatorul a raspuns: " + "blabla");
        this.pack();
    }

    public static void main(String[] args){
        JFrame frame = new GameBoardGUI("dificultatePanel");
        frame.setVisible(true);
    }
}

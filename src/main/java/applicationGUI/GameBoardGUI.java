package applicationGUI;

import utils.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameBoardGUI extends JFrame {
    private String champion = "";
    private String difficulty;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel playerPanel;
    private JPanel fazanPlayerPanel;
    private JPanel computerPanel;
    private JPanel fazanComputerPanel;
    private JLabel indications;
    private JLabel computerUsername;
    private JLabel playerUsername;
    private JLabel help;
    private JTextField entryWord;
    private JButton sendButton;
    private JButton stopButton;
    private JButton skip;
    private JLabel userF;
    private JLabel userA;
    private JLabel userZ;
    private JLabel userSecondA;
    private JLabel userN;
    private JLabel computerF;
    private JLabel computerA;
    private JLabel computerZ;
    private JLabel computerSecondA;
    private JLabel computerN;
    private boolean isFinished = false;
    private Player user;
    private Player computer;
    private List<String> usedWords;
    private boolean alphabetStop = false;
    private String firstLetter;
    private String firstTwoLetters;
    private boolean firstWordAccepted = false;

    public GameBoardGUI(String appName, String username, String difficulty) {
        super(appName);
        this.user = new Player(username);
        this.computer = new Player("Computer");
        this.difficulty = difficulty;
        this.usedWords = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.mainPanel.setPreferredSize(new Dimension(750, 500));
        this.computerUsername.setText(computer.getUsername());
        this.playerUsername.setText(username);
        //fa stop button true daca reincerci cu alpfabetul
        this.stopButton.setVisible(false);
        this.skip.addActionListener(this::skip);
        this.pack();
        this.setVisible(true);
        playGame();
    }

    public void skip(ActionEvent event){
        this.user.setChances(this.user.getChances() - 1);
        if(this.user.getChances() == 4){
            this.userF.setForeground(Color.red);
        } else if(this.user.getChances() == 3) {
            this.userA.setForeground(Color.red);
        }else if(this.user.getChances() == 2){
            this.userZ.setForeground(Color.red);
        }else if(this.user.getChances() == 1){
            this.userSecondA.setForeground(Color.red);
        }else if(this.user.getChances() == 0){
            this.userZ.setForeground(Color.red);
            this.setVisible(false);
            this.champion = computer.getUsername();
            JFrame frame = new FinalPageGUI("Final Page", champion);
            frame.setVisible(true);
        }
        playGame();
    }
    public void decrementComputerChances(){
        this.computer.setChances(this.computer.getChances() - 1);
        if(this.computer.getChances() == 4){
            this.computerF.setForeground(Color.red);
        } else if(this.computer.getChances() == 3) {
            this.computerA.setForeground(Color.red);
        }else if(this.computer.getChances() == 2){
            this.computerZ.setForeground(Color.red);
        }else if(this.computer.getChances() == 1){
            this.computerSecondA.setForeground(Color.red);
        }else if(this.computer.getChances() == 0){
            this.computerZ.setForeground(Color.red);
            this.setVisible(false);
            this.champion = user.getUsername();
            JFrame frame = new FinalPageGUI("Final Page", champion);
            frame.setVisible(true);
        }
        playGame();
    }
    public boolean wordExists(String word){
        //Select din baza de date
        return true;
        //return false;
    }
    public void verifyResponse(ActionEvent event) {
        boolean wordExistsFunctionResponse = wordExists(this.entryWord.getText());
        if(wordExistsFunctionResponse){

            firstWordAccepted = true;
            int entryWordLength = entryWord.getText().length();
            firstTwoLetters = this.entryWord.getText().substring(entryWordLength - 2, entryWordLength);
            //SELECT din baza de date where cuvant = firstTwoLetters;
            // Daca nu selecteaza nimic din baza de date
            decrementComputerChances();
            firstTwoLetters = ""; // aici trebuie sa primeasca ultimele doua litere ale selectului din baza de date.
            //TODO raspunde calculatorul si trebuie sa afisam ultimele doua litere ale cuvantului astuia
            this.indications.setText("Cuvantul tau trebuie sa inceapa cu: " + firstTwoLetters);
        }
        else{
            if(this.firstWordAccepted){
                //TODO afiseaza primele doua litere
                if(this.entryWord.getText().length() >= 2 ) {
                    this.entryWord.setText(firstTwoLetters);
                }
            }
            else{
                //TODO afiseaza din nou prima litera
                if(this.entryWord.getText().length() >= 1) {
                    this.entryWord.setText(firstLetter);
                }
            }
        }
        if (isFinished) {
            this.isFinished = false;
            this.usedWords = new ArrayList<>();
            this.setVisible(false);
            JFrame frame = new FinalPageGUI("Final Page", champion);
            frame.setVisible(true);
        }
    }
    public String alphabet(){
        //this.stopButton.setVisible(true);
        String letter;
//        int letterASCIICode = 65;
//        this.stopButton.addActionListener(this::stopButton);
//        while(!this.alphabetStop){
//            if(letterASCIICode == 91){
//                letterASCIICode = 65;
//            }
//            this.indications.setText(Character.toString((char) letterASCIICode));
//            letterASCIICode++;
//        }
//        letter = Character.toString((char) letterASCIICode - 1);

        letter = Character.toString((char) (int)(Math.random() * ((91 - 65) + 1)) + 65);
        return letter;
    }

//    public void stopButton(ActionEvent event){
//        this.alphabetStop = true;
//        this.stopButton.setVisible(false);
//    }
    public void playGame() {
        firstLetter = alphabet();
        this.firstWordAccepted = false;
        this.indications.setText("Cuvantul va incepe cu litera: " + firstLetter);
        this.entryWord.setText(firstLetter);
        this.sendButton.addActionListener(this::verifyResponse);
    }

}

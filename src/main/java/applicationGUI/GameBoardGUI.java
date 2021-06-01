package applicationGUI;

import utils.Database;
import utils.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private String lastTwoLetters;
    private boolean firstWordAccepted = false;
    private boolean firstMove = true;
    private String response = "";

    public GameBoardGUI(String appName, String username, String difficulty) {
        super(appName);
        this.user = new Player(username);
        this.computer = new Player("Computer");
        this.difficulty = difficulty;
        this.usedWords = new ArrayList<>();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.mainPanel.setPreferredSize(new Dimension(1000, 500));
        this.computerUsername.setText(computer.getUsername());
        this.playerUsername.setText(username);

        this.skip.addActionListener(this::skip);
        this.sendButton.addActionListener(this::verifyResponse);
        this.pack();
        this.setVisible(true);
        this.getRootPane().setDefaultButton(this.sendButton);
        this.sendButton.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterWord();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        playGame();
    }

    public void enterWord() {
        this.sendButton.addActionListener(this::verifyResponse);
    }

    public void skip(ActionEvent event) {
        this.user.setChances(this.user.getChances() - 1);
        if (this.user.getChances() == 4) {
            this.userF.setForeground(Color.red);
        } else if (this.user.getChances() == 3) {
            this.userA.setForeground(Color.red);
        } else if (this.user.getChances() == 2) {
            this.userZ.setForeground(Color.red);
        } else if (this.user.getChances() == 1) {
            this.userSecondA.setForeground(Color.red);
        } else if (this.user.getChances() == 0) {
            this.userN.setForeground(Color.red);
            this.setVisible(false);
            this.champion = computer.getUsername();
            JFrame frame = new FinalPageGUI("Fazan - Winner", champion);
            frame.setVisible(true);
            this.firstWordAccepted = true;
        }
        this.usedWords = new ArrayList<>();
        this.firstWordAccepted = false;
        playGame();
    }

    public void decrementComputerChances() {
        this.computer.setChances(this.computer.getChances() - 1);
        if (this.computer.getChances() == 4) {
            this.computerF.setForeground(Color.red);
        } else if (this.computer.getChances() == 3) {
            this.computerA.setForeground(Color.red);
        } else if (this.computer.getChances() == 2) {
            this.computerZ.setForeground(Color.red);
        } else if (this.computer.getChances() == 1) {
            this.computerSecondA.setForeground(Color.red);
        } else if (this.computer.getChances() == 0) {
            this.computerN.setForeground(Color.red);
            this.setVisible(false);
            this.champion = user.getUsername();
            JFrame frame = new FinalPageGUI("Fazan - Winner", champion);
            frame.setVisible(true);
            this.firstWordAccepted = false;
        }
        this.usedWords = new ArrayList<>();
        this.firstWordAccepted = false;

    }

    public String computerMove() {
        String response = "";
        try {
            Connection con = Database.getConnection();
            String verifyQuery = "";
            if (firstMove) {

                verifyQuery = "SELECT * FROM (SELECT word FROM words w WHERE first_two_letters = UPPER(?) AND " +
                        "((SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters =  " +
                        " SUBSTRING(w.word FROM char_length(w.word) - 1 FOR 2)) > 0)) sel ORDER BY random() limit 1";
                PreparedStatement statement = con.prepareStatement(verifyQuery);
                statement.setString(1, firstTwoLetters);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    response = resultSet.getString(1);
                    if (!verifyUsedWord(response)) {
                        computerMove();
                    }
                } else {
                    if (!firstMove) {
                        decrementComputerChances();
                    } else {
                        this.entryWord.setText("");
                        this.indications.setText("N-ai voie sa inchizi din prima! Cuvantul trebuie sa inceapa cu: " + firstLetter);
                    }
                }
            } else {
                if (difficulty.equals("medium")) {
                    verifyQuery = "SELECT word from words WHERE first_two_letters = UPPER(?) order by random() limit 1";
                    String numberOfOptions = "SELECT count(*) from (SELECT word from words WHERE first_two_letters = UPPER(?)) cnt";
                    PreparedStatement statement1 = con.prepareStatement(numberOfOptions);
                    statement1.setString(1, firstTwoLetters);
                    ResultSet resultSet1 = statement1.executeQuery();

                    PreparedStatement statement = con.prepareStatement(verifyQuery);
                    statement.setString(1, firstTwoLetters);
                    ResultSet resultSet = statement.executeQuery();

                    int size = 0;
                    if (resultSet1.next()) {
                        size = resultSet1.getInt(1);
                    }


                    if (resultSet.next()) {
                        response = resultSet.getString(1);
                        if (!verifyUsedWord(response)) {
                            computerMove();
                        }
                    } else {
                        if (!firstMove) {
                            decrementComputerChances();
                        } else {
                            this.entryWord.setText("");
                            this.indications.setText("N-ai voie sa inchizi din prima! Cuvantul trebuie sa inceapa cu: " + firstLetter);
                        }
                    }
                } else if (difficulty.equals("hard")) {
                    verifyQuery = "SELECT * FROM (SELECT word FROM words w WHERE first_two_letters = UPPER(?) AND " +
                            "((SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters = " +
                            " SUBSTRING(w.word FROM char_length(w.word) - 1 for 2)) = " +
                            " (SELECT MIN(count)" +
                            "  FROM (SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters = " +
                            " SUBSTRING(word from char_length(word) - 1 for 2) group by word) cnt) or " +
                            " (SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters = SUBSTRING(w.word from char_length(w.word) - 1 for 2)) = 0) limit 10) sel2 order by random() limit 1";

                    PreparedStatement statement = con.prepareStatement(verifyQuery);
                    statement.setString(1, firstTwoLetters);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        response = resultSet.getString(1);
                        if (!verifyUsedWord(response)) {
                            computerMove();
                        }
                    } else {
                        verifyQuery = "SELECT word from words WHERE first_two_letters = UPPER(?) order by random() limit 1";
                        String numberOfOptions = "SELECT count(*) from (SELECT word from words WHERE first_two_letters = UPPER(?)) cnt";
                        PreparedStatement statement1 = con.prepareStatement(numberOfOptions);
                        statement1.setString(1, firstTwoLetters);
                        ResultSet resultSet1 = statement1.executeQuery();

                        statement = con.prepareStatement(verifyQuery);
                        statement.setString(1, firstTwoLetters);
                        resultSet = statement.executeQuery();

                        int size = 0;
                        if (resultSet1.next()) {
                            size = resultSet1.getInt(1);
                        }

                        if (resultSet.next()) {
                            response = resultSet.getString(1);
                            if (!verifyUsedWord(response)) {
                                computerMove();
                            }
                        } else {

                            if (!firstMove) {
                                decrementComputerChances();
                            } else {
                                this.entryWord.setText("");
                                this.indications.setText("N-ai voie sa inchizi din prima! Cuvantul trebuie sa inceapa cu: " + firstLetter);
                            }
                        }
                    }
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return response;
    }

    public boolean wordExists(String word) {
        try {
            Connection con = Database.getConnection();
            String verifyQuery = "SELECT * from words WHERE word = UPPER(TRIM(?))";
            PreparedStatement statement = con.prepareStatement(verifyQuery);
            statement.setString(1, word);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean verifyUsedWord(String word) {
        if (usedWords.contains(word.toUpperCase())) {
            return false;
        } else {
            if (!firstMove) {
                usedWords.add(word.toUpperCase());
            }
            return true;
        }
    }

    public void verifyResponse(ActionEvent event) {
        boolean wordExistsFunctionResponse = wordExists(this.entryWord.getText());

        if ((!firstWordAccepted && this.entryWord.getText().length() >= 1 && this.entryWord.getText().substring(0, 1).equalsIgnoreCase(firstLetter)) || (firstWordAccepted && this.entryWord.getText().length() >= 2 && this.entryWord.getText().substring(0, 2).equalsIgnoreCase(firstTwoLetters))) {

            if (wordExistsFunctionResponse) {
                if (verifyUsedWord(this.entryWord.getText())) {
                    int entryWordLength = entryWord.getText().length();
                    if (entryWordLength - 2 >= 0) {
                        firstTwoLetters = this.entryWord.getText().substring(entryWordLength - 2, entryWordLength);
                    }

                    response = computerMove();
                    if (response.equals("")) {
                        if (!firstMove) {
                            playGame();
                        }
                    } else {
                        if (firstMove) {
                            usedWords.add(entryWord.getText().toUpperCase());
                            firstWordAccepted = true;
                        }
                        firstMove = false;
                        lastTwoLetters = firstTwoLetters;
                        int responseLength = response.length();
                        if (responseLength >= 2) {
                            firstTwoLetters = response.substring(responseLength - 2, responseLength);
                        }
                        this.indications.setText("Calculatorul a raspuns: " + response + ". Cuvantul tau trebuie sa inceapa cu: " + firstTwoLetters);
                        this.entryWord.setText("");
                    }
                } else {
                    if (!firstWordAccepted) {
                        this.entryWord.setText("");
                        this.indications.setText("Cuvant deja folosit. Cuvantul trebuie sa inceapa cu: " + firstLetter);
                    } else {
                        this.entryWord.setText("");
                        this.indications.setText("Cuvant deja folosit. Calculatorul a raspuns: " + response + ". Cuvantul trebuie sa inceapa cu: " + firstTwoLetters);
                    }
                }
            } else {

                if (this.firstWordAccepted) {
                    //TODO afiseaza primele doua litere
                    if (this.entryWord.getText().length() >= 2) {
                        this.indications.setText("Cuvant inexistent! Calculatorul a raspuns: " + response + " Trebuie sa inceapa cu: " + firstTwoLetters);
                        this.entryWord.setText("");
                    }
                } else {
                    //TODO afiseaza din nou prima litera
                    if (this.entryWord.getText().length() >= 1) {
                        this.indications.setText("Cuvant inexistent! Trebuie sa inceapa cu: " + firstLetter);
                        this.entryWord.setText("");
                    }
                }
            }

        } else {
            if (!firstWordAccepted) {
                this.entryWord.setText("");
                this.indications.setText("Cuvantul trebuie sa inceapa cu: " + firstLetter);
            } else {
                this.entryWord.setText("");
                this.indications.setText("Calculatorul a raspuns: " + response + ". Cuvantul trebuie sa inceapa cu: " + firstTwoLetters);
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

    public String alphabet() {
        String letter;
        letter = Character.toString((char) (int) (Math.random() * ((90 - 65) + 1)) + 65);
        return letter;
    }

    public void playGame() {
        firstLetter = alphabet();
        firstMove = true;
        this.entryWord.setText("");
        this.firstWordAccepted = false;
        this.indications.setText("Cuvantul va incepe cu litera: " + firstLetter);
    }

    @Override
    public String toString() {
        return "GameBoardGUI{" +
                "usedWords=" + usedWords +
                '}';
    }
}

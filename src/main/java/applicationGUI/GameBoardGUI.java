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
            JFrame frame = new FinalPageGUI("Fazan - Winner", this.champion);
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
            JFrame frame = new FinalPageGUI("Fazan - Winner", this.champion);
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
            if (this.firstMove) {
                /* Daca este prima mutare selectam un cuvant din baza de date care are cel putin un alt cuvant care incepe cu ultimele doua litere ale lui */
                verifyQuery = "SELECT * FROM (SELECT word FROM words w WHERE first_two_letters = UPPER(?) AND " +
                        "((SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters =  " +
                        " SUBSTRING(w.word FROM char_length(w.word) - 1 FOR 2)) > 0)) sel ORDER BY random() limit 1";
                PreparedStatement statement = con.prepareStatement(verifyQuery);
                statement.setString(1, this.firstTwoLetters);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    response = resultSet.getString(1);
                    if (!verifyUsedWord(response)) {
                        computerMove();
                    }
                } else {
                    if (!this.firstMove) {
                        decrementComputerChances();
                    } else {
                        this.entryWord.setText("");
                        this.indications.setText("N-ai voie sa inchizi din prima! Cuvantul trebuie sa inceapa cu: " + this.firstLetter);
                    }
                }
            } else {
                if (difficulty.equals("medium")) {
                    /* Selectarea unui cuvant oarecare din baza de date ce incepe cu ultimele doua litere ale cuvantului jucatorului */
                    verifyQuery = "SELECT word from words WHERE first_two_letters = UPPER(?) order by random() limit 1";

                    PreparedStatement statement = con.prepareStatement(verifyQuery);
                    statement.setString(1, this.firstTwoLetters);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        response = resultSet.getString(1);
                        if (!verifyUsedWord(response)) {
                            computerMove();
                        }
                    } else {
                        if (!this.firstMove) {
                            decrementComputerChances();
                        } else {
                            this.entryWord.setText("");
                            this.indications.setText("N-ai voie sa inchizi din prima! Cuvantul trebuie sa inceapa cu: " + this.firstLetter);
                        }
                    }
                } else if (difficulty.equals("hard")) {
                    /* Selectarea unui cuvant din baza de date care sa-i ofere jucatorului cat mai putine variante de raspuns, inclusiv nicio varianta de raspuns  */
                    verifyQuery = "SELECT * FROM (SELECT word FROM words w WHERE first_two_letters = UPPER(?) AND " +
                            "((SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters = " +
                            " SUBSTRING(w.word FROM char_length(w.word) - 1 for 2)) = " +
                            " (SELECT MIN(count)" +
                            "  FROM (SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters = " +
                            " SUBSTRING(word from char_length(word) - 1 for 2) group by word) cnt) or " +
                            " (SELECT COUNT(first_two_letters) FROM words WHERE first_two_letters = SUBSTRING(w.word from char_length(w.word) - 1 for 2)) = 0) limit 10) sel2 order by random() limit 1";

                    PreparedStatement statement = con.prepareStatement(verifyQuery);
                    statement.setString(1, this.firstTwoLetters);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        response = resultSet.getString(1);
                        if (!verifyUsedWord(response)) {
                            computerMove();
                        }
                    } else {
                        /* Daca nu exista nicio varianta de raspuns cu numarul minim de variante de raspuns pentru jucator alegem un cuvant random ce va incepe cu ultimele doua litere ale cuvantului jucatorului */
                        verifyQuery = "SELECT word from words WHERE first_two_letters = UPPER(?) order by random() limit 1";
                        statement = con.prepareStatement(verifyQuery);
                        statement.setString(1, this.firstTwoLetters);
                        resultSet = statement.executeQuery();

                        if (resultSet.next()) {
                            response = resultSet.getString(1);
                            if (!verifyUsedWord(response)) {
                                computerMove();
                            }
                        } else {

                            if (!this.firstMove) {
                                decrementComputerChances();
                            } else {
                                this.entryWord.setText("");
                                this.indications.setText("N-ai voie sa inchizi din prima! Cuvantul trebuie sa inceapa cu: " + this.firstLetter);
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
            if (!this.firstMove) {
                usedWords.add(word.toUpperCase());
            }
            return true;
        }
    }

    public void verifyResponse(ActionEvent event) {
        boolean wordExistsFunctionResponse = wordExists(this.entryWord.getText());

        if ((!this.firstWordAccepted && this.entryWord.getText().length() >= 1 && this.entryWord.getText().substring(0, 1).equalsIgnoreCase(this.firstLetter)) || (this.firstWordAccepted && this.entryWord.getText().length() >= 2 && this.entryWord.getText().substring(0, 2).equalsIgnoreCase(this.firstTwoLetters))) {

            if (wordExistsFunctionResponse) {
                if (verifyUsedWord(this.entryWord.getText())) {
                    int entryWordLength = this.entryWord.getText().length();
                    if (entryWordLength - 2 >= 0) {
                        this.firstTwoLetters = this.entryWord.getText().substring(entryWordLength - 2, entryWordLength);
                    }

                    this.response = computerMove();
                    if (this.response.equals("")) {
                        if (!this.firstMove) {
                            playGame();
                        }
                    } else {
                        if (this.firstMove) {
                            usedWords.add(entryWord.getText().toUpperCase());
                            this.firstWordAccepted = true;
                        }
                        this.firstMove = false;
                        this.lastTwoLetters = this.firstTwoLetters;
                        int responseLength = this.response.length();
                        if (responseLength >= 2) {
                            this.firstTwoLetters = this.response.substring(responseLength - 2, responseLength);
                        }
                        this.indications.setText("Calculatorul a raspuns: " + this.response + ". Cuvantul tau trebuie sa inceapa cu: " + this.firstTwoLetters);
                        this.entryWord.setText("");
                    }
                } else {
                    if (!this.firstWordAccepted) {
                        this.entryWord.setText("");
                        this.indications.setText("Cuvant deja folosit. Cuvantul trebuie sa inceapa cu: " + this.firstLetter);
                    } else {
                        this.entryWord.setText("");
                        this.indications.setText("Cuvant deja folosit. Calculatorul a raspuns: " + this.response + ". Cuvantul trebuie sa inceapa cu: " + this.firstTwoLetters);
                    }
                }
            } else {

                if (this.firstWordAccepted) {
                    if (this.entryWord.getText().length() >= 2) {
                        this.indications.setText("Cuvant inexistent! Calculatorul a raspuns: " + this.response + " Trebuie sa inceapa cu: " + this.firstTwoLetters);
                        this.entryWord.setText("");
                    }
                } else {
                    if (this.entryWord.getText().length() >= 1) {
                        this.indications.setText("Cuvant inexistent! Trebuie sa inceapa cu: " + this.firstLetter);
                        this.entryWord.setText("");
                    }
                }
            }

        } else {
            if (!this.firstWordAccepted) {
                this.entryWord.setText("");
                this.indications.setText("Cuvantul trebuie sa inceapa cu: " + this.firstLetter);
            } else {
                this.entryWord.setText("");
                this.indications.setText("Calculatorul a raspuns: " + this.response + ". Cuvantul trebuie sa inceapa cu: " + this.firstTwoLetters);
            }

        }
    }

    public String alphabet() {
        String letter;
        letter = Character.toString((char) (int) (Math.random() * ((90 - 65) + 1)) + 65);
        return letter;
    }

    public void playGame() {
        this.firstLetter = alphabet();
        this.firstMove = true;
        this.entryWord.setText("");
        this.firstWordAccepted = false;
        this.indications.setText("Cuvantul va incepe cu litera: " + this.firstLetter);
    }
}

package utils;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InsertWords {
    public InsertWords() {
        try {
            File file = new File("C:\\Users\\dobos\\IdeaProjects\\Fazan\\src\\main\\java\\resources\\RomanianDictionary.txt");    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    Connection con = Database.getConnection();
                    String verifyQuery = "SELECT * from words WHERE word = ?";
                    PreparedStatement statement = con.prepareStatement(verifyQuery);
                    statement.setString(1, line);
                    ResultSet resultSet = statement.executeQuery();
                    if(!resultSet.next()) {
                        String query = "INSERT INTO words (word, first_two_letters) VALUES (?,?)";
                        PreparedStatement posted = con.prepareStatement(query);
                        posted.setString(1, line);
                        if (line.length() >= 2) {
                            posted.setString(2, line.substring(0, 2));
                        } else {
                            posted.setString(2, "  ");
                        }
                        posted.execute();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            fr.close();    //closes the stream and release the resources
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


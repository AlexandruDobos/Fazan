package utils;

public class Player {
    private String username;
    private int chances;

    public Player(String username) {
        this.username = username;
        this.chances = 5;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }
}

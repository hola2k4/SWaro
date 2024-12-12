package vn.edu.usth.clothesapp.db;

public class User {
    private String Userid;
    private String PasswordHash;

    // Constructor
    public User(String userid, String passwordHash) {
        Userid = userid;
        PasswordHash = passwordHash;
    }

    // Getter và Setter cho Userid
    public String getUsername() {
        return Userid;
    }

    public void setUsername(String userid) {
        Userid = userid;
    }

    // Getter và Setter cho PasswordHash
    public String getPassword() {
        return PasswordHash;
    }

    public void setPassword(String passwordHash) {
        PasswordHash = passwordHash;
    }
}

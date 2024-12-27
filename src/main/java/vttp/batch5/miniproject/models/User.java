package vttp.batch5.miniproject.models;

import jakarta.validation.constraints.NotEmpty;

public class User {
    
    @NotEmpty(message="Username is a compulsory field")
    private String username;
    
    @NotEmpty(message="Password is a compulsory field")
    private String password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

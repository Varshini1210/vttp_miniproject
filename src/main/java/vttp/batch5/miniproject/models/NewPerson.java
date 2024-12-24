package vttp.batch5.miniproject.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewPerson {
    
    @NotEmpty(message = "username is mandatory")
    @Size(min =5, max=16, message="username must be between 5 and 16 characters")
    private String username;

    @NotEmpty(message = "email is mandatory")
    @Email(message = "Email must adhere to email format")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 8 characters, including at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    @NotEmpty(message="Please enter a password")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
    message = "Password must contain at least 8 characters, including at least one uppercase letter, one lowercase letter, one digit, and one special character.")
    @NotEmpty(message="Please re-enter your password")
    private String password2;
    
    public NewPerson(String username, String email, String password, String password2) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.password2 = password2;
    }

    public NewPerson() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    @Override
    public String toString() {
        return   username + ","+ email + ","+ password;
    } 
}

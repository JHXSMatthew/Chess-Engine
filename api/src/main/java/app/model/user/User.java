package app.model.user;

import javax.persistence.*;
import java.io.Serializable;

// maybe need a generated UUID?
@Entity
@Table(name="user", indexes = {
        @Index(name="userName" , columnList = "userName")})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name= "userName", nullable = false)
    private String userName;

    @Column
    private String password;
    @Column
    private String email;


    public User(){}

    public User(int id, String UserName, String Password, String Email){
        this.userName = UserName;
        this.password = Password;
        this.email = Email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

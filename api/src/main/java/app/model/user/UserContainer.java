package app.model.user;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

// maybe need a generated UUID?
@Entity
@Table(name="user_container")
public class UserContainer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserId;
    private String UserName;
    @Column
    private String Password;
    @Column
    private String Email;


    public UserContainer(){}

    public UserContainer(int id, String UserName, String Password, String Email){
        this.UserId = id;
        this.UserName = UserName;
        this.Password = Password;
        this.Email = Email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

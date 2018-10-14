package app.model.user;

import javax.persistence.*;

/**
 * Created by JHXSMatthew on 13/10/18.
 */
@Entity
@Table(name = "auth_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    @Column( nullable = false)
    private String token;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

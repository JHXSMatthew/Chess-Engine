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

    @OneToOne
    private User user = null;

    @Column( nullable = false)
    private String token;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object obj) {
        return obj!=null && obj instanceof Token && ((Token) obj).id == this.id;
    }
}

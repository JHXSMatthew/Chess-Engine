package app.model.user;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;

/**
 * Created by JHXSMatthew on 13/10/18.
 */
@Entity
@Table(name = "auth_token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "This token's id")
    private int id;

    @OneToOne
    @JoinColumn(unique = true)
    @ApiModelProperty(notes = "This token's belongs to ")
    private User user = null;

    @Column( nullable = false)
    @ApiModelProperty(notes = "Token value")
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

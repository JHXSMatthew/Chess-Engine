package model.move;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Entity
@Table(name = "MoveHistory")
public class MoveHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(nullable = false)
    private String state;
    @NotBlank
    @Column(nullable = false)
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return this.id;
    }

    public String getState() {
        return this.state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

}

package hello;

import javax.persistence.*;


@Entity
public class MoveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;
    private String state;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId(){
        return this.id;
    }

    public String getState(){
        return this.state;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

}

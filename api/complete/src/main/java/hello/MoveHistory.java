package hello;

import javax.persistence.*;


@Entity
public class MoveHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer id;
    private String state;

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

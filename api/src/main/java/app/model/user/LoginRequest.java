package app.model.user;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by JHXSMatthew on 13/10/18.
 */
public class LoginRequest {

    @ApiModelProperty(notes = "User name used to login")
    private String userName;
    @ApiModelProperty(notes = "Password used to login")
    private String password;

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
}

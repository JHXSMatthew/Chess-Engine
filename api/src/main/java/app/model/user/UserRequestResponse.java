package app.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestResponse {
    @JsonProperty("userName")
    private String UserName;
    @JsonProperty("email")
    private String Email;

    public UserRequestResponse(){}

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


}

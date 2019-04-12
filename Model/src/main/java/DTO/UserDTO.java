package DTO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    String username;
    String password;


    @Override
    public String toString() {
        return "Angajat{" +
                "username='" + username + '\'' +
                ", password='" + password + '\''+
                '}';
    }



    public void setUsername(String username) {
        this.username = username;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return this.username;
    }


    public String getPassword() {
        return password;
    }

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

package Domain;

import java.io.Serializable;

public interface User extends HasID<String>, Serializable {
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
}

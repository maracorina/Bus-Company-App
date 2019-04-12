package Protocol;

import DTO.UserDTO;

public class LogInRequest implements Request {
    private UserDTO user;

    public LogInRequest(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return this.user;
    }
}

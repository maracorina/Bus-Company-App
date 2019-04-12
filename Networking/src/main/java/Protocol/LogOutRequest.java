package Protocol;

import Domain.User;

public class LogOutRequest implements Request {
    private User user;

    public User getUser() {
        return user;
    }

    public LogOutRequest(User user) {
        this.user = user;
    }
}

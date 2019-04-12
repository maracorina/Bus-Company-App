package Domain;

public class Angajat implements User{
    String username;
    String password;
    String ID;


    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "Angajat{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }


    public String getID() {
        return ID;
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

    public Angajat(String id, String username, String password) {
        this.ID=id;
        this.username = username;
        this.password = password;
    }
}

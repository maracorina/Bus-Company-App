package Domain;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Cursa implements HasID<String>, Serializable {
    String id;
    String destinatia;
    LocalDateTime data;
    int locuriDisponibile;

    public Cursa(String id, String destinatia, LocalDateTime data, int locuriDisponibile) {
        this.id = id;
        this.destinatia = destinatia;
        this.data = data;
        this.locuriDisponibile = locuriDisponibile;
    }

    @Override
    public String toString() {
        return "Cursa{" +
                "id='" + id + '\'' +
                ", destinatia='" + destinatia + '\'' +
                ", data=" + data +
                ", locuriDisponibile=" + locuriDisponibile +
                '}';
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public void setDestinatia(String destinatia) {
        this.destinatia = destinatia;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public void setLocuriDisponibile(int locuriDisponibile) {
        this.locuriDisponibile = locuriDisponibile;
    }

    @Override
    public String getID() {
        return id;
    }

    public String getDestinatia() {
        return destinatia;
    }

    public LocalDateTime getData() {
        return data;
    }

    public int getLocuriDisponibile() {
        return locuriDisponibile;
    }
}

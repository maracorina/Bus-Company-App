package Domain;

import java.io.Serializable;

public class Rezervare implements HasID<String>, Serializable {
    String id;
    String idCursa;
    String nume;
    int nrLocuri;

    public Rezervare(String id, String idCursa, String nume, int nrLocuri) {
        this.id = id;
        this.idCursa = idCursa;
        this.nume = nume;
        this.nrLocuri = nrLocuri;
    }

    @Override
    public String getID() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }

    public void setIdCursa(String idCursa) {
        this.idCursa = idCursa;
    }

    public String getIdCursa() {
        return idCursa;
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "id='" + id + '\'' +
                ", nume='" + nume + '\'' +
                ", nrLocuri=" + nrLocuri +
                '}';
    }

    @Override
    public void setID(String id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }
}

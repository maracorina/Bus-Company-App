package DTO;

import java.io.Serializable;

public class RezervareDTO implements Serializable {
    String idCursa;
    int nrLoc;
    String nume;

    public RezervareDTO(String idCursa, int nrLoc, String nume) {
        this.idCursa = idCursa;
        this.nrLoc = nrLoc;
        this.nume = nume;
    }

    public String getIdCursa() {
        return idCursa;
    }

    public int getNrLoc() {
        return nrLoc;
    }

    public String getNume() {
        return nume;
    }

    public void setNrLoc(int nrLoc) {
        this.nrLoc = nrLoc;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}

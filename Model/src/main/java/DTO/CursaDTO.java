package DTO;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CursaDTO implements Serializable {
    String destinatia;
    LocalDateTime data;
    int nrLocuri;

    public CursaDTO(String destinatia, LocalDateTime data, int nrLocuri) {
        this.destinatia = destinatia;
        this.data = data;
        this.nrLocuri=nrLocuri;
    }

    public void setDestinatia(String destinatia) {
        this.destinatia = destinatia;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }


    public String getDestinatia() {
        return destinatia;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setNrLocuri(int nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    public int getNrLocuri() {
        return nrLocuri;
    }
}

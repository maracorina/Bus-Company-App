package Protocol;

import Domain.Rezervare;

public class NewRezervareResponse implements UpdateResponse {
    private Rezervare rezervare;

    public Rezervare getRezervare() {
        return rezervare;
    }

    public NewRezervareResponse(Rezervare rezervare) {
        this.rezervare = rezervare;
    }
}

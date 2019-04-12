package Protocol;

import DTO.RezervareDTO;

public class AddRezervareRequest implements Request {
    private RezervareDTO rezervare;

    public RezervareDTO getRezervare() {
        return rezervare;
    }

    public AddRezervareRequest(RezervareDTO rezervare) {
        this.rezervare = rezervare;
    }
}

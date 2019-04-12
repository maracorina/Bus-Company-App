package Protocol;

import DTO.RezervareDTO;

public class GetRezervariResponse implements Response {
    private RezervareDTO[] rezervari;

    public RezervareDTO[] getRezervari() {
        return rezervari;
    }

    public GetRezervariResponse(RezervareDTO[] rezervari) {
        this.rezervari = rezervari;
    }
}

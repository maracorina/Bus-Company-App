package Protocol;

import Domain.Cursa;

public class GetCurseResponse implements Response {
    private Cursa[] Curse;

    public GetCurseResponse(Cursa[] curse) {
        Curse = curse;
    }

    public Cursa[] getCurse() {
        return Curse;
    }
}

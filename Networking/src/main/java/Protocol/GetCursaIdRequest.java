package Protocol;

import DTO.CursaDTO;

public class GetCursaIdRequest implements Request {
    private CursaDTO cursa;

    public CursaDTO getCursa() {
        return cursa;
    }

    public GetCursaIdRequest(CursaDTO cursa) {
        this.cursa = cursa;
    }
}

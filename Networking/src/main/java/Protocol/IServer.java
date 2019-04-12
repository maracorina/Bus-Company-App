package Protocol;

import DTO.CursaDTO;
import Domain.*;
import Utils.AppException;

public interface IServer {
    void login(User var1, IAppObserver client) throws AppException;

    void logout(User var1) throws AppException;

    Cursa[] getCurse() throws AppException;

    Rezervare[] getRezervari(String idCursa) throws AppException;

    void addRezervare(Rezervare rezervare) throws AppException;

    String getCursaId(CursaDTO cursa) throws AppException;
}

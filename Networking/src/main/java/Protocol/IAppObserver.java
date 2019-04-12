package Protocol;

import Domain.Rezervare;
import Utils.AppException;

public interface IAppObserver {
    public void rezervareAdded(Rezervare rezervare) throws AppException;
}

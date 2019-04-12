package Services;

import Domain.Rezervare;
import Utils.ChangeEventType;
import Utils.RezervareChangeEvent;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface IServiceRezervari {
    public Iterable<Rezervare> findByCursa(String idCursa);

    public Rezervare add(String idCursa, String nume, int nrLocuri);
}

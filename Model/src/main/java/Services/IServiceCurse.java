package Services;

import Domain.Cursa;
import Utils.RezervareChangeEvent;

public interface IServiceCurse {
    public Iterable<Cursa> findAll();

    public Cursa findByDestAndData(String destinatie, String data);

    public void update(RezervareChangeEvent rezervareChangeEvent);

}


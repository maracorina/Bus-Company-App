package Service;

import Domain.Rezervare;
import Repository.DBRepositoryRezervari;
import Services.IServiceRezervari;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ServiceRezervari implements IServiceRezervari{
    private DBRepositoryRezervari repo;

    public ServiceRezervari(DBRepositoryRezervari repo) {
        this.repo = repo;
    }

    public Iterable<Rezervare> findByCursa(String idCursa){
        return repo.findByCursa(idCursa);
    }

    public Rezervare add(String idCursa, String nume, int nrLocuri){
        String id= (StreamSupport.stream(repo.findAll().spliterator(), false)
                .collect(Collectors.toList()).size()+1)+"";
        Rezervare r= new Rezervare(id, idCursa, nume, nrLocuri);
        repo.save(r);
        return r;
    }

}

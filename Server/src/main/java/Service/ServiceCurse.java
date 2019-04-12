package Service;

import Domain.Cursa;
import Repository.DBRepositoryCurse;
import Services.IServiceCurse;
import Utils.*;


public class ServiceCurse implements IServiceCurse{
    private DBRepositoryCurse repo;

    public ServiceCurse(DBRepositoryCurse repo) {
        this.repo = repo;
    }

    public Iterable<Cursa> findAll(){
        return repo.findAll();
    }

    public Cursa findByDestAndData(String destinatie, String data){
        return repo.findByDestAndData(destinatie, data);
    }

    @Override
    public void update(RezervareChangeEvent rezervareChangeEvent) {
        Cursa c= repo.findOne(rezervareChangeEvent.getData().getIdCursa());
        Cursa cNew=new Cursa(c.getID(), c.getDestinatia(), c.getData(), c.getLocuriDisponibile());
        cNew.setLocuriDisponibile(c.getLocuriDisponibile()-rezervareChangeEvent.getData().getNrLocuri());
        repo.update(cNew);
    }
}

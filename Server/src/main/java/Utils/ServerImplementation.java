package Utils;

import DTO.CursaDTO;
import Domain.*;
import Protocol.IAppObserver;
import Protocol.IServer;
import Service.ServiceAngajati;
import Service.ServiceCurse;
import Service.ServiceRezervari;
import Services.IServiceAngajati;
import Services.IServiceCurse;
import Services.IServiceRezervari;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class ServerImplementation implements IServer {
    private IServiceAngajati srvA;
    private IServiceCurse srvC;
    private IServiceRezervari srvR;
    private Map<String, IAppObserver> localClients=new HashMap<>();
    private final int defaultThreadsNo = 5;

    public ServerImplementation(IServiceAngajati srvA, IServiceCurse srvC, IServiceRezervari srvR) {
        this.srvA = srvA;
        this.srvC = srvC;
        this.srvR = srvR;
    }


    public synchronized void login(User user, IAppObserver client) throws AppException {
        if (srvA.validLogger(user.getUsername(), user.getPassword())) {
            localClients.put(user.getUsername(), client);
        } else {
            throw new AppException("Authentication failed.");
        }
    }

    public synchronized Cursa[] getCurse() throws AppException {
        Iterable<Cursa> curse = this.srvC.findAll();
        List<Cursa> result = new ArrayList<>();
        Iterator var4 = curse.iterator();

        while(var4.hasNext()) {
            Cursa friend = (Cursa)var4.next();
            result.add(friend);
        }

        return (Cursa[])result.toArray(new Cursa[result.size()]);
    }

    public synchronized Rezervare[] getRezervari(String idCursa) throws AppException {
        Iterable<Rezervare> rezervari = this.srvR.findByCursa(idCursa);
        List<Rezervare> result = new ArrayList<>();
        Iterator var4 = rezervari.iterator();

        while(var4.hasNext()) {
            Rezervare friend = (Rezervare) var4.next();
            result.add(friend);
        }

        return (Rezervare[])result.toArray(new Rezervare[result.size()]);
    }

    @Override
    public void addRezervare(Rezervare rezervare) throws AppException {
        srvR.add(rezervare.getIdCursa(), rezervare.getNume(), rezervare.getNrLocuri());
        srvC.update(new RezervareChangeEvent(ChangeEventType.ADD, rezervare));

        if (localClients != null) {
            localClients.forEach((key, c) -> {
                try{
                    c.rezervareAdded(rezervare);
                }catch (AppException ex){
                    ex.printStackTrace();
                }
            });
        } else {
            throw new AppException("No users are logged in.");
        }
    }

    public synchronized String getCursaId(CursaDTO cursa) throws AppException {
        return srvC.findByDestAndData(cursa.getDestinatia(), cursa.getData().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).getID();
    }

    @Override
    public void logout(User user) throws AppException {
        IAppObserver localClient = (IAppObserver)this.localClients.remove(user.getUsername());
        if (localClient == null) {
            throw new AppException("User " + user.getUsername() + " is not logged in.");
        }
    }
}



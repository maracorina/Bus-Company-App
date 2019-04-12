package Controller;


import Domain.Rezervare;
import DTO.RezervareDTO;
import Protocol.IAppObserver;
import Protocol.IServer;
import Utils.AppException;
import Utils.Observer;
import Utils.RezervareChangeEvent;
import Views.RezervariView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class RezervariController implements Observer<RezervareChangeEvent>, IAppObserver {
    private IServer server;
    private RezervariView view;
    private String idCursa;



    public RezervariController(IServer server) {
        this.server = server;
    }

    private ObservableList<RezervareDTO> model= FXCollections.observableArrayList();

    public ObservableList<RezervareDTO> getModel() {
        return model;
    }

    public RezervariView getView() {
        return view;
    }

    public void setView(RezervariView view) {
        this.view = view;
    }


    public void handlesearch(ActionEvent a, String idCursa){
        this.idCursa=idCursa;
        updateSearch(a);
        if(!view.getStage().isShowing())
            view.getStage().showAndWait();
        else
            view.getStage().toFront();
    }

    public void handleAdd(ActionEvent a){
        try {
            server.addRezervare(new Rezervare("", idCursa, view.textFieldNume.getText(), Integer.parseInt(view.textFieldLocuri.getText())));
        }catch (AppException ex){
            ex.printStackTrace();
        }
        }

    public void updateList(){
        List<RezervareDTO> list = new ArrayList<>();
        try {
            Rezervare[] rezervari = server.getRezervari(idCursa);
            for(int i=0; i<rezervari.length; i++){
                list.add(new RezervareDTO(idCursa, rezervari[i].getNrLocuri(), rezervari[i].getNume()));
            }

            List<String> listNames = new ArrayList<>();
            list.forEach(r->{
                for(int i=0; i<r.getNrLoc(); i++)
                    listNames.add(r.getNume());
            });

            list.clear();
            int i=1;
            while(i<=listNames.size()){
                list.add(new RezervareDTO(idCursa, i, listNames.get(i-1)));
                i++;
            }
            while(i<=18){
                list.add(new RezervareDTO(idCursa, i, "-"));
                i++;
            }

            model.setAll(StreamSupport.stream(list.spliterator(), false)
                    .collect(Collectors.toList()));
        }catch (AppException ex){
            ex.printStackTrace();
        }
    }

    public void updateSearch(ActionEvent a) {
        updateList();
    }

    @Override
    public void update(RezervareChangeEvent rezervareChangeEvent) {
        updateList();
    }

    @Override
    public void rezervareAdded(Rezervare rezervare){
        if(idCursa.equals(rezervare.getIdCursa())){
            int i = 0;
            int k = -1;

            for(Iterator var4 = this.model.iterator(); var4.hasNext(); ++i) {
                RezervareDTO rez = (RezervareDTO) var4.next();
                if (rez.getNume().equals("-") && k==-1) {
                    k = i;
                }
            }

            for(int j=0; j<rezervare.getNrLocuri(); j++) {
                this.model.set(k, new RezervareDTO(rezervare.getIdCursa(), k + 1, rezervare.getNume()));
                k++;
            }
            this.view.getTableView().setItems(this.model);
        }
    }
}

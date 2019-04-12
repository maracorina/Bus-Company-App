package Controller;


import Domain.Angajat;
import Domain.Cursa;
import DTO.CursaDTO;
import Domain.Rezervare;
import Domain.User;
import Protocol.IAppObserver;
import Protocol.IServer;
import Utils.*;
import Views.CurseView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CurseController implements IAppObserver {
    private IServer server;
    private CurseView view;
    private User user;
    private ObservableList<Cursa> model= FXCollections.observableArrayList();

    public ObservableList<Cursa> getModel() {
        return model;
    }


    public CurseController(IServer s) {
        server=s;
    }

    public void setView(CurseView view) {
        this.view = view;
    }

    public void handleLogIn(User user){
        List<Cursa> list = new ArrayList<>();
        try {
            Cursa[] curse = server.getCurse();
            for(int i=0; i<curse.length; i++){
                list.add(curse[i]);
            }
            model.setAll(StreamSupport.stream(list.spliterator(), false)
                    .collect(Collectors.toList()));

            this.user=user;
        }catch (AppException ex){
            ex.printStackTrace();
        }
    }

    public CurseView getView() {
        return view;
    }

    public void handleSearch(ActionEvent a){
        try {
            String id = server.getCursaId(new CursaDTO(view.textFieldDestinatie.getText(), LocalDateTime.parse(view.textFieldData.getText() + " " + view.textFieldOra.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 0));
            MainController.handleSetRezervariView(a, id);
        }catch (AppException er){
            er.printStackTrace();
        }
    }

    public void handleLogout(ActionEvent a){
        try {
            server.logout(user);
            MainController.handleLogOutView(a);
        }catch(AppException ex){
            ex.printStackTrace();
        }
    }


    public void showDetails(Cursa c) {
        if (c==null)
        {
            view.textFieldDestinatie.setText("");
            view.textFieldData.setText("");
            view.textFieldOra.setText("");
        }
        else
        {
            view.textFieldDestinatie.setText(c.getDestinatia());
            view.textFieldData.setText(c.getData().toLocalDate().toString());
            view.textFieldOra.setText(c.getData().toLocalTime().toString());
        }
    }

    @Override
    public void rezervareAdded(Rezervare rezervare){
        int i = 0;
        int k = 0;
        Cursa cursaGasita=null;

        for(Iterator var4 = this.model.iterator(); var4.hasNext(); ++i) {
            Cursa cursa = (Cursa)var4.next();
            if (cursa.getID().equals(rezervare.getIdCursa())) {
                k = i;
                cursaGasita=cursa;
            }
        }

        Cursa newC = new Cursa(cursaGasita.getID(), cursaGasita.getDestinatia(), cursaGasita.getData(), cursaGasita.getLocuriDisponibile()-rezervare.getNrLocuri());
        this.model.set(k, newC);
        this.view.getTableView().setItems(this.model);
    }
}

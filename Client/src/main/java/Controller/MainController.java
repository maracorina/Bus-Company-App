package Controller;

import Domain.Angajat;
import Domain.Rezervare;
import Domain.User;
import Protocol.IAppObserver;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class MainController implements IAppObserver {
    static private CurseController ctrlC;
    static private RezervariController ctrlR;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

     private Stage root;

    public MainController(CurseController c, RezervariController r) {
        ctrlC=c;
        ctrlR = r;
    }

    public void setRoot(Stage root) {
        this.root = root;
    }

    public static void handleLogOutView(ActionEvent actionEvent) {
        if(ctrlR.getView().getStage().isShowing())
            ctrlR.getView().getStage().close();
        ((Node)((Node)actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void handleSetCurseView(ActionEvent actionEvent, Stage root) {
        this.root=root;
        root.close();
        root=new Stage();
        ctrlC.handleLogIn(this.user);
        root.setScene(ctrlC.getView().getView());
        root.setTitle("Curse");
        root.show();
    }

    static public void handleSetRezervariView(ActionEvent actionEvent, String cursa) {
        ctrlR.handlesearch(actionEvent, cursa);
    }

    @Override
    public void rezervareAdded(Rezervare rezervare) {
        ctrlC.rezervareAdded(rezervare);
        ctrlR.rezervareAdded(rezervare);
    }
}

package Controller;


import Domain.Angajat;
import Protocol.IServer;
import Utils.AppException;
import Views.LogInView;
import Views.MessageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class LogInController {
    private IServer server;
    private LogInView view;
    private MainController mainCtrl;
    static private Stage root;

    public MainController getMainCtrl() {
        return mainCtrl;
    }

    public LogInController(IServer server, MainController mainCtrl, Stage root) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.root=root;
    }

    public LogInView getView() {
        return view;
    }

    public void setView(LogInView v) {
        view = v;
        root.setScene(view.getView());
        root.setTitle("Log in");
    }

    public void handleLogInButton(ActionEvent actionEvent) {
        String username = view.textFieldUsername.getText();
        String password = view.textFieldPassword.getText();
        try {
            this.server.login(new Angajat("", username, password), this.mainCtrl);
            mainCtrl.setUser(new Angajat("", username, password));
            this.mainCtrl.handleSetCurseView(actionEvent, root);
        } catch (AppException var6) {
            MessageView.showErrorMessage("Invalid user");
        }
    }
}

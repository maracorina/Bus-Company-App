import Controller.CurseController;
import Controller.LogInController;
import Controller.MainController;
import Controller.RezervariController;
import Protocol.IServer;
import Protocol.ServerProxy;
import Views.CurseView;
import Views.LogInView;
import Views.RezervariView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class ClientStart extends Application {
    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";
    //private static final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Properties clientProps = new Properties();

        try {
            clientProps.load(ClientStart.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException var8) {
            System.err.println("Cannot find client.properties " + var8);
            return;
        }

        String serverIP = clientProps.getProperty("tasks.server.host", defaultServer);
        int serverPort = defaultPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("tasks.server.port"));
        } catch (NumberFormatException var7) {
            System.err.println("Wrong port number " + var7.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }

        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);
        IServer server = new ServerProxy(serverIP, serverPort);



        CurseController ctrl2=new CurseController(server);
        CurseView view2=new CurseView(ctrl2);
        ctrl2.setView(view2);
        view2.setController(ctrl2);

        RezervariController ctrl1=new RezervariController(server);
        RezervariView view1=new RezervariView(ctrl1);
        ctrl1.setView(view1);

        MainController control=new MainController(ctrl2, ctrl1);

        LogInController ctrl3=new LogInController(server, control, primaryStage);
        LogInView view3=new LogInView(ctrl3);
        ctrl3.setView(view3);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

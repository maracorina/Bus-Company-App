
import Protocol.IServer;
import Protocol.IServer;
import Repository.DBRepositoryAngajati;
import Repository.DBRepositoryCurse;
import Repository.DBRepositoryRezervari;
import Service.ServiceAngajati;
import Service.ServiceCurse;
import Service.ServiceRezervari;
import Utils.ObjectConcurrentServer;
import Utils.ServerException;
import Utils.ServerImplementation;

import java.io.IOException;
import java.util.Properties;

public class ServerStart {
    private static int defaultPort = 55555;

    public ServerStart() {
    }

    public static void main(String[] args) {
        Properties serverProps = new Properties();

        try {
            serverProps.load(ServerStart.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException var10) {
            System.err.println("Cannot find server.properties " + var10);
            return;
        }

        DBRepositoryCurse bazaC=new DBRepositoryCurse(serverProps);

        DBRepositoryRezervari bazaR=new DBRepositoryRezervari(serverProps);

        DBRepositoryAngajati bazaA=new DBRepositoryAngajati(serverProps);


        ServiceAngajati srv3=new ServiceAngajati(bazaA);

        ServiceCurse srv2=new ServiceCurse(bazaC);

        ServiceRezervari srv1=new ServiceRezervari(bazaR);

        IServer chatServerImpl = new ServerImplementation(srv3, srv2, srv1);
        int ServerPort = defaultPort;

        try {
            ServerPort = Integer.parseInt(serverProps.getProperty("tasks.server.port"));
        } catch (NumberFormatException var9) {
            System.err.println("Wrong  Port Number" + var9.getMessage());
            System.err.println("Using default port " + defaultPort);
        }

        System.out.println("Starting server on port: " + ServerPort);
        ObjectConcurrentServer server = new ObjectConcurrentServer(ServerPort, chatServerImpl);

        try {
            server.start();
        } catch (ServerException var8) {
            System.err.println("Error starting the server" + var8.getMessage());
        }

    }
}

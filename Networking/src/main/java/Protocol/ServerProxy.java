package Protocol;

import DTO.CursaDTO;
import DTO.RezervareDTO;
import DTO.UserDTO;
import Domain.*;
import Utils.AppException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerProxy implements IServer {
    private String host;
    private int port;
    private IAppObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServerProxy(String host, int port) {
        this.host = host;
        this.port = port;
        this.qresponses = new LinkedBlockingQueue();
    }

    public void login(User user, IAppObserver client) throws AppException {
        this.initializeConnection();
        this.sendRequest(new LogInRequest(new UserDTO(user.getUsername(), user.getPassword())));
        Response response = this.readResponse();
        if (response instanceof OkResponse) {
            this.client=client;
        } else if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            this.closeConnection();
            throw new AppException(err.getMessage());
        }
    }

    public void addRezervare(Rezervare rezervare) throws AppException {
        this.sendRequest(new AddRezervareRequest(new RezervareDTO(rezervare.getIdCursa(), rezervare.getNrLocuri(), rezervare.getNume())));
        Response response = this.readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse) response;
            throw new AppException(err.getMessage());
        }
    }

    public void logout(User user) throws AppException {
        this.sendRequest(new LogOutRequest(user));
        Response response = this.readResponse();
        this.closeConnection();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse)response;
            throw new AppException(err.getMessage());
        }
    }


    public Cursa[] getCurse() throws AppException {
        this.sendRequest(new GetCurseRequest());
        Response response = this.readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse)response;
            throw new AppException(err.getMessage());
        } else {
            GetCurseResponse resp = (GetCurseResponse)response;
            Cursa[] curse = resp.getCurse();

            return curse;
        }
    }

    public Rezervare[] getRezervari(String idCursa) throws AppException {
        this.sendRequest(new GetRezervariRequest(idCursa));
        Response response = this.readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse)response;
            throw new AppException(err.getMessage());
        } else {
            GetRezervariResponse resp = (GetRezervariResponse)response;
            RezervareDTO[] rDTO = resp.getRezervari();
            Rezervare[] rezervari = new Rezervare[rDTO.length];

            for(int i = 0; i < rDTO.length; ++i) {
                rezervari[i] = new Rezervare("", idCursa, rDTO[i].getNume(), rDTO[i].getNrLoc());
            }
            return rezervari;
        }
    }

    public String getCursaId(CursaDTO cursa) throws AppException {
        this.sendRequest(new GetCursaIdRequest(cursa));
        Response response = this.readResponse();
        if (response instanceof ErrorResponse) {
            ErrorResponse err = (ErrorResponse)response;
            throw new AppException(err.getMessage());
        } else {
            GetCursaIdResponse resp = (GetCursaIdResponse)response;
            return resp.getId();
        }
    }

    private void closeConnection() {
        this.finished = true;

        try {
            this.input.close();
            this.output.close();
            this.connection.close();
            this.client=null;
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    private void sendRequest(Request request) throws AppException {
        try {
            this.output.writeObject(request);
            this.output.flush();
        } catch (IOException var3) {
            throw new AppException("Error sending object " + var3);
        }
    }

    private Response readResponse() throws AppException {
        Response response = null;

        try {
            response = this.qresponses.take();
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

        return response;
    }

    private void initializeConnection() throws AppException {
        try {
            this.connection = new Socket(this.host, this.port);
            this.output = new ObjectOutputStream(this.connection.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(this.connection.getInputStream());
            this.finished = false;
            this.startReader();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    private void startReader() {
        Thread tw = new Thread(new ServerProxy.ReaderThread());
        tw.start();
    }

    private void handleUpdate(UpdateResponse update) {
        NewRezervareResponse frUpd = (NewRezervareResponse) update;
        System.out.println("Reservation added " + frUpd.getRezervare());

        try {
            this.client.rezervareAdded(frUpd.getRezervare());
        } catch (AppException var7) {
            var7.printStackTrace();
        }
    }

    private class ReaderThread implements Runnable {
        private ReaderThread() {
        }

        public void run() {
            while(!ServerProxy.this.finished) {
                try {
                    Object response = ServerProxy.this.input.readObject();
                    System.out.println("response received " + response);
                    if (response instanceof UpdateResponse) {
                        ServerProxy.this.handleUpdate((UpdateResponse)response);
                    } else {
                        try {
                            ServerProxy.this.qresponses.put((Response)response);
                        } catch (InterruptedException var3) {
                            var3.printStackTrace();
                        }
                    }
                } catch (IOException var4) {
                    System.out.println("Reading error " + var4);
                } catch (ClassNotFoundException var5) {
                    System.out.println("Reading error " + var5);
                }
            }

        }
    }
}


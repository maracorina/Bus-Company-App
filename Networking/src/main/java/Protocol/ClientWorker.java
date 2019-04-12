package Protocol;

import DTO.RezervareDTO;
import DTO.UserDTO;
import Domain.*;
import Utils.AppException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientWorker implements Runnable, IAppObserver{
    private IServer server;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ClientWorker(IServer server, Socket connection) {
        this.server = server;
        this.connection = connection;

        try {
            this.output = new ObjectOutputStream(connection.getOutputStream());
            this.output.flush();
            this.input = new ObjectInputStream(connection.getInputStream());
            this.connected = true;
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }


    public void run() {
        while(this.connected) {
            try {
                Object request = this.input.readObject();
                Object response = this.handleRequest((Request)request);
                if (response != null) {
                    this.sendResponse((Response)response);
                }
            } catch (IOException var4) {
                var4.printStackTrace();
            } catch (ClassNotFoundException var5) {
                var5.printStackTrace();
            }

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var3) {
                var3.printStackTrace();
            }
        }

        try {
            this.input.close();
            this.output.close();
            this.connection.close();
        } catch (IOException var6) {
            System.out.println("Error " + var6);
        }

    }


    private Response handleRequest(Request request) {
        Response response = null;
        UserDTO user;
        if (request instanceof LogInRequest) {
            System.out.println("Login request ...");
            LogInRequest logReq = (LogInRequest)request;
            user = logReq.getUser();

            try {
                this.server.login(new Angajat("", user.getUsername(), user.getPassword()), this);
                return new OkResponse();
            } catch (Exception var8) {
                this.connected = false;
                return new ErrorResponse(var8.getMessage());
            }

        } else if (request instanceof LogOutRequest) {
            System.out.println("Logout request");
            try {
                this.server.logout(((LogOutRequest) request).getUser());
                this.connected = false;
                return new OkResponse();
            } catch (Exception var9) {
                return new ErrorResponse(var9.getMessage());
            }
        }else if (request instanceof GetCurseRequest) {
            System.out.println("GetCurse Request ...");
            GetCurseRequest getReq = (GetCurseRequest)request;

            try {
                Cursa[] curse = this.server.getCurse();
                return new GetCurseResponse(curse);
            } catch (Exception var11) {
                return new ErrorResponse(var11.getMessage());
            }
        }else if (request instanceof GetRezervariRequest) {
            System.out.println("GetRezervari Request ...");
            GetRezervariRequest getReq = (GetRezervariRequest)request;

            try {
                Rezervare[] rezervari = this.server.getRezervari(getReq.getCursaId());
                RezervareDTO[] rDTO = new RezervareDTO[rezervari.length];

                for(int i = 0; i < rezervari.length; ++i) {
                    rDTO[i] = new RezervareDTO(getReq.getCursaId(), rezervari[i].getNrLocuri(), rezervari[i].getNume());
                }
                return new GetRezervariResponse(rDTO);
            } catch (AppException var11) {
                return new ErrorResponse(var11.getMessage());
            }
        }else if (request instanceof GetCursaIdRequest) {
            System.out.println("GetCursaId Request ...");
            GetCursaIdRequest getReq = (GetCursaIdRequest)request;

            try {
                String cursa = this.server.getCursaId(getReq.getCursa());
                return new GetCursaIdResponse(cursa);
            } catch (Exception var11) {
                return new ErrorResponse(var11.getMessage());
            }
        } else if (request instanceof AddRezervareRequest) {
            System.out.println("AddRezervare request ...");
            AddRezervareRequest logReq = (AddRezervareRequest)request;
            RezervareDTO rez=logReq.getRezervare();

            try {
                this.server.addRezervare(new Rezervare("", rez.getIdCursa(), rez.getNume(), rez.getNrLoc()));
                return new OkResponse();
            } catch (Exception var8) {
                return new ErrorResponse(var8.getMessage());
            }

        } else {
            return (Response)response;
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("sending response " + response);
        this.output.writeObject(response);
        this.output.flush();
    }

    @Override
    public void rezervareAdded(Rezervare rezervare) throws AppException{
        System.out.println("Rezervare added " + rezervare);

        try {
            this.sendResponse(new NewRezervareResponse(rezervare));
        } catch (IOException var4) {
            throw new AppException(var4.getMessage());
        }
    }
}

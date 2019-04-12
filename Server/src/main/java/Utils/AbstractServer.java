package Utils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;

    public AbstractServer(int port) {
        this.port = port;
    }

    public void start() throws ServerException {
        try {
            this.server = new ServerSocket(this.port);

            while(true) {
                System.out.println("Waiting for clients ...");
                Socket client = this.server.accept();
                System.out.println("Client connected ...");
                this.processRequest(client);
            }
        } catch (IOException var8) {
            throw new ServerException("Starting server errror ", var8);
        } finally {
            try {
                this.server.close();
            } catch (IOException var7) {
                throw new ServerException("Closing server error ", var7);
            }
        }
    }

    protected abstract void processRequest(Socket var1);

    public void stop() throws ServerException {
        try {
            this.server.close();
        } catch (IOException var2) {
            throw new ServerException("Closing server error ", var2);
        }
    }
}

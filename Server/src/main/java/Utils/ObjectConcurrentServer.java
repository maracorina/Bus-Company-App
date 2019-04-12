package Utils;

import Protocol.ClientWorker;
import Protocol.IServer;

import java.net.Socket;

public class ObjectConcurrentServer extends AbstractConcurrentServer {
    private IServer chatServer;

    public ObjectConcurrentServer(int port, IServer chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatObjectConcurrentServer");
    }

    protected Thread createWorker(Socket client) {
        ClientWorker worker = new ClientWorker(this.chatServer, client);
        Thread tw = new Thread(worker);
        return tw;
    }
}

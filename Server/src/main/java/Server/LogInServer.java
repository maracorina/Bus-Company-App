package Server;

import Repository.DBRepositoryAngajati;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class LogInServer extends ConcurrentServer{
    protected DBRepositoryAngajati bazaA;
    public LogInServer(int port) {
        super(port);

        Properties serverProps=new Properties();
        try {
            //String file=Main.class.getClassLoader().getResource("bd.properties").getFile();
            serverProps.load(new FileInputStream("bd.properties"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot find bd.config "+e);
        }

        bazaA=new DBRepositoryAngajati(serverProps);
    }

    protected Thread createWorker(Socket client){
        Worker worker=new Worker(client);
        Thread tw=new Thread(worker);
        return tw;
    }





    class Worker implements Runnable {
        private Socket client;
        Worker(Socket client) {
            this.client=client;
        }


        public void run() {
            System.out.println("Starting to process request ...");
            try(BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer=new PrintWriter(client.getOutputStream())) {

                String[] line=br.readLine().split(" ");
                String result;
                if(LogInServer.this.bazaA.findByUsernameAndPassword(line[0], line[1])==null)
                    result="false";
                else
                    result="true";
                //read message from client

                //send result back to client

                writer.println(result);
                writer.flush();

                System.out.println("Finished  processing request ...");
            } catch (IOException e) {
                System.out.println("Error in processing client request "+e);
            }finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

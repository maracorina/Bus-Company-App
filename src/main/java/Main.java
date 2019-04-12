import Controller.CurseController;
import Controller.LogInController;
import Controller.MainController;
import Controller.RezervariController;
import Repository.DBRepositoryAngajati;
import Repository.DBRepositoryCurse;
import Repository.DBRepositoryRezervari;
import Service.ServiceAngajati;
import Service.ServiceCurse;
import Service.ServiceRezervari;
import Views.CurseView;
import Views.LogInView;
import Views.RezervariView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Main extends Application {
    private static final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Properties serverProps=new Properties();
//        try {
//            //String file=Main.class.getClassLoader().getResource("bd.properties").getFile();
//            serverProps.load(new FileInputStream("bd.properties"));
//            //System.setProperties(serverProps);
//
//            System.out.println("Properties set. ");
//            //System.getProperties().list(System.out);
//            serverProps.list(System.out);
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("Cannot find bd.config "+e);
//        }
//
//        DBRepositoryCurse bazaC=new DBRepositoryCurse(serverProps);
//
//        DBRepositoryRezervari bazaR=new DBRepositoryRezervari(serverProps);
//
//        DBRepositoryAngajati bazaA=new DBRepositoryAngajati(serverProps);
//
//
//        ServiceAngajati srv3=new ServiceAngajati(bazaA);
//        LogInController ctrl3=new LogInController(srv3);
//        LogInView view3=new LogInView(ctrl3);
//        ctrl3.setView(view3);
//
//        ServiceCurse srv2=new ServiceCurse(bazaC);
//        CurseController ctrl2=new CurseController(srv2);
//        CurseView view2=new CurseView(ctrl2);
//        ctrl2.setView(view2);
//        view2.setController(ctrl2);
//
//        ServiceRezervari srv1=new ServiceRezervari(bazaR);
//        RezervariController ctrl1=new RezervariController(srv1);
//        RezervariView view1=new RezervariView(ctrl1);
//        ctrl1.setView(view1);
//
//        MainController control=new MainController(ctrl3, ctrl2, ctrl1, primaryStage);
//
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

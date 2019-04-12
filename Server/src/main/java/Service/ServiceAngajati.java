package Service;

import Repository.DBRepositoryAngajati;
import Server.LogInServer;
import Services.IServiceAngajati;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceAngajati implements IServiceAngajati {
    DBRepositoryAngajati repo;

    public ServiceAngajati(DBRepositoryAngajati repo) {
        this.repo = repo;
    }

    public boolean validLogger(String username, String password){

        if(repo.findByUsernameAndPassword(username, password)==null)
            return false;
        else
            return true;
    }
}

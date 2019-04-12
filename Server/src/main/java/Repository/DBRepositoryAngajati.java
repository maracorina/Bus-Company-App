package Repository;

import Domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBRepositoryAngajati implements IRepositoryAngajati {
    private JDBCUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public DBRepositoryAngajati(Properties props){
        logger.info("Initializing SortingTaskRepository with properties: {} ",props);
        dbUtils=new JDBCUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Angajati")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Angajat entity) {
        logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Angajati values (?,?,?)")){
            preStmt.setString(1,entity.getID());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(String id) {
        logger.traceEntry("deleting task with {}",id);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Angajati where ID=?")){
            preStmt.setString(1,id);
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Angajat entity) {
        logger.traceEntry("deleting task with {}",entity.getID());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Angajati set Username=?,Password=? where ID=?")){
            preStmt.setString(1, entity.getUsername());
            preStmt.setString(2, entity.getPassword());
            preStmt.setString(3,entity.getID());
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Angajat findOne(String id) {
        logger.traceEntry("finding task with id {} ",id);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati where ID=?")){
            preStmt.setString(1,id);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String idd = result.getString("ID");
                    String username = result.getString("Username");
                    String password=result.getString("Password");

                    Angajat a=new Angajat(idd, username, password);
                    logger.traceExit(a);
                    return a;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", id);

        return null;
    }

    public Angajat findByUsernameAndPassword(String username, String password) {
        logger.traceEntry("finding task with username " + username + "and password" +password);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati where Username=? and Password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,password);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String id = result.getString("ID");
                    Angajat a=new Angajat(id, username, password);
                    logger.traceExit(a);
                    return a;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with username " + username + "and password" +password);

        return null;
    }

    @Override
    public Iterable<Angajat> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Angajat> st=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Angajati")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String idd = result.getString("ID");
                    String username = result.getString("Username");
                    String password=result.getString("Password");

                    Angajat a=new Angajat(idd, username, password);
                    st.add(a);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(st);
        return st;
    }



}

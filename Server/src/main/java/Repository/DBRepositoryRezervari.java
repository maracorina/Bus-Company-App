package Repository;


import Domain.Rezervare;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBRepositoryRezervari implements IRepositoryRezervari {
    private JDBCUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public DBRepositoryRezervari(Properties props){
        logger.info("Initializing SortingTaskRepository with properties: {} ",props);
        dbUtils=new JDBCUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Rezervari")) {
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
    public void save(Rezervare entity) {
        logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Rezervari values (?,?,?,?)")){
            preStmt.setString(1,entity.getID());
            preStmt.setString(2,entity.getIdCursa());
            preStmt.setString(3,entity.getNume());
            preStmt.setInt(4,entity.getNrLocuri());
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
        try(PreparedStatement preStmt=con.prepareStatement("delete from Rezervari where ID=?")){
            preStmt.setString(1,id);
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Rezervare entity) {
        logger.traceEntry("deleting task with {}",entity.getID());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Rezervari set IDCursa=?,Nume=?,NrLocuri=? where ID=?")){
            preStmt.setString(1, entity.getIdCursa());
            preStmt.setString(2, entity.getNume());
            preStmt.setInt(3,entity.getNrLocuri());
            preStmt.setString(4,entity.getID());
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Rezervare findOne(String id) {
        logger.traceEntry("finding task with id {} ",id);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Rezervari where ID=?")){
            preStmt.setString(1,id);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String idd = result.getString("ID");
                    String idCursa = result.getString("IDCursa");
                    String nume=result.getString("Nume");
                    int nrLocuri=result.getInt("NrLocuri");

                    Rezervare r=new Rezervare(idd, idCursa, nume, nrLocuri);
                    logger.traceExit(r);
                    return r;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", id);

        return null;
    }

    @Override
    public Iterable<Rezervare> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Rezervare> st=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Rezervari")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String idd = result.getString("ID");
                    String idCursa = result.getString("IDCursa");
                    String nume=result.getString("Nume");
                    int nrLocuri=result.getInt("NrLocuri");

                    Rezervare r=new Rezervare(idd, idCursa, nume, nrLocuri);
                    st.add(r);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(st);
        return st;
    }


    public Iterable<Rezervare> findByCursa(String idCursa) {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Rezervare> st=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Rezervari where IDCursa=?")) {
            preStmt.setString(1,idCursa);
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    String idd = result.getString("ID");
                    String nume=result.getString("Nume");
                    int nrLocuri=result.getInt("NrLocuri");

                    Rezervare r=new Rezervare(idd, idCursa, nume, nrLocuri);
                    st.add(r);
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

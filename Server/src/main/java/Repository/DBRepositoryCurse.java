package Repository;

import Domain.Cursa;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBRepositoryCurse implements IRepositoryCurse {
    private JDBCUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public DBRepositoryCurse(Properties props){
        logger.info("Initializing SortingTaskRepository with properties: {} ",props);
        dbUtils=new JDBCUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Curse")) {
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
    public void save(Cursa entity) {
        logger.traceEntry("saving task {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Curse values (?,?,?,?)")){
            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            preStmt.setString(1,entity.getID());
            preStmt.setString(2,entity.getDestinatia());
            preStmt.setString(3,entity.getData().format(formatter));
            preStmt.setInt(4,entity.getLocuriDisponibile());
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
        try(PreparedStatement preStmt=con.prepareStatement("delete from Curse where ID=?")){
            preStmt.setString(1,id);
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Cursa entity) {
        logger.traceEntry("deleting task with {}",entity.getID());
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Curse set Destinatia=?,Data=?,LocuriDisponibile=? where ID=?")){
            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            preStmt.setString(1, entity.getDestinatia());
            preStmt.setString(2, entity.getData().format(formatter));
            preStmt.setInt(3,entity.getLocuriDisponibile());
            preStmt.setString(4,entity.getID());
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Cursa findOne(String id) {
        logger.traceEntry("finding task with id {} ",id);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Curse where ID=?")){
            preStmt.setString(1,id);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String idd = result.getString("ID");
                    String destinatia = result.getString("Destinatia");
                    LocalDateTime data= LocalDateTime.parse(result.getString("Data"), formatter);
                    int locuriDisponibile=result.getInt("LocuriDisponibile");

                    Cursa c=new Cursa(idd, destinatia, data, locuriDisponibile);
                    logger.traceExit(c);
                    return c;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No task found with id {}", id);

        return null;
    }


    public Cursa findByDestAndData(String destinatie, String data) {
        logger.traceEntry("finding cursa with destinatia {} ",destinatie + "si data" + data);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Curse where Destinatia=? and Data=?")){
            preStmt.setString(1, destinatie);
            preStmt.setString(2, data);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String id = result.getString("ID");
                    int locuriDisponibile=result.getInt("LocuriDisponibile");

                    Cursa c=new Cursa(id, destinatie, LocalDateTime.parse(data, formatter), locuriDisponibile);
                    logger.traceExit(c);
                    return c;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No cursa found with destinatia {} ",destinatie + "si data" + data);

        return null;
    }

    @Override
    public Iterable<Cursa> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Cursa> st=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Curse")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String idd = result.getString("ID");
                    String destinatia = result.getString("Destinatia");
                    LocalDateTime data= LocalDateTime.parse(result.getString("Data"), formatter);
                    int locuriDisponibile=result.getInt("LocuriDisponibile");

                    Cursa c=new Cursa(idd, destinatia, data, locuriDisponibile);
                    st.add(c);
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

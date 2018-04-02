package repository;

import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TicketRepository implements Repository<Integer, Ticket> {
    private JdbcUtils dbUtils;

    public TicketRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void save(Ticket entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Tickets(idConcert,buyer) values (?,?)")) {
            preStmt.setString(1, Integer.toString(entity.getIdConcert()));
            preStmt.setString(2, entity.getBuyer());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

    }

    @Override
    public void delete(Ticket Ticket) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Tickets where id=?")) {
            preStmt.setInt(1, Ticket.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        if (size() == 0) {
            try (PreparedStatement preStmt = con.prepareStatement("ALTER TABLE Tickets AUTO_INCREMENT = 1")) {
                int result = preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        }
    }

    public void put(Ticket entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Tickets set placesBought=? where id=?")) {
            preStmt.setString(1, Integer.toString(entity.getPlacesBought()));
            preStmt.setInt(2, entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Iterable<Ticket> getAll() {
        Connection con = dbUtils.getConnection();
        List<Ticket> Tickets = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Tickets ")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int idConcert = result.getInt("idConcert");
                    int placesBought = result.getInt("placesBought");
                    String buyer = result.getString("buyer");
                    Ticket Ticket = new Ticket(id, idConcert, placesBought, buyer);
                    Tickets.add(Ticket);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Tickets;

    }

    @Override
    public Ticket findById(Integer integer) {
        Connection con = dbUtils.getConnection();
        Ticket ticket = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Tickets where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int idConcert = result.getInt("idConcert");
                    int placesBought = result.getInt("placesBought");
                    String buyer = result.getString("buyer");
                    ticket = new Ticket(id, idConcert, placesBought, buyer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    @Override
    public int size() {
        Connection con = dbUtils.getConnection();
        int size = 0;
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) from Tickets ")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    size += result.getInt(1);
                    ;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return size;
    }

}

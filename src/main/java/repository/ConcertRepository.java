package repository;

import model.Artist;
import model.Concert;
import model.Concert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConcertRepository implements Repository<Integer, Concert> {
    private JdbcUtils dbUtils;

    public ConcertRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void save(Concert entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into " +
                "Concerts(idArtist,date,idLocation,numberOfTickets,soldTickets) values (?,?,?,?,?)")) {
            preStmt.setString(1, Integer.toString(entity.getIdArtist()));
            preStmt.setString(2, entity.getDate());
            preStmt.setString(3, Integer.toString(entity.getIdLocation()));
            preStmt.setString(4, Integer.toString(entity.getNumberOfTickets()));
            preStmt.setString(5, Integer.toString(entity.getSoldTickets()));
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

    }

    @Override
    public void delete(Concert Concert) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Concerts where id=?")) {
            preStmt.setInt(1, Concert.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        if (size() == 0) {
            try (PreparedStatement preStmt = con.prepareStatement("ALTER TABLE Concerts AUTO_INCREMENT = 1")) {
                int result = preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        }
    }

    public void put(Concert entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Concerts set soldTickets=? where id=?")) {
            preStmt.setString(1, Integer.toString(entity.getSoldTickets()));
            preStmt.setInt(2, entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Iterable<Concert> getAll() {
        Connection con = dbUtils.getConnection();
        List<Concert> Concerts = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Concerts")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int idArtist = result.getInt("idArtist");
                    String date = result.getString("date");
                    int idLocation = result.getInt("idLocation");
                    int numberOfTickets = result.getInt("numberOfTickets");
                    int soldTickets = result.getInt("soldTickets");
                    Concert Concert = new Concert(id, idArtist, date, idLocation, numberOfTickets, soldTickets);
                    Concerts.add(Concert);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Concerts;

    }

    @Override
    public Concert findById(Integer integer) {
        Connection con = dbUtils.getConnection();
        Concert concert = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Concerts where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int idArtist = result.getInt("idArtist");
                    String date = result.getString("date");
                    int idLocation = result.getInt("idLocation");
                    int numberOfTickets = result.getInt("numberOfTickets");
                    int soldTickets = result.getInt("soldTickets");
                    concert = new Concert(id, idArtist, date, idLocation, numberOfTickets, soldTickets);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return concert;
    }

    @Override
    public int size() {
        Connection con = dbUtils.getConnection();
        int size = 0;
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) from Concerts")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    size += result.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return size;
    }

    public ArrayList<Concert> getConcertsByArtist(int idArtist) {
        Connection con = dbUtils.getConnection();
        ArrayList<Concert> Concerts = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Concerts where idArtist=?")) {
            preStmt.setInt(1, idArtist);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String date = result.getString("date");
                    int idLocation = result.getInt("idLocation");
                    int numberOfTickets = result.getInt("numberOfTickets");
                    int soldTickets = result.getInt("soldTickets");
                    Concert Concert = new Concert(id, idArtist, date, idLocation, numberOfTickets, soldTickets);
                    Concerts.add(Concert);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Concerts;
    }

    public ArrayList<Concert> getConcertsByDate(String date) {
        Connection con = dbUtils.getConnection();
        ArrayList<Concert> Concerts = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Concerts where date=?")) {
            preStmt.setString(1, date);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int idArtist = result.getInt("idArtist");
                    int idLocation = result.getInt("idLocation");
                    int numberOfTickets = result.getInt("numberOfTickets");
                    int soldTickets = result.getInt("soldTickets");
                    Concert Concert = new Concert(id, idArtist, date, idLocation, numberOfTickets, soldTickets);
                    Concerts.add(Concert);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Concerts;
    }

    public ArrayList<Concert> getConcertsByArtistAndDate(int idArtist, String date) {
        Connection con = dbUtils.getConnection();
        ArrayList<Concert> Concerts = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Concerts where date=? and idArtist=?")) {
            preStmt.setString(1, date);
            preStmt.setInt(2, idArtist);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int idLocation = result.getInt("idLocation");
                    int numberOfTickets = result.getInt("numberOfTickets");
                    int soldTickets = result.getInt("soldTickets");
                    Concert Concert = new Concert(id, idArtist, date, idLocation, numberOfTickets, soldTickets);
                    Concerts.add(Concert);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Concerts;
    }
}

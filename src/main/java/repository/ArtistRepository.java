package repository;

import model.Artist;
import model.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ArtistRepository implements Repository<Integer, Artist> {
    private JdbcUtils dbUtils;

    public ArtistRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void save(Artist entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Artists(lastName,firstName) values (?,?)")) {
            preStmt.setString(1, entity.getLastName());
            preStmt.setString(2, entity.getFirstName());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

    }

    @Override
    public void delete(Artist Artist) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Artists where id=?")) {
            preStmt.setInt(1, Artist.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        if (size() == 0) {
            try (PreparedStatement preStmt = con.prepareStatement("ALTER TABLE Artists AUTO_INCREMENT = 1")) {
                int result = preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        }
    }

    public void put(Artist entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Artists set lastName=? where id=?")) {
            preStmt.setString(1, entity.getLastName());
            preStmt.setInt(2, entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Iterable<Artist> getAll() {
        Connection con = dbUtils.getConnection();
        List<Artist> Artists = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Artists")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String lastName = result.getString("lastname");
                    String firstName = result.getString("firstname");
                    Artist Artist = new Artist(id, lastName, firstName);
                    Artists.add(Artist);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Artists;

    }

    @Override
    public Artist findById(Integer integer) {
        Connection con = dbUtils.getConnection();
        Artist artist = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Artists  where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String lastName = result.getString("lastname");
                    String firstName = result.getString("firstname");
                    artist = new Artist(id, lastName, firstName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artist;
    }

    @Override
    public int size() {
        Connection con = dbUtils.getConnection();
        int size = 0;
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) from Artists")) {
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
}

package repository;

import model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LocationRepository implements Repository<Integer, Location> {
    private JdbcUtils dbUtils;

    public LocationRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void save(Location entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Locations(name,places) values (?,?)")) {
            preStmt.setString(1, entity.getName());
            preStmt.setString(2, Integer.toString(entity.getPlaces()));
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

    }

    @Override
    public void delete(Location Location) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Locations where id=?")) {
            preStmt.setInt(1, Location.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        if (size() == 0) {
            try (PreparedStatement preStmt = con.prepareStatement("ALTER TABLE Locations AUTO_INCREMENT = 1")) {
                int result = preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        }
    }

    public void put(Location entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Locations set name=? places=? where id=?")) {
            preStmt.setString(1, entity.getName());
            preStmt.setString(2, Integer.toString(entity.getPlaces()));
            preStmt.setInt(3, entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Iterable<Location> getAll() {
        Connection con = dbUtils.getConnection();
        List<Location> locations = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Locations ")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int places = result.getInt("places");
                    Location Location = new Location(id, name, places);
                    locations.add(Location);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return locations;

    }

    @Override
    public Location findById(Integer integer) {
        Connection con = dbUtils.getConnection();
        Location location = null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Locations  where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int places = result.getInt("places");
                    location = new Location(id, name, places);;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public int size() {
        Connection con = dbUtils.getConnection();
        int size = 0;
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) from Locations")) {
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

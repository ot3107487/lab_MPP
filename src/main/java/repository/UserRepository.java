package repository;

import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository implements Repository<Integer, User> {
    private JdbcUtils dbUtils;

    public UserRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void save(User entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Users(lastName,firstName,userName,password,idRole) values (?,?,?,?,?)")) {
            preStmt.setString(1, entity.getLastName());
            preStmt.setString(2, entity.getFirstName());
            preStmt.setString(3, entity.getUserName());
            preStmt.setString(4, entity.getPassword());
            preStmt.setString(5, Integer.toString(entity.getIdRole()));
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

    }

    @Override
    public void delete(User User) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Users where id=?")) {
            preStmt.setInt(1, User.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        if (size() == 0) {
            try (PreparedStatement preStmt = con.prepareStatement("ALTER TABLE Users AUTO_INCREMENT = 1")) {
                int result = preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        }
    }

    public void put(User entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Users set password=? where id=?")) {
            preStmt.setString(1, entity.getPassword());
            preStmt.setInt(2, entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Iterable<User> getAll() {
        Connection con = dbUtils.getConnection();
        List<User> Users = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Users")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String lastName = result.getString("lastname");
                    String firstName = result.getString("firstname");
                    String userName = result.getString("username");
                    String password = result.getString("password");
                    int idRole = result.getInt("idRole");
                    User User = new User(id, lastName, firstName, userName, password, idRole);
                    Users.add(User);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Users;

    }

    @Override
    public User findById(Integer integer) {
        Connection con = dbUtils.getConnection();
        User user=null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from  Users where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String lastName = result.getString("lastname");
                    String firstName = result.getString("firstname");
                    String userName = result.getString("username");
                    String password = result.getString("password");
                    int idRole = result.getInt("idRole");
                    user = new User(id, lastName, firstName, userName, password, idRole);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return user;
    }

    @Override
    public int size() {
        Connection con = dbUtils.getConnection();
        int size = 0;
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) from Users")) {
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

    public boolean login(String userName,String password){
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("select * from  Users where username=? and password=?")) {
            preStmt.setString(1, userName);
            preStmt.setString(2, password);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return false;
    }
}

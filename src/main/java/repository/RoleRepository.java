package repository;

import model.Role;
import model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RoleRepository implements Repository<Integer,Role> {
    private JdbcUtils dbUtils;

    public RoleRepository(Properties props) {
        dbUtils = new JdbcUtils(props);
    }

    @Override
    public void save(Role entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("insert into Roles(name) values (?)")) {
            preStmt.setString(1, entity.getName());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }

    }

    @Override
    public void delete(Role Role) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("delete from Roles where id=?")) {
            preStmt.setInt(1, Role.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        if(size()==0){
            try (PreparedStatement preStmt = con.prepareStatement("ALTER TABLE Roles AUTO_INCREMENT = 1")) {
                int result = preStmt.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error DB " + ex);
            }
        }
    }

    public void put(Role entity) {
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("update Roles set name=? where id=?")) {
            preStmt.setString(1, entity.getName());
            preStmt.setInt(2, entity.getId());
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Iterable<Role> getAll() {
        Connection con = dbUtils.getConnection();
        List<Role> Roles = new ArrayList<>();
        try (PreparedStatement preStmt = con.prepareStatement("select * from Roles ")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name=result.getString("name");
                    Role Role =new Role(id,name);
                    Roles.add(Role);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Roles;

    }

    @Override
    public Role findById(Integer integer) {
        Connection con = dbUtils.getConnection();
        Role role=null;
        try (PreparedStatement preStmt = con.prepareStatement("select * from Roles where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name=result.getString("name");
                    role =new Role(id,name);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return role;
    }

    @Override
    public int size() {
        Connection con=dbUtils.getConnection();
        int size=0;
        try (PreparedStatement preStmt = con.prepareStatement("select count(*) from Roles")) {
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    size+=result.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return size;
    }
}

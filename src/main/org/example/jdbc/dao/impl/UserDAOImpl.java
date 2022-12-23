package org.example.jdbc.dao.impl;

import org.example.jdbc.dao.UsersDAO;
import org.example.model.Office;
import org.example.model.Role;
import org.example.model.User;
import org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class UserDAOImpl implements UsersDAO {

    private OfficesDAOImpl officesDAO = new OfficesDAOImpl();
    private RolesDAOImpl rolesDAO = new RolesDAOImpl();

    @Override
    public boolean createUser(User user) {
        String sql = "INSERT INTO test_project.users (email, pwd, name, details) VALUES ('"
                +user.getEmail()+"', '"+user.getPwd()+"', '"+user.getName()+"', '"+ user.getDetails()+"')";

        Connection conn = DBUtils.getConnection();
        try {
            Statement stmt = conn.createStatement();
            int rowCount = stmt.executeUpdate(sql);
            if(rowCount == 1){
                System.out.println("User has been created with email "+ user.getEmail() );
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.closeConnection(conn);
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {

        char is_active_char = user.isActive() ? 'Y' : 'N';

        final String update = "UPDATE user SET email = '"
                +user.getEmail()+"', pwd = '"+ user.getPwd()+"', name = '"+user.getName()+"', office_id = '"
                +user.getOffice().getId()+"', details = '"+user.getDetails()+"', is_active = '"+is_active_char+"'  WHERE user.id = "+ user.getId();
        try( Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            if(stmt.executeUpdate(update) > 0){
                System.out.println("User with ID " + user.getId() + " has been update successfully");
                return true;
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUserById(int id) {
        return false;
    }

    @Override
    public User getUserById(int id) {
        final String select = "SELECT * FROM test_project.users WHERE id = " + id;
        Connection conn = DBUtils.getConnection();
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            while (rs.next()){
                user = new User();
                user.setId(id);

                user.setEmail(rs.getString("email"));
                user.setPwd(rs.getString("pwd"));
                user.setName(rs.getString(4));
                user.setDetails(rs.getString(5));
                user.setActive(rs.getString(6).equals("Y"));

                // office
                user.setOffice(officesDAO.getById(new Office(rs.getInt("office_id"))));

                //roles
                user.setRoles(rolesDAO.getAllRolesByUser(user));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.closeConnection(conn);
        }


        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        final String select = "SELECT * FROM test_project.users WHERE email = '" + email + "'";
        Connection conn = DBUtils.getConnection();
        User user = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt(1));
                user.setEmail(rs.getString("email"));
                user.setPwd(rs.getString("pwd"));
                user.setName(rs.getString(4));
                user.setDetails(rs.getString(5));
                user.setActive(rs.getString(6).equals("Y"));

                user.setCreatedTs(rs.getTimestamp("CREATED_TS"));
                user.setUpdatedTs(rs.getTimestamp("LAST_UPDATED_TS"));

                // office
                user.setOffice(officesDAO.getById(new Office(rs.getInt("office_id"))));

                //roles
                user.setRoles(rolesDAO.getAllRolesByUser(user));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.closeConnection(conn);
        }

        return user;
    }

    @Override
    public Set<User> getAllUser() {
        String sql = "SELECT * FROM test_project.users";
        Connection conn = DBUtils.getConnection();
        Set<User> all = new HashSet<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setPwd(rs.getString("pwd"));
                user.setName(rs.getString(4));
                user.setDetails(rs.getString(5));
                user.setActive(rs.getString(6).equals("Y"));

                // office
                user.setOffice(officesDAO.getById(new Office(rs.getInt("office_id"))));

                //roles
                user.setRoles(rolesDAO.getAllRolesByUser(user));

                all.add(user);
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DBUtils.closeConnection(conn);
            }

            return all;
        }


    @Override
    public boolean activateUser(String email) {
        String update = "UPDATE test_project.users SET is_active = 'Y' WHERE users.email = '"+email+"'";

        Connection conn = DBUtils.getConnection();
        try {
            Statement stmt = conn.createStatement();
            int rowCount = stmt.executeUpdate(update);
            if(rowCount == 1){
                System.out.println("User has been activated with email "+ email );
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }  finally {
            DBUtils.closeConnection(conn);
        }

        return false;

    }
}

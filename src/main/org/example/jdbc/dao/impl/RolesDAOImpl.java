package org.example.jdbc.dao.impl;

import org.example.jdbc.dao.AbstactDAO;
import org.example.model.Role;
import org.example.model.User;
import org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RolesDAOImpl extends AbstactDAO<Role> {
    @Override
    public boolean create(Role role) {

        final String insert = "INSERT INTO roles (id, name, descr) VALUES ('"
                +role.getId()+"', '"+role.getName()+"', '" + role.getDescription() + "')";

        try( Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            if(stmt.executeUpdate(insert) > 0){
                System.out.println("Role with name " + role.getName() + " has been created successfully");
                return true;
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Role role) {
        final String update = "UPDATE roles SET name = '"
                +role.getName()+"', descr = '"+role.getDescription()+"' WHERE role.id = "+ role.getId();
        try( Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            if(stmt.executeUpdate(update) > 0){
                System.out.println("Role with ID " + role.getId() + " has been update successfully");
                return true;
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Role role) {
        final String delete = "DELETE FROM test_project.ROLES WHERE id = " + role.getId();

        try( Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            if(stmt.executeUpdate(delete) > 0){
                System.out.println("Role with ID " + role.getId() + " has been deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Role getById(Role role) {
        final String select = "SELECT * FROM test_project.ROLES WHERE id = " + role.getId();
        Connection conn = DBUtils.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            if (rs.next()){
                role.setName(rs.getString("name"));
                role.setDescription(rs.getString("desc"));
            } else {
                System.out.println("Role Not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.closeConnection(conn);
        }
        return role;
    }

    @Override
    public Set<Role> getAll() {
        Set<Role> allRoles = new HashSet<>();
        final String select = "SELECT R.id, R.name, R.descr  FROM roles R";

        try (Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(select)){
            while (rs.next()){
                int roleID = rs.getInt("id");
                String name = rs.getString("name");
                String descr = rs.getString("descr");
                allRoles.add(new Role(roleID, name, descr));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allRoles;

    }


    public Set<Role> getAllRolesByUser(User user){
       Set<Role> userRoles = new HashSet<>();

        final String select = "SELECT U.user_id, U.role_id, R.name, R.descr  FROM  users_roles U " +
                "INNER JOIN roles R ON U.role_id = R.id WHERE U.user_id =  " + user.getId();

        try (Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(select)){
            while (rs.next()){
              int roleID = rs.getInt("role_id");
              String name = rs.getString("name");
              String descr = rs.getString("descr");
              userRoles.add(new Role(roleID, name, descr));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       return userRoles;
    }
}

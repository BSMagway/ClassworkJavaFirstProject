package org.example.jdbc.dao.impl;

import org.example.jdbc.dao.AbstactDAO;
import org.example.model.Office;
import org.example.util.DBUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class OfficesDAOImpl extends AbstactDAO<Office> {
    @Override
    public boolean create(Office office) {

        final String insert = "INSERT INTO offices (location, phone1, phone2) VALUES ('"
                +office.getLocation()+"', '"+office.getPhone1()+"', '"+office.getPhone2()+"')";

        try( Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            if(stmt.executeUpdate(insert) > 0){
                System.out.println("Office with ID " + office.getId() + " has been created successfully");
                return true;
            }
        }  catch (SQLException e) {
           e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Office office) {

        final String update = "UPDATE offices SET location = '"
                +office.getLocation()+"', phone1 = '"+office.getPhone1()+"', phone2 = '"+office.getPhone2()+"' WHERE offices.id = "+ office.getId();
        try( Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            if(stmt.executeUpdate(update) > 0){
                System.out.println("Office with ID " + office.getId() + " has been update successfully");
                return true;
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Office office) {
        final String delete = "DELETE FROM test_project.OFFICES WHERE id = " + office.getId();

        try( Connection conn = DBUtils.getConnection();
             Statement stmt = conn.createStatement()) {
            if(stmt.executeUpdate(delete) > 0){
                System.out.println("Office with ID " + office.getId() + " has been deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Office getById(Office office) {
        final String select = "SELECT * FROM test_project.OFFICES WHERE id = " + office.getId();
        Connection conn = DBUtils.getConnection();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            if (rs.next()){
                office.setLocation(rs.getString("location"));
                office.setPhone1(rs.getString("phone1"));
                office.setPhone2(rs.getString("phone2"));
            } else {
                System.out.println("Office Not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.closeConnection(conn);
        }
        return office;
    }

    @Override
    public Set<Office> getAll() {
        final String select = "SELECT * FROM test_project.OFFICES";
        Connection conn = DBUtils.getConnection();
        Set<Office> offices = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(select);
            offices = new HashSet<>();
            while (rs.next()){
                int id = rs.getInt("id");
                String location = rs.getString("location");
                String phone1 = rs.getString("phone1");
                String phone2 = rs.getString("phone2");

                offices.add(new Office(id, location, phone1, phone2));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBUtils.closeConnection(conn);
        }
        return offices;
    }
}

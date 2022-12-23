package org.example.util;

import java.sql.*;

public class DBUtils {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/test_project";
    public static final String DB_USER = "root";
    public static final String DB_USER_PWD = "root";


    public static Connection getConnection(){
        Connection conn = null;
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_USER_PWD);
            if (!conn.isClosed()) {
                System.out.println("Connections is open");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  conn;
    }


    public static void closeConnection(Connection conn){
        try {
            if (conn != null){
                conn.close();
                System.out.println("Connections is closed");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        closeConnection(conn);
    }


    static void test(int id){
        Connection conn = getConnection();
        String sql = "SELECT * FROM offices WHERE id = " + id;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){

                String location = rs.getString("location");
                String ph1 = rs.getString("phone1");
                String ph2 = rs.getString(4);

                System.out.println(id);
                System.out.println(location);
                System.out.println(ph1);
                System.out.println(ph2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

}


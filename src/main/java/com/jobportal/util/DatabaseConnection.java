package com.jobportal.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/job_portal_db";
    private static final String user = "root";
    private static final String pass = "Ranjan@0000";

    public static Connection connection = null;

    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,pass);
        }catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
}

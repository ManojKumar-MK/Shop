package com.example.shop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String DRIVER_NAME = "com.mysql.jdbc.Driver";
    private String URL = "jdbc:mysql://localhost:3306/zomoto";
    private String USERNAME = "root";
    private String PASSWORD = "root";

    private static ConnectionFactory connectionFactory = null;
    private Connection connection = null;

    private ConnectionFactory(){
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection()
    {
        return connection;
    }


    public static ConnectionFactory getInstance() {

        if(connectionFactory==null)
            connectionFactory = new ConnectionFactory();
        return connectionFactory;


    }

}

package com.example.ordermanagement.connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * postgress connection
 */
public class ConnectionFactory {
    public static final String USER = "postgres";
    public static final String PASS = "your_password";

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String DBURL  = "jdbc:postgresql://localhost:5432/ordersmanagement";

    private static final ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * concetion
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "PostgreSQL JDBC driver not found", e);
        }
    }

    /**
     * returns a connection
     * @return
     */
    private Connection createConnection() {
        try {
            return DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error creating PostgreSQL connection", e);
            return null;
        }
    }

    /**
     * public for the connection
     * @return
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * closes a connection
     * @param conn
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try { conn.close(); }
            catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing Connection", e);
            }
        }
    }

    /**
     * closesv a statment
     * @param stmt
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try { stmt.close(); }
            catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing Statement", e);
            }
        }
    }

    /**
     * closes a resultSet
     * @param rs
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try { rs.close(); }
            catch (SQLException e) {
                LOGGER.log(Level.WARNING, "Error closing ResultSet", e);
            }
        }
    }
}

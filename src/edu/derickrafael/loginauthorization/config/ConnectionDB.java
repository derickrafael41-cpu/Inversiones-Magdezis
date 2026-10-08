package edu.derickrafael.loginauthorization.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB extends CredentialsDB {

    private static Connection conn; 

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName(CONTROLLER);
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("¡Conexión exitosa a la base de datos!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error: Falló la conexión a la base de datos.");
            e.printStackTrace();
        }
        return conn;
    }
}

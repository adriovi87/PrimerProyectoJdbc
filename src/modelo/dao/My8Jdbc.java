package modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class My8Jdbc {
    private static final String url = "jdbc:mysql://localhost:3306/proyectos_FP_2025?serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";
    private static Connection conn;

  
    private My8Jdbc() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión establecida con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar a la BD.");
        }
    }

   
    public static Connection getConexion() {
        if (conn == null) {
            new My8Jdbc(); 
        }
        return conn;
    }
}
package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;
    private String url = "jdbc:postgresql://localhost:5432/fxapp";
    private String username = "postgres";
    private String password = "postgre";

    private DatabaseConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }catch (SQLException a){
            System.out.println("SQL error: " + a.getMessage() );
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DatabaseConnection getInstance()  {
        try {
            if (instance == null) {
                instance = new DatabaseConnection();
            } else if (instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }

            return instance;
        }catch (SQLException a){
            System.out.println("SQL error: " + a.getMessage());
        }
        return null;
    }
}

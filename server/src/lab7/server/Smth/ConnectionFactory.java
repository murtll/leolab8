package lab7.server.Smth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public static Connection getConnection() throws SQLException {
        String host = System.getenv("PG_HOST");
        int port = Integer.parseInt(System.getenv("PG_PORT"));
        String user = System.getenv("PG_USER");
        String pass = System.getenv("PG_PASS");
        String db = System.getenv("PG_DB");

        String url = "jdbc:postgresql://" + host + ":" + port + "/" + db;

        return DriverManager.getConnection(url, user, pass);
    }
}

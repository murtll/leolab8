package lab7.server.Security;

import lab7.server.Db.DatabaseManager;
import lab7.server.Exceptions.InvalidCredentialsException;
import lab7.server.Network.ClientMetaDto;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AuthenticationManager {
    private final DatabaseManager databaseManager;
    private final Logger logger;

    public AuthenticationManager(DatabaseManager databaseManager, Logger logger) {
        this.databaseManager = databaseManager;
        this.logger = logger;
    }

    public ClientMetaDto authenticate(ClientMetaDto meta) throws SQLException, InvalidCredentialsException, NoSuchAlgorithmException {
        String username = meta.getUsername();
        String password = meta.getPassword();
        String hashed = Hasher.sha512(password);
        return databaseManager.checkUser(username, hashed);
    }

    public ClientMetaDto registerUser(ClientMetaDto meta) throws NoSuchAlgorithmException, SQLException {
        String username = meta.getUsername();
        String password = meta.getPassword();
        int color = meta.getColor();
        String hashed = Hasher.sha512(password);
        return databaseManager.addUser(username, hashed, color);
    }
}

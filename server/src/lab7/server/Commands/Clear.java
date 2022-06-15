package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.AbstrCommand;
import lab7.server.Smth.CollectionManager;
import lab7.server.Smth.DatabaseManager;

import java.sql.SQLException;


/**
 * Collection delete command class
 *
 * @author Nagorny Leonid
 */

public class Clear extends AbstrCommand {
    private final CollectionManager collectionManager;
    private final DatabaseManager databaseManager;

    public Clear(CollectionManager C, DatabaseManager databaseManager) {
        this.collectionManager = C;
        this.databaseManager = databaseManager;
    }

    @Override
    public ServerResponseDto execute(long userId) {
        try {
            databaseManager.removeAllVehiclesOfUser(userId);
        } catch (SQLException e) {
            return new ServerResponseDto(false, "Error executing SQL statement");
        }
        collectionManager.clear(userId);
        return new ServerResponseDto(true, "OK");
    }
}

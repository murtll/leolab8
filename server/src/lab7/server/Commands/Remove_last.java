package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.CollectionManager;
import lab7.server.Db.DatabaseManager;

import java.sql.SQLException;

/**
 * The class of the command to remove the last element of the collection
 *
 * @author Nagorny Leonid
 */

public class Remove_last extends AbstrCommand {
    private final CollectionManager cm;
    private final DatabaseManager databaseManager;

    public Remove_last(CollectionManager cm, DatabaseManager databaseManager) {
        this.cm = cm;
        this.databaseManager = databaseManager;
    }

    public ServerResponseDto execute(long userId) {
        long lastId = cm.getLastId(userId);
        if (lastId == -1) {
            return new ServerResponseDto(false, "Id is not present");
        }

        try {
            databaseManager.removeVehicleById(lastId);
        } catch (SQLException e) {
            return new ServerResponseDto(false, "Error executing SQL statement");
        }

        cm.remove_by_id(lastId, userId);
        return new ServerResponseDto(true, "OK");
    }
}


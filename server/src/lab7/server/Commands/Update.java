package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.*;

import java.sql.SQLException;

/**
 * Update command class id
 *
 * @author Nagorny Leonid
 */

public class Update extends AbstrCommand {
    private final InputChecker ic;
    private final CollectionManager cm;
    private final DatabaseManager databaseManager;

    public Update(CollectionManager cm, InputChecker ic, DatabaseManager databaseManager) {
        this.cm = cm;
        this.ic = ic;
        this.databaseManager = databaseManager;
    }

    public ServerResponseDto execute(String argument, Vehicle v, long userId) {
        if (ic.longValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
            long id = Long.parseLong(argument);

            try {
                databaseManager.updateVehicleById(id, v);
            } catch (SQLException e) {
                return new ServerResponseDto(false, "Error executing SQL statement");
            }

            cm.updateById(id, v, userId);

            return new ServerResponseDto(true, "OK");
        }
        return new ServerResponseDto(true, "The inserting ID is not in valid range! Please insert Id greater than 0!");
    }

}

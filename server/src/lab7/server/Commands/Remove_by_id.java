package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.CollectionManager;
import lab7.server.Db.DatabaseManager;
import lab7.server.Smth.InputChecker;

import java.sql.SQLException;

/**
 * Command class responsible for deleting a collection element by id
 *
 * @author Nagorny Leonid
 */

public class Remove_by_id extends AbstrCommand {
    private final CollectionManager cm;
    private final InputChecker ic;

    private final DatabaseManager databaseManager;

    public Remove_by_id(CollectionManager cm, InputChecker ic, DatabaseManager databaseManager) {
        this.cm = cm;
        this.ic = ic;
        this.databaseManager = databaseManager;
    }

    @Override
    public ServerResponseDto execute(String argument, long userId) {
        if (ic.longValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
            long id = Long.parseLong(argument);

            try {
                databaseManager.removeVehicleById(id);
            } catch (SQLException e) {
                return new ServerResponseDto(false, "Error executing SQL statement");
            }

            if (!cm.remove_by_id(id, userId)) {
                return new ServerResponseDto(false, "Такого id не существует.Пожалуйста введите существующий id!");
            }
            return new ServerResponseDto(true, "OK");
        }
        return new ServerResponseDto(true, "Введенный id имеет неправильный формат!Введеный id должен содеражать значение больше 0!");
    }

}

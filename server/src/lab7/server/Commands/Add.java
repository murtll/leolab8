package lab7.server.Commands;

import lab7.server.Exceptions.EmptyResultSetException;
import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.CollectionManager;


import lab7.server.Db.DatabaseManager;
import lab7.server.Entity.Vehicle;

import java.sql.SQLException;

/**
 * Add element command class
 *
 * @author Nagorny Leonid
 */

public class Add extends AbstrCommand {
    private final CollectionManager cm;
    private final DatabaseManager dm;

    public Add(CollectionManager cm, DatabaseManager dm) {
        this.cm = cm;
        this.dm = dm;
    }

    public ServerResponseDto execute(Vehicle v, long userId) {
        try {
            Vehicle readyVehicle = dm.addVehicle(v, userId);
            cm.add(readyVehicle, userId);
        } catch (SQLException | EmptyResultSetException e) {
            return new ServerResponseDto(false, "Error executing SQL statement");
        }
        return new ServerResponseDto(true, "OK");
    }
}

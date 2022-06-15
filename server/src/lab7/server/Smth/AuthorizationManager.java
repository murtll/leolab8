package lab7.server.Smth;

import lab7.server.Network.ClientDataDto;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class AuthorizationManager {

    private final DatabaseManager databaseManager;

    private final Logger logger;

    public AuthorizationManager(DatabaseManager databaseManager, Logger logger) {
        this.databaseManager = databaseManager;
        this.logger = logger;
    }

    public boolean isAuthorized(long userId, ClientDataDto data) throws SQLException {
        switch (data.getCommand()) {
            case "help":
            case "info":
            case "show":
            case "show_my":
            case "count_less_than_fuel_type":
            case "count_by_engine_power":
            case "group_counting_by_creation_date":
            case "add":
            case "clear":
            case "shuffle":
            case "remove_last":
                return true;
            case "update":
                return databaseManager.getVehicleOwnerId(data.getVehicle().getId()) == userId;
            case "remove_by_id":
                return databaseManager.getVehicleOwnerId(data.getId()) == userId;
            default:
                return false;
        }
    }
}

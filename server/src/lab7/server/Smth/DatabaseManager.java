package lab7.server.Smth;

import lab7.server.Exceptions.EmptyResultSetException;
import lab7.server.Exceptions.InvalidCredentialsException;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class DatabaseManager {

    private final DatabaseWrapper wrapper;
    private final Logger logger;

    public DatabaseManager(Logger logger, boolean migrate) {
        this.wrapper = new DatabaseWrapper(logger);
        this.logger = logger;

        if (migrate) {
            try {
                doMigration();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("Got errors while executing migrations - " + e.getMessage());
            }
        }
    }

    public DatabaseManager(Logger logger) {
        this.wrapper = new DatabaseWrapper(logger);
        this.logger = logger;
    }

    public void addUser(String username, String hash) throws SQLException {
        wrapper.executeUpdate("INSERT INTO users (username, password) VALUES (?, ?);", username, hash);
    }

    public long checkUser(String username, String hash) throws SQLException, InvalidCredentialsException {
        DatabaseResponse response = wrapper.executeQuery("SELECT id FROM users WHERE username=? and password=?;", username, hash);
        ResultSet rs = response.getResultSet();
        if (rs.next()) {
            long result = rs.getLong("id");
            response.executeCleanup();
            return result;
        } else {
            response.executeCleanup();
            throw new InvalidCredentialsException();
        }
    }

    private void doMigration() throws SQLException {
        String statement = "DROP TABLE IF EXISTS vehicles;" +
                "DROP TABLE IF EXISTS users;" +
                "CREATE TABLE users (id BIGSERIAL PRIMARY KEY, username VARCHAR(64) UNIQUE NOT NULL, password VARCHAR(255) NOT NULL);" +
                "CREATE TABLE vehicles (id BIGSERIAL PRIMARY KEY, name VARCHAR(255), coordinates_x BIGINT NOT NULL, coordinates_y BIGINT NOT NULL, creation_date TIMESTAMP NOT NULL DEFAULT current_timestamp, engine_power INT NOT NULL, capacity DECIMAL(4) NOT NULL, vehicle_type VARCHAR(16) NOT NULL, fuel_type VARCHAR(16) NOT NULL, user_id BIGINT NOT NULL, FOREIGN KEY(user_id) REFERENCES users(id));";

        wrapper.executeUpdate(statement);
    }

    public long getVehicleOwnerId(long id) throws SQLException {
        DatabaseResponse response = wrapper.executeQuery("SELECT user_id FROM vehicles WHERE id=?;", id);
        ResultSet rs = response.getResultSet();
        long result;
        if (rs.next()) {
             result = rs.getLong("user_id");
        } else {
            result = -1;
        }
        response.executeCleanup();
        return result;
    }

    public Vehicle addVehicle(Vehicle vehicle, long userId) throws SQLException, EmptyResultSetException {
        DatabaseResponse response = wrapper.executeQuery("INSERT INTO vehicles (name, coordinates_x, coordinates_y," +
                "engine_power, capacity, vehicle_type, fuel_type, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) returning id, creation_date;",
                vehicle.getName(), vehicle.getCoordinates().getX(), vehicle.getCoordinates().getY(),
                vehicle.getEnginePower(), vehicle.getCapacity(), vehicle.getVehicleType(), vehicle.getFuelType(), userId);

        ResultSet rs = response.getResultSet();
        if (rs.next()) {
            long id = rs.getLong("id");
            Timestamp creationDate = rs.getTimestamp("creation_date");
            response.executeCleanup();

            return vehicle.preparedForSaving(id, creationDate.toLocalDateTime().toLocalDate());
        } else {
            throw new EmptyResultSetException();
        }
    }

    public void removeAllVehiclesOfUser(long userId) throws SQLException {
        wrapper.executeUpdate("DELETE FROM vehicles where user_id=?;", userId);
    }

    public void removeVehicleById(long id) throws SQLException {
        wrapper.executeUpdate("DELETE FROM vehicles where id=?;", id);
    }

    public void updateVehicleById(long id, Vehicle v) throws SQLException {
        wrapper.executeUpdate("UPDATE vehicles set name=?, coordinates_x=?, coordinates_y=?," +
                "engine_power=?, capacity=?, vehicle_type=?, fuel_type=? where id=?", v.getName(), v.getCoordinates().getX(), v.getCoordinates().getY(),
                v.getEnginePower(), v.getCapacity(), v.getVehicleType(), v.getFuelType(), id);
    }

    public Map<Long, Vector<Vehicle>> getAllVehicles() throws SQLException {
        DatabaseResponse response = wrapper.executeQuery("SELECT * FROM vehicles;");
        ResultSet rs = response.getResultSet();

        Map<Long, Vector<Vehicle>> vehicles = new HashMap<>();

        while (rs.next()) {
            long userId = rs.getLong("user_id");
            if (!vehicles.containsKey(userId)) {
                vehicles.put(userId, new Vector<>());
            }
            vehicles.get(userId).add(Vehicle.fromResultSetRow(rs));
        }

        return vehicles;
    }
}


package lab7.server.Entity;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 * vehicle class
 *
 * @author Nagorny Leonid
 */

public class Vehicle implements Comparable {

    private final long id;
    private String name;
    private Coordinates coordinates;
    private final LocalDate creationDate;
    private Integer enginePower;
    private Float capacity;
    private VehicleType vehicleType;
    private FuelType fuelType;

    private long userId;
    private int userColor;

    public Vehicle() {
        this.id = 0;
        this.creationDate = null;
    }


    private Vehicle(long id, String name, Coordinates coordinates, LocalDate creationDate, int enginePower, float capacity, VehicleType vehicleType, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
    }

    public Vehicle(String name, Coordinates coordinates, int enginePower, float capacity, VehicleType vehicleType, FuelType fuelType) {
        this.id = 0;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = null;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
    }

    public Vehicle(long id, String name, Coordinates coordinates, LocalDate creationDate, Integer enginePower, Float capacity, VehicleType vehicleType, FuelType fuelType, long userId, int userColor) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
        this.userId = userId;
        this.userColor = userColor;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Integer getEnginePower() {
        return enginePower;
    }

    public Float getCapacity() {
        return capacity;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public long getUserId() {
        return userId;
    }

    public int getUserColor() {
        return userColor;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUserColor(int userColor) {
        this.userColor = userColor;
    }

    @Override
    public String toString() {
        return "Vehicle {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", enginePower=" + enginePower +
                ", capacity=" + capacity +
                ", vehicleType=" + vehicleType +
                ", fuelType=" + fuelType +
                ", userId=" + userId +
                '}';
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public int compareTo(Object o) {
        Vehicle vehicle = (Vehicle) o;
        return (getEnginePower() - vehicle.getEnginePower());
    }

    public Vehicle preparedForSaving(long id, LocalDate creationDate, long userId, int userColor) {
        return new Vehicle(id, this.name, this.coordinates, creationDate, this.enginePower, this.capacity, this.vehicleType, this.fuelType, userId, userColor);
    }

    public void update(String name, Coordinates coordinates, int enginePower, float capacity, VehicleType vehicleType, FuelType fuelType) {
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
    }

    public static Vehicle fromResultSetRow(ResultSet rs) throws SQLException {
        return new Vehicle(rs.getLong("id"),
                rs.getString("name"),
                new Coordinates(rs.getInt("coordinates_x"), rs.getInt("coordinates_y")),
                rs.getTimestamp("creation_date").toLocalDateTime().toLocalDate(),
                rs.getInt("engine_power"),
                rs.getFloat("capacity"),
                VehicleType.valueOf(rs.getString("vehicle_type")),
                FuelType.valueOf(rs.getString("fuel_type")),
                rs.getLong("user_id"),
                rs.getInt("color"));
    }

}


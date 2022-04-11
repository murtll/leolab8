package lab5.Smth;


import java.time.LocalDate;


/**
 * vehicle class
 *
 * @author Nagorny Leonid
 */

public class Vehicle implements Comparable {

    //    private final LocalDate creationDate;
    private final int id;
    private final String name;
    private final Coordinates coordinates;
    private final LocalDate creationDate;
    private final Integer enginePower;
    private final Float capacity;
    private final VehicleType vehicleType;
    private final FuelType fuelType;

    public Vehicle(int id, String name, Coordinates coordinates, LocalDate creationDate, int enginePower, float capacity, VehicleType vehicleType, FuelType fuelType) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
    }


    public int getId() {
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

    @Override
    public String toString() {
        String info = "Больше информации ниже\n";
        info += ("id:" + id + '\n');
        info += ("имя:" + name + '\n');
        info += (coordinates.toString());
        info += ("Дата создания:" + creationDate + '\n');
        info += ("Мощность двигателя:" + enginePower + '\n');
        info += ("Емкость:" + capacity + '\n');
        info += ("Тип транспорта:" + vehicleType + '\n');
        info += ("Тип топлива:" + fuelType + '\n');
        return info;
    }

    public Coordinates getCoordinates() {

        return coordinates;
    }

    @Override
    public int compareTo(Object o) {
        Vehicle vehicle = (Vehicle) o;
//        return (getCreationDate().compareTo(getCreationDate()));
        return (getEnginePower() - vehicle.getEnginePower());
    }

}


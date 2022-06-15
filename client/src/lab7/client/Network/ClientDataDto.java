package lab7.client.Network;

import lab7.client.Smth.FuelType;
import lab7.client.Smth.Vehicle;

public class ClientDataDto {
    private final String command;
    private Vehicle vehicle;
    private long id;
    private FuelType fuelType;
    private int enginePower;

    public ClientDataDto() {
        this.command = null;
    }

    public ClientDataDto(String command) {
        this.command = command;
    }

    public ClientDataDto(String command, Vehicle vehicle) {
        this.command = command;
        this.vehicle = vehicle;
    }

    public ClientDataDto(String command, Vehicle vehicle, long id) {
        this.command = command;
        this.vehicle = vehicle;
        this.id = id;
    }

    public ClientDataDto(String command, long id) {
        this.command = command;
        this.id = id;
    }

    public ClientDataDto(String command, FuelType fuelType) {
        this.command = command;
        this.fuelType = fuelType;
    }

    public ClientDataDto(String command, int enginePower) {
        this.command = command;
        this.enginePower = enginePower;
    }


    public String getCommand() {
        return command;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public long getId() {
        return id;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public int getEnginePower() {
        return enginePower;
    }
}

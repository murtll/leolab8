package lab7.server.Network;

import lab7.server.Entity.Vehicle;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Vector;

public class ServerResponseDto {
    private final boolean ok;
    private String message;
    private Vector<Vehicle> vehicles;
    private int number;
    private long id;
    private int color;
    private Map<LocalDate, Long> groups;

    public ServerResponseDto() {
        this.ok = false;
    }

    public ServerResponseDto(boolean ok) {
        this.ok = ok;
    }

    public ServerResponseDto(boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }

    public ServerResponseDto(boolean ok, Vector<Vehicle> vehicles) {
        this.ok = ok;
        this.vehicles = vehicles;
    }

    public ServerResponseDto(boolean ok, Map<LocalDate, Long> groups) {
        this.ok = ok;
        this.groups = groups;
    }

    public ServerResponseDto(boolean ok, int number) {
        this.ok = ok;
        this.number = number;
    }

    public ServerResponseDto(boolean ok, long id, int color) {
        this.ok = ok;
        this.id = id;
        this.color = color;
    }

    public boolean isOk() {
        return ok;
    }

    public String getMessage() {
        return message;
    }

    public Vector<Vehicle> getVehicles() {
        return vehicles;
    }

    public int getNumber() {
        return number;
    }

    public Map<LocalDate, Long> getGroups() {
        return groups;
    }

    public long getId() {
        return id;
    }

    public int getColor() {
        return color;
    }
}

package lab7.server.Security;

import lab7.server.Network.ClientDataDto;

public class CommandValidator {
    public static boolean validateNoArgCommand(ClientDataDto data) {
        return data.getEnginePower() == 0 && data.getFuelType() == null && data.getVehicle() == null && data.getId() == 0;
    }

    public static boolean validateCommandWithId(ClientDataDto data) {
        return data.getEnginePower() == 0 && data.getFuelType() == null && data.getVehicle() == null && data.getId() != 0;
    }
    public static boolean validateCommandWithIdAndVehicle(ClientDataDto data) {
        return data.getEnginePower() == 0 && data.getFuelType() == null && data.getVehicle() != null && data.getId() != 0;
    }

    public static boolean validateCommandWithVehicle(ClientDataDto data) {
        return data.getEnginePower() == 0 && data.getFuelType() == null && data.getVehicle() != null && data.getId() == 0;
    }

    public static boolean validateCommandWithFuelType(ClientDataDto data) {
        return data.getEnginePower() == 0 && data.getFuelType() != null && data.getVehicle() == null && data.getId() == 0;
    }

    public static boolean validateCommandWithEnginePower(ClientDataDto data) {
        return data.getEnginePower() != 0 && data.getFuelType() == null && data.getVehicle() == null && data.getId() == 0;
    }

    public static boolean validateEditCommand(ClientDataDto data) {
        switch (data.getCommand()) {
            case "help":
            case "info":
            case "show":
            case "show_my":
            case "count_less_than_fuel_type":
            case "count_by_engine_power":
            case "group_counting_by_creation_date":
            case "shuffle":
                return false;
            default:
                return true;
        }
    }
}

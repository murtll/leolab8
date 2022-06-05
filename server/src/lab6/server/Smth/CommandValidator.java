package lab6.server.Smth;

import lab6.server.Network.ClientDataDto;

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
}

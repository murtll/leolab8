package lab7.server.Smth;

import lab7.server.Network.ClientDataDto;
import lab7.server.Network.ServerResponseDto;
import lab7.server.Security.CommandValidator;

/**
 * class with interactive mode and script mode
 *
 * @author Nagorny Leonid
 */

public class Commander {

    public static ServerResponseDto categorizeCommand(ClientDataDto userCommand, long userId) {
        switch (userCommand.getCommand()) {
            case "help":
                if (CommandValidator.validateNoArgCommand(userCommand))
                    return CommandManager.help();
                return new ServerResponseDto(false, "Command doesn't support arguments.");
            case "info":
                if (CommandValidator.validateNoArgCommand(userCommand))
                    return CommandManager.info();
                return new ServerResponseDto(false, "Command doesn't support arguments.");
            case "show":
                if (CommandValidator.validateNoArgCommand(userCommand))
                    return CommandManager.show();
                return new ServerResponseDto(false, "Command doesn't support arguments.");
            case "show_my":
                if (CommandValidator.validateNoArgCommand(userCommand))
                    return CommandManager.show(userId);
                return new ServerResponseDto(false, "Command doesn't support arguments.");
            case "add":
                if (CommandValidator.validateCommandWithVehicle(userCommand))
                    return CommandManager.add(userCommand.getVehicle(), userId);
                return new ServerResponseDto(false, "Command doesn't support arguments.");
            case "clear":
                if (CommandValidator.validateNoArgCommand(userCommand))
                    return CommandManager.clear(userId);
                return new ServerResponseDto(false, "Command doesn't support arguments.");
            case "update":
                if (CommandValidator.validateCommandWithIdAndVehicle(userCommand)) {
                    return CommandManager.update_id(userCommand.getId(), userCommand.getVehicle(), userId);
                }
                return new ServerResponseDto(false, "Invalid arguments.");
            case "count_less_than_fuel_type":
                if (CommandValidator.validateCommandWithFuelType(userCommand)) {
                    return CommandManager.count_less_than_fuel_type(userCommand.getFuelType());
                }
                return new ServerResponseDto(false, "Invalid arguments.");
            case "remove_by_id":
                if (CommandValidator.validateCommandWithId(userCommand)) {
                    return CommandManager.remove_by_id(userCommand.getId(), userId);
                }
                return new ServerResponseDto(false, "Invalid arguments.");
            case "count_by_engine_power":
                if (CommandValidator.validateCommandWithEnginePower(userCommand)) {
                    return CommandManager.count_by_engine_power(userCommand.getEnginePower());
                }
                return new ServerResponseDto(false, "Invalid arguments.");
            case "group_counting_by_creation_date":
                if (CommandValidator.validateNoArgCommand(userCommand)) {
                    return CommandManager.group_counting_by_creation_date();
                }
                return new ServerResponseDto(false, "Invalid arguments.");
            case "shuffle":
                if (CommandValidator.validateNoArgCommand(userCommand)) {
                    return CommandManager.Shuffle(userId);
                }
                return new ServerResponseDto(false, "Invalid arguments.");
            case "remove_last":
                if (CommandValidator.validateNoArgCommand(userCommand)) {
                    return CommandManager.remove_last(userId);
                }
                return new ServerResponseDto(false, "Invalid arguments.");
            default:
                return new ServerResponseDto(false, "Unknown command");
        }
    }

}

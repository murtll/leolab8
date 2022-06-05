package lab6.server.Intefaces;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.FuelType;
import lab6.server.Smth.Vehicle;

import java.util.Map;
import java.util.Vector;

/**
 * common interface for commands
 *
 * @author Nagorny Leonid
 */

public interface Commands {

    ServerResponseDto execute();
    ServerResponseDto execute(String argument);
    ServerResponseDto execute(Vehicle v);
    ServerResponseDto execute(String argument, Vehicle v);
}

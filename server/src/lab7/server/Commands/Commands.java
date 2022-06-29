package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Entity.Vehicle;

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
    ServerResponseDto execute(Vehicle v, long id);
    ServerResponseDto execute(String argument, Vehicle v, long id);
    ServerResponseDto execute(String argument, long id);
    ServerResponseDto execute(long id);
}

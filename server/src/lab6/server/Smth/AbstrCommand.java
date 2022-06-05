package lab6.server.Smth;

import lab6.server.Intefaces.Commands;
import lab6.server.Network.ServerResponseDto;

import java.util.Map;
import java.util.Vector;

/**
 * An abstract command class that implements the Commands interface
 *
 * @author Nagorny Leonid
 */

public abstract class AbstrCommand implements Commands {
    public ServerResponseDto execute() { throw new RuntimeException("Not implemented"); }
    public ServerResponseDto execute(String argument) { throw new RuntimeException("Not implemented"); }
    public ServerResponseDto execute(Vehicle v) { throw new RuntimeException("Not implemented"); }
    public ServerResponseDto execute(String argument, Vehicle v) { throw new RuntimeException("Not implemented"); }
}

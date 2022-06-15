package lab7.server.Smth;

import lab7.server.Intefaces.Commands;
import lab7.server.Network.ServerResponseDto;

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
    public ServerResponseDto execute(long id) { throw new RuntimeException("Not implemented"); }
    public ServerResponseDto execute(String argument, long id) { throw new RuntimeException("Not implemented"); }
    public ServerResponseDto execute(Vehicle v, long id) { throw new RuntimeException("Not implemented"); }
    public ServerResponseDto execute(String argument, Vehicle v, long id) { throw new RuntimeException("Not implemented"); }
}

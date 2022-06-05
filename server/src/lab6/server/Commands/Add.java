package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;


import lab6.server.Smth.Vehicle;

/**
 * Add element command class
 *
 * @author Nagorny Leonid
 */

public class Add extends AbstrCommand {
    private final CollectionManager cm;

    public Add(CollectionManager cm) {
        this.cm = cm;
    }

    public ServerResponseDto execute(Vehicle v) {
        cm.add(v);
        return new ServerResponseDto(true, "OK");
    }
}

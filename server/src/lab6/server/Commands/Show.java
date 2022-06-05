package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;


/**
 * The command class for displaying collection items
 *
 * @author Nagorny Leonid
 */

public class Show extends AbstrCommand {
    private final CollectionManager cm;

    public Show(CollectionManager c) {
        this.cm = c;
    }

    public ServerResponseDto execute() {
        return new ServerResponseDto(true, cm.show());
    }
}

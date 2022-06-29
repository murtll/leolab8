package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.CollectionManager;


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

    public ServerResponseDto execute(long userId) {
        return new ServerResponseDto(true, cm.show(userId));
    }

}

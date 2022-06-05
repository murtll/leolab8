package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;

/**
 * The class of the command to remove the last element of the collection
 *
 * @author Nagorny Leonid
 */

public class Remove_last extends AbstrCommand {
    private final CollectionManager cm;

    public Remove_last(CollectionManager cm) {
        this.cm = cm;
    }

    public ServerResponseDto execute() {
        cm.remove_last();
        return new ServerResponseDto(true, "OK");
    }
}


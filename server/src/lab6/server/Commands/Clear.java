package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;


/**
 * Collection delete command class
 *
 * @author Nagorny Leonid
 */

public class Clear extends AbstrCommand {
    private final CollectionManager collectionManager;

    public Clear(CollectionManager C) {
        this.collectionManager = C;
    }

    @Override
    public ServerResponseDto execute() {
        collectionManager.clear();
        return new ServerResponseDto(true, "OK");
    }
}

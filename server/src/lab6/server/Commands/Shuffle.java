package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;

/**
 * Command class responsible for mixing elements of the collection
 *
 * @author Nagorny Leonid
 */

public class Shuffle extends AbstrCommand {
    private final CollectionManager collectionManager;

    public Shuffle(CollectionManager c) {
        this.collectionManager = c;
    }

    public ServerResponseDto execute() {
        collectionManager.Shuffle();
        return new ServerResponseDto(true, "OK");
    }
}

package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.AbstrCommand;
import lab7.server.Smth.CollectionManager;

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

    public ServerResponseDto execute(long userId) {
        collectionManager.Shuffle(userId);
        return new ServerResponseDto(true, "OK");
    }
}

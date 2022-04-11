package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;

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

    @Override
    public boolean execute(String argument) {
        return false;
    }

    public boolean execute() {
        collectionManager.Shuffle();
        return true;
    }
}

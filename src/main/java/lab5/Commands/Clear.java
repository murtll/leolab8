package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;


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
    public boolean execute(String argument) {
        return false;
    }

    @Override
    public boolean execute() {
        collectionManager.clear();
        return true;
    }
}

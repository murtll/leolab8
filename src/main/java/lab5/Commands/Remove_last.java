package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;

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

    @Override
    public boolean execute() {
        cm.remove_last();
        return true;
    }

    public boolean execute(String argument) {
        return false;
    }
}


package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;


/**
 * Collection save command class
 *
 * @author Nagorny Leonid
 */

public class Save extends AbstrCommand {
    private final CollectionManager cm;

    public Save(CollectionManager cm) {
        this.cm = cm;

    }

    @Override
    public boolean execute(String argument) {
        return false;
    }

    public boolean execute() {
        cm.save();
        return true;
    }
}

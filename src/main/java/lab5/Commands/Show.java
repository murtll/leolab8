package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;


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

    @Override
    public boolean execute(String argument) {
        return false;
    }

    public boolean execute() {
        cm.show();
        return true;
    }
}

package lab5.Smth;

import lab5.Intefaces.Commands;

/**
 * An abstract command class that implements the Commands interface
 *
 * @author Nagorny Leonid
 */

public abstract class AbstrCommand implements Commands {
    public boolean execute(boolean argument) {
        return false;
    }

    public boolean execute() {
        return false;
    }
}

package lab5.Commands;

import lab5.Smth.AbstrCommand;


/**
 * Program exit command class
 *
 * @author Nagorny Leonid
 */

public class Exit extends AbstrCommand {
    @Override
    public boolean execute(String argument) {
        return false;
    }

    public boolean execute() {
        System.exit(0);
        return true;
    }
}

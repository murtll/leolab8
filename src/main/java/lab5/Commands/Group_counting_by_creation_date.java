package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;


/**
 * The class of the command for grouping a collection element by the value of the element's creationdate field
 *
 * @author Nagorny Leonid
 */

public class Group_counting_by_creation_date extends AbstrCommand {
    private final CollectionManager cm;

    public Group_counting_by_creation_date(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public boolean execute(String argument) {
        return false;
    }

    @Override
    public boolean execute() {
        cm.group_counting_by_creation_date();
        return true;
    }
}

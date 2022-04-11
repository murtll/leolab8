package lab5.Commands;

import lab5.Smth.*;

/**
 * Update command class id
 *
 * @author Nagorny Leonid
 */

public class Update extends AbstrCommand {
    private final InputChecker ic;
    private final CollectionManager cm;
    private final VehicleAsk ca;

    public Update(CollectionManager cm, InputChecker ic, VehicleAsk ca) {
        this.cm = cm;
        this.ca = ca;
        this.ic = ic;
    }

    public boolean execute(String argument) {
        if (ic.longValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
            int id = Integer.parseInt(argument);
            if (cm.remove_by_id((long) id)) {
                System.out.println("Id doesn't exist. Please insert the existing id!");
                return false;
            }
            cm.add(ca.createVehicle());
            return true;
        }
        System.out.println("The inserting ID is not in valid range! Please insert Id greater than 0!");
        return false;
    }

}

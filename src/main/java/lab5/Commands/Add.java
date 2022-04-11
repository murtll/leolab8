package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;


import lab5.Smth.VehicleAsk;

/**
 * Add element command class
 *
 * @author Nagorny Leonid
 */

public class Add extends AbstrCommand {
    private final CollectionManager cm;
    private final VehicleAsk ca;

    public Add(CollectionManager cm, VehicleAsk ca) {
        this.cm = cm;
        this.ca = ca;
    }


    @Override
    public boolean execute(String argument) {
        return false;
    }

    public boolean execute() {
        cm.add(ca.createVehicle());
        return true;
    }
}

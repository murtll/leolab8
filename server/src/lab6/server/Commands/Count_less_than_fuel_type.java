package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;
import lab6.server.Smth.FuelType;
import lab6.server.Smth.InputChecker;


/**
 * The class of the count command is less than the value of fuel?
 *
 * @author Nagorny Leonid
 */

public class Count_less_than_fuel_type extends AbstrCommand {
    private final CollectionManager cm;

    public Count_less_than_fuel_type(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public ServerResponseDto execute(String argument) {
        FuelType ft = FuelType.getObjByStr(argument);
        if (ft != null) {
            int result = cm.count_less_than_fuel_type(ft);
            return new ServerResponseDto(true, result);
        }
        return new ServerResponseDto(false, "Invalid command");
    }
}
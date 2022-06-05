package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;

import lab6.server.Smth.InputChecker;

/**
 * The class of the command to count the elements of the collection by the value engine power
 *
 * @author Nagorny Leonid
 */

public class Count_by_engine_power extends AbstrCommand {
    private final CollectionManager cm;

    public Count_by_engine_power(CollectionManager cm) {
        this.cm = cm;
    }

    @Override
    public ServerResponseDto execute(String argument) {
        if (InputChecker.integerValidCheck(argument, 0, Integer.MAX_VALUE)) {
            int enginePower = Integer.parseInt(argument);
            int result = cm.count_by_engine_power(enginePower);
            return new ServerResponseDto(true, result);
        }
        return new ServerResponseDto(false, "Введенный id имеет неправильный формат! Введеный id должен содеражать значение больше 0!");
    }

}


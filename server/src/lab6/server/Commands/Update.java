package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;
import lab6.server.Smth.InputChecker;
import lab6.server.Smth.Vehicle;

/**
 * Update command class id
 *
 * @author Nagorny Leonid
 */

public class Update extends AbstrCommand {
    private final InputChecker ic;
    private final CollectionManager cm;

    public Update(CollectionManager cm, InputChecker ic) {
        this.cm = cm;
        this.ic = ic;
    }

    public ServerResponseDto execute(String argument, Vehicle v) {
        if (ic.longValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
            int id = Integer.parseInt(argument);
            if (cm.remove_by_id((long) id)) {
                return new ServerResponseDto(true, "Id doesn't exist. Please insert the existing id!");
            }
            cm.add(v);
            return new ServerResponseDto(true, "OK");
        }
        return new ServerResponseDto(true, "The inserting ID is not in valid range! Please insert Id greater than 0!");
    }

}

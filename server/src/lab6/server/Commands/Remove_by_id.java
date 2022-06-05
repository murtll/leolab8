package lab6.server.Commands;

import lab6.server.Network.ServerResponseDto;
import lab6.server.Smth.AbstrCommand;
import lab6.server.Smth.CollectionManager;
import lab6.server.Smth.InputChecker;

/**
 * Command class responsible for deleting a collection element by id
 *
 * @author Nagorny Leonid
 */

public class Remove_by_id extends AbstrCommand {
    private final CollectionManager cm;
    private final InputChecker ic;

    public Remove_by_id(CollectionManager cm, InputChecker ic) {
        this.cm = cm;
        this.ic = ic;
    }

    @Override
    public ServerResponseDto execute(String argument) {
        if (ic.longValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
            int id = Integer.parseInt(argument);
            if (cm.remove_by_id((long) id)) {
                return new ServerResponseDto(false, "Такого id не существует.Пожалуйста введите существующий id!");
            }
            return new ServerResponseDto(true, "OK");
        }
        return new ServerResponseDto(true, "Введенный id имеет неправильный формат!Введеный id должен содеражать значение больше 0!");
    }

}

package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;
import lab5.Smth.InputChecker;

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
    public boolean execute(String argument) {
        if (ic.longValidCheck(argument, (long) 0, Long.MAX_VALUE)) {
            int id = Integer.parseInt(argument);
            if (cm.remove_by_id((long) id)) {
                System.out.println("Такого id не существует.Пожалуйста введите существующий id!");
                return false;
            }
            return true;
        }
        System.out.println("Введенный id имеет неправильный формат!Введеный id должен содеражать значение больше 0!");
        return false;
    }

}

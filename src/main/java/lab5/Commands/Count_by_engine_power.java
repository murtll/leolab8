package lab5.Commands;

import lab5.Smth.AbstrCommand;
import lab5.Smth.CollectionManager;

import lab5.Smth.InputChecker;

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
    public boolean execute(String argument) {
        if (InputChecker.integerValidCheck(argument, 0, Integer.MAX_VALUE)) {
            int enginePower = Integer.parseInt(argument);
            if (cm.count_by_engine_power(enginePower)) {
                System.out.println("Такой мощности не существует.Пожалуйста введите существующую мощность двигателя!");
                return false;
            }
            return true;
        }
        System.out.println("Введенный id имеет неправильный формат!Введеный id должен содеражать значение больше 0!");
        return false;
    }

}


package lab5.Commands;

import lab5.Smth.*;


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
    public boolean execute(String argument) {
        if (InputChecker.integerValidCheck(argument, 0, Integer.MAX_VALUE)) {
            int enginePower = Integer.parseInt(argument);
            if (cm.count_by_engine_power(enginePower)) {
                System.out.println("Такой топлива не существует.Пожалуйста введите число от 1ого до 4ех!");
                return false;
            }
            cm.count_less_than_fuel_type(FuelType.getObjByStr(argument));
            return true;
        }
if (FuelType.values()==null){
    System.out.println("Enum 0!");
}
        return false;
    }
}
package lab6.server.Smth;

import lab6.server.Intefaces.Commands;
import lab6.server.Network.ServerResponseDto;


/**
 * a class that takes in its constructor as command arguments
 *
 * @author Nagorny Leonid
 */

public class CommandManager {
    private static Commands help;
    private static Commands info;
    private static Commands show;
    private static Commands add;
    private static Commands update_id;
    private static Commands remove_by_id;
    private static Commands clear;
    private static Commands remove_last;
    private static Commands Shuffle;
    private static Commands group_counting_by_creation_date;
    private static Commands count_by_engine_power;
    private static Commands count_less_than_fuel_type;

    public CommandManager(Commands help, Commands info,
                          Commands show,
                          Commands add, Commands update_id,
                          Commands remove_by_id,
                          Commands clear,
                          Commands remove_last,
                          Commands Shuffle,
                          Commands group_counting_by_creation_date,
                          Commands count_by_engine_power, Commands count_less_than_fuel_type) {
        CommandManager.help = help;
        CommandManager.info = info;
        CommandManager.show = show;
        CommandManager.add = add;
        CommandManager.update_id = update_id;
        CommandManager.remove_by_id = remove_by_id;
        CommandManager.clear = clear;
        CommandManager.remove_last = remove_last;
        CommandManager.Shuffle = Shuffle;
        CommandManager.group_counting_by_creation_date = group_counting_by_creation_date;
        CommandManager.count_by_engine_power = count_by_engine_power;
        CommandManager.count_less_than_fuel_type = count_less_than_fuel_type;
    }


    public static ServerResponseDto help() {
        return help.execute();
    }

    public static ServerResponseDto info() {
        return info.execute();
    }

    public static ServerResponseDto show() {
        return show.execute();
    }

    public static ServerResponseDto add(Vehicle v) {
        return add.execute(v);
    }

    public static ServerResponseDto update_id(long id, Vehicle v) {
        return update_id.execute(String.valueOf(id), v);
    }

    public static ServerResponseDto remove_by_id(long id) {
        return remove_by_id.execute(String.valueOf(id));
    }

    public static ServerResponseDto clear() {
        return clear.execute();
    }

    public static ServerResponseDto remove_last() {
        return remove_last.execute();
    }

    public static ServerResponseDto Shuffle() {
        return Shuffle.execute();
    }

    public static ServerResponseDto group_counting_by_creation_date() {
        return group_counting_by_creation_date.execute();
    }

    public static ServerResponseDto count_by_engine_power(int ep) {
        return count_by_engine_power.execute(String.valueOf(ep));
    }

    public static ServerResponseDto count_less_than_fuel_type(FuelType ft) {
        return count_less_than_fuel_type.execute(ft.toString());
    }
}

package lab7.server.Smth;

import lab7.server.Entity.FuelType;
import lab7.server.Entity.Vehicle;
import lab7.server.Commands.Commands;
import lab7.server.Network.ServerResponseDto;


/**
 * a class that gives access to the commands
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

    public static void setHelp(Commands help) {
        CommandManager.help = help;
    }

    public static void setInfo(Commands info) {
        CommandManager.info = info;
    }

    public static void setShow(Commands show) {
        CommandManager.show = show;
    }

    public static void setAdd(Commands add) {
        CommandManager.add = add;
    }

    public static void setUpdate_id(Commands update_id) {
        CommandManager.update_id = update_id;
    }

    public static void setRemove_by_id(Commands remove_by_id) {
        CommandManager.remove_by_id = remove_by_id;
    }

    public static void setClear(Commands clear) {
        CommandManager.clear = clear;
    }

    public static void setRemove_last(Commands remove_last) {
        CommandManager.remove_last = remove_last;
    }

    public static void setShuffle(Commands shuffle) {
        Shuffle = shuffle;
    }

    public static void setGroup_counting_by_creation_date(Commands group_counting_by_creation_date) {
        CommandManager.group_counting_by_creation_date = group_counting_by_creation_date;
    }

    public static void setCount_by_engine_power(Commands count_by_engine_power) {
        CommandManager.count_by_engine_power = count_by_engine_power;
    }

    public static void setCount_less_than_fuel_type(Commands count_less_than_fuel_type) {
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

    public static ServerResponseDto show(long userId) {
        return show.execute(userId);
    }


    public static ServerResponseDto add(Vehicle v, long userId) {
        return add.execute(v, userId);
    }

    public static ServerResponseDto update_id(long id, Vehicle v, long userId) {
        return update_id.execute(String.valueOf(id), v, userId);
    }

    public static ServerResponseDto remove_by_id(long id, long userId) {
        return remove_by_id.execute(String.valueOf(id), userId);
    }

    public static ServerResponseDto clear(long userId) {
        return clear.execute(userId);
    }

    public static ServerResponseDto remove_last(long userId) {
        return remove_last.execute(userId);
    }

    public static ServerResponseDto Shuffle(long userId) {
        return Shuffle.execute(userId);
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

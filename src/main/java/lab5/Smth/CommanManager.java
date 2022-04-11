package lab5.Smth;

import lab5.Intefaces.Commands;

/**
 * a class that takes in its constructor as command arguments
 *
 * @author Nagorny Leonid
 */

public class CommanManager {
    private static Commands help;
    private static Commands info;
    private static Commands show;
    private static Commands add;
    private static Commands update_id;
    private static Commands remove_by_id;
    private static Commands clear;
    private static Commands save;
    private static Commands execute;
    private static Commands exit;
    private static Commands remove_last;
    private static Commands Shuffle;
    //    private static Commands history;
    private static Commands group_counting_by_creation_date;
    private static Commands count_by_engine_power;
    private static Commands count_less_than_fuel_type;

    public CommanManager(Commands help, Commands info,
                         Commands show,
                         Commands add, Commands update_id,
                         Commands remove_by_id,
                         Commands clear, Commands save,
                         Commands execute,
                         Commands exit, Commands remove_last,
                         Commands Shuffle,
                         Commands group_counting_by_creation_date,
                         Commands count_by_engine_power, Commands count_less_than_fuel_type) {
        CommanManager.help = help;
        CommanManager.info = info;
        CommanManager.show = show;
        CommanManager.add = add;
        CommanManager.update_id = update_id;
        CommanManager.remove_by_id = remove_by_id;
        CommanManager.clear = clear;
        CommanManager.save = save;
        CommanManager.execute = execute;
        CommanManager.exit = exit;
        CommanManager.remove_last = remove_last;
        CommanManager.Shuffle = Shuffle;
//        history = history;
        CommanManager.group_counting_by_creation_date = group_counting_by_creation_date;
        CommanManager.count_by_engine_power = count_by_engine_power;
        CommanManager.count_less_than_fuel_type = count_less_than_fuel_type;
    }


    public static boolean help() {
        return help.execute();
    }

    public static boolean info() {
        return info.execute();
    }

    public static boolean show() {
        return show.execute();
    }

    public static boolean add() {
        return add.execute();
    }

    public static boolean update_id(String s) {
        return update_id.execute(s);
    }

    public static boolean remove_by_id(String s) {
        return remove_by_id.execute(s);
    }

    public static boolean clear() {
        return clear.execute();
    }

    public static boolean save() {
        return save.execute();
    }

    public static boolean execute() {
        return execute.execute();
    }

    public static boolean exit() {
        return exit.execute();
    }

    public static boolean remove_last() {
        return remove_last.execute();
    }

    public static boolean Shuffle() {
        return Shuffle.execute();
    }

//    public static boolean history() {
//        return history.execute();
//    }

    public static boolean group_counting_by_creation_date() {
        return group_counting_by_creation_date.execute();
    }

    public static boolean count_by_engine_power(String s) {
        return count_by_engine_power.execute(s);
    }

    public static boolean count_less_than_fuel_type() {
        return count_less_than_fuel_type.execute();
    }
}

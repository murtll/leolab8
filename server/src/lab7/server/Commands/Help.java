package lab7.server.Commands;

import lab7.server.Network.ServerResponseDto;
import lab7.server.Smth.AbstrCommand;

/**
 * Help command class
 *
 * @author Nagorny Leonid
 */

public class Help extends AbstrCommand {
    private static final String help =  "help : вывести справку по доступным командам\n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "add  {element} : добавить новый элемент в коллекцию\n" +
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_by_id id : удалить элемент из коллекции по его id\n" +
            "clear : очистить коллекцию\n" +
            "save : сохранить коллекцию в файл\n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "exit : завершить программу (без сохранения в файл)\n" +
            "remove_last : удалить последний элемент из коллекции\n" +
            "shuffle : перемешать элементы коллекции в случайном порядке\n" +
            "history : вывести последние 9 команд (без их аргументов)\n" +
            "group_counting_by_creation_date : сгруппировать элементы коллекции по значению поля creationDate, вывести количество элементов в каждой группе\n" +
            "count_by_engine_power enginePower : вывести количество элементов, значение поля enginePower которых равно заданному\n" +
            "count_less_than_fuel_type fuelType : вывести количество элементов, значение поля fuelType которых меньше заданного";

    public ServerResponseDto execute() {
        return new ServerResponseDto(true, help);
    }
}


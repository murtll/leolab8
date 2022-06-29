package lab7.client.Smth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * class with interactive mode and script mode
 *
 * @author Nagorny Leonid
 */

public class Commander {
    private static final Deque<String> historyRecord = new ArrayDeque<>(9);
    private static Scanner userScanner;

    public static void setUserScanner(Scanner userScanner) {
        Commander.userScanner = userScanner;
    }


    public static void interactiveMode() {
        while (true) {
            System.out.print("=> ");
            String userInput = userScanner.nextLine();
            String[] userCommand = userInput.trim().split(" ");

            if (userInput.length() == 0 || userInput.startsWith("#")) {
                continue;
            } else if (userCommand.length > 2) {
                System.out.println("Неккоректный ввод!Команда должна включать в себя 1 или 2 аргумента." + "Пожалуйста введите команду еще раз!");
                continue;
            }

            categorizeCommand(userCommand, false);
            updateHistory(userCommand);

            System.out.println("_________________________________");
        }
    }

    public static void updateHistory(String[] newCommand) {
        String s;
        s = String.join(" ", newCommand);
        if (historyRecord.size() == 18)
            historyRecord.removeFirst();
        historyRecord.addLast(s);
    }

    public static String scriptMode(String fileName) {

        File scriptFile = new File(fileName.trim());
        System.out.println("Executing script file " + scriptFile.getAbsolutePath());

        Scanner fileScanner;

        try {
            fileScanner = new Scanner(scriptFile);
        } catch (FileNotFoundException e) {
            System.out.println("Этот скрипт файл не существует. Пожалуйста проверьте путь!");
            return "";
        }

        int lineCounter = 0;
        VehicleAsk.setScanner(fileScanner);

        while (fileScanner.hasNextLine()) {
            lineCounter++;
            String scriptLine = fileScanner.nextLine();

            String[] userCommand = scriptLine.trim().split(" ");

            System.out.println(fileName + ':' + lineCounter + " >>> " + scriptLine);

            if (scriptLine.length() == 0 || scriptLine.startsWith("#")) {
                continue;
            } else if (userCommand.length > 2) {
                System.out.println("Неккоректный ввод!Команда должна включать в себя 1 или 2 аргумента.");
                continue;
            }

            String response = categorizeCommand(userCommand, true);
            if (response.equals("invalid_arguments_number") ||
                    response.equals("network_error") ||
                    response.equals("error") ||
                    response.equals("not_allowed_in_script") ||
                    response.equals("unknown_command")) {
                return response;
            }

            System.out.println("------------");
        }
        VehicleAsk.setScanner(userScanner);
        return "success";
    }


    private static String categorizeCommand(String[] userCommand, boolean fromScript) {
        switch (userCommand[0]) {
            case "add":
                if (userCommand.length == 1) {
                    return CommandManager.add();
                } else {
                    return "invalid_arguments_number";
                }
            case "clear":
                if (userCommand.length == 1) {
                    return CommandManager.clear();
                } else {
                    return "invalid_arguments_number";
                }
            case "update":
                if (userCommand.length == 2) {
                    return CommandManager.updateId(userCommand[1]);
                } else {
                    return "invalid_arguments_number";
                }
            case "remove_by_id":
                if (userCommand.length == 2) {
                    return CommandManager.removeById(userCommand[1]);
                } else {
                    return "invalid_arguments_number";
                }
            case "shuffle":
                if (userCommand.length == 1) {
                    return CommandManager.shuffle();
                } else {
                    System.out.println("invalid_arguments_number");
                }
            case "remove_last":
                if (userCommand.length == 1) {
                    return CommandManager.removeLast();
                } else {
                    System.out.println("invalid_arguments_number");
                }
            case "execute":
            case "help":
            case "info":
            case "show":
            case "show_my":
            case "exit":
            case "history":
            case "count_less_than_fuel_type":
            case "login":
            case "register":
            case "count_by_engine_power":
            case "group_counting_by_creation_date":
                return "not_allowed_in_script";
            default:
                return "unknown_command";
        }
    }
}

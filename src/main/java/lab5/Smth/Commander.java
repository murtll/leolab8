package lab5.Smth;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Scanner;

/**
 * class with interactive mode and script mode
 *
 * @author Nagorny Leonid
 */

public class Commander {
    private static final HashMap<String, Boolean> inStack = new HashMap<>();
    private static final Deque<String> historyRecord = new ArrayDeque<>(9);
    private static Scanner userScanner;
    FileM fileM;

    public Commander(CommanManager C, Scanner sc, FileM fileM) {
        this.fileM = fileM;
        userScanner = sc;
    }

    public static void interactiveMode() {
        while (true) {
            String[] userCommand = userScanner.nextLine().trim().split(" ");
            if (userCommand.length > 2) {
                System.out.println("Неккоректный ввод!Команда должна включать в себя 1 или 2 аргумента." + "Пожалуйста введите команду еще раз!");
                continue;
            }
            if (userCommand[0].equals("Exit")) {
                System.exit(0);
            }
            if (!categorizeCommand(userCommand, false)) {
                System.out.println("Неправильный ввод! Невозможно выполнить команду!");
                continue;
            }
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

    private static boolean scriptMode(String fileName) {

        System.out.println("извлечение скрипт файла " + fileName);
//        if (inStack.get(fileName) != null) {
//            if (inStack.get(fileName)) {
//                System.out.println("To avoid infinite recursion. File " + fileName + " can't be executed.");
//                return false;
//            }
//        }
//        inStack.put(fileName, true);

        File scriptFile = new File(fileName.trim());
        System.out.println(scriptFile.getAbsolutePath());
        Scanner fileScanner;
        try {
            fileScanner = new Scanner(scriptFile);

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Этот скрипт файл не существует. Пожалуйста проверьте путь!");
            return false;
        }
        while (fileScanner.hasNextLine()) {
            String[] userCommand = fileScanner.nextLine().trim().split(" ");
            if (userCommand.length > 2) {
                System.out.println("Неккоректный ввод!Команда должна включать в себя 1 или 2 аргумента.");
                continue;
            }
            if (categorizeCommand(userCommand, true)) {
//                    System.out.println("Command is invalid. Can't execute!");
                continue;
            }
            updateHistory(userCommand);
            System.out.println("------------");
        }
        inStack.put(fileName, false);
        return true;
    }


    private static boolean categorizeCommand(String[] userCommand, boolean fromScript) {
        switch (userCommand[0]) {
            case "execute":
                if (fromScript) {
                    System.out.println("Рекурсия недопустима!");
                    return false;
                }
                if (userCommand.length != 1) {
                    return scriptMode(userCommand[1]);
                }
                System.out.println("Пожалуйста внесите script_file!");
                return true;
            case "help":
                if (userCommand.length == 1) {
                    return CommanManager.help();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "info":
                if (userCommand.length == 1) {
                    return CommanManager.info();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "show":
                if (userCommand.length == 1) {
                    return CommanManager.show();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "add":
                if (userCommand.length == 1) {
                    return CommanManager.add();
                }
                System.out.println("Чтобы добавить транспорт, вы должны ввести название команды 'add' !");
                return true;
            case "clear":
                if (userCommand.length == 1) {
                    return CommanManager.clear();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "exit":
                if (userCommand.length == 1) {
                    return CommanManager.exit();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "history":
                if (userCommand.length == 1) {
                    for (String cm : historyRecord) {
                        System.out.println(cm);
                    }
                    return false;
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "update":
                if (userCommand.length == 2) {
                    return CommanManager.update_id(userCommand[1]);
                }
                System.out.println("Пожалуйста введите Id и команду в одной линии");
                return true;
            case "count_less_than_fuel_type":
                if (userCommand.length == 1) {
                    return CommanManager.count_less_than_fuel_type();
                }
                System.out.println("Пожалуйста введите fueltype от 1ого до 4ех");
                return true;
            case "save":
                if (userCommand.length == 1) {
                    return CommanManager.save();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "remove_by_id":
                if (userCommand.length == 2) {
                    return CommanManager.remove_by_id(userCommand[1]);
                }
                System.out.println("Пожалуйста введите Id и команду в одной линии");
                return true;
            case "count_by_engine_power":
                if (userCommand.length == 2) {
                    return CommanManager.count_by_engine_power(userCommand[1]);
                }
                System.out.println("Пожалуйста введите Id и команду в одной линии");
                return true;
                case "group_counting_by_creation_date":
                if (userCommand.length == 1) {
                    return CommanManager.group_counting_by_creation_date();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "Shuffle":
                if (userCommand.length == 1) {
                    return CommanManager.Shuffle();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            case "remove_last":
                if (userCommand.length == 1) {
                    return CommanManager.remove_last();
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                return true;
            default:
                return false;
        }
    }
}

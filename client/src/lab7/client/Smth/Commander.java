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

    private static void scriptMode(String fileName) {

        File scriptFile = new File(fileName.trim());
        System.out.println("Executing script file " + scriptFile.getAbsolutePath());

        Scanner fileScanner;

        try {
            fileScanner = new Scanner(scriptFile);
        } catch (FileNotFoundException e) {
            System.out.println("Этот скрипт файл не существует. Пожалуйста проверьте путь!");
            return;
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

            categorizeCommand(userCommand, true);

            System.out.println("------------");
        }
        VehicleAsk.setScanner(userScanner);
    }


    private static void categorizeCommand(String[] userCommand, boolean fromScript) {
        switch (userCommand[0]) {
            case "execute":
                if (fromScript) {
                    System.out.println("Рекурсия недопустима!");
                } else if (userCommand.length != 1) {
                    scriptMode(userCommand[1]);
                } else {
                    System.out.println("Пожалуйста внесите script_file!");
                }
                break;
            case "help":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.help());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "info":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.info());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "show":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.show());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "show_my":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.showMy());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "add":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.add());
                } else {
                    System.out.println("Чтобы добавить транспорт, вы должны ввести название команды 'add' !");
                }
                break;
            case "clear":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.clear());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "exit":
                if (userCommand.length == 1) {
                    System.exit(0);
                }
                System.out.println("Это команда не поддерживает данный аргумент");
                break;
            case "history":
                if (userCommand.length == 1) {
                    for (String cm : historyRecord) {
                        System.out.println(cm);
                    }
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "update":
                if (userCommand.length == 2) {
                    System.out.println(CommandManager.updateId(userCommand[1]));
                } else {
                    System.out.println("Пожалуйста введите Id и команду в одной линии");
                }
                break;
            case "count_less_than_fuel_type":
                if (userCommand.length == 2) {
                    System.out.println(CommandManager.countLessThanFuelType(userCommand[1]));
                } else {
                    System.out.println("Пожалуйста введите тип топлива и команду в одной линии");
                }
                break;
            case "remove_by_id":
                if (userCommand.length == 2) {
                    System.out.println(CommandManager.removeById(userCommand[1]));
                } else {
                    System.out.println("Пожалуйста введите Id и команду в одной линии");
                }
                break;
            case "count_by_engine_power":
                if (userCommand.length == 2) {
                    System.out.println(CommandManager.countByEnginePower(userCommand[1]));
                } else {
                    System.out.println("Пожалуйста введите Id и команду в одной линии");
                }
                break;
            case "group_counting_by_creation_date":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.groupCountingByCreationDate());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "shuffle":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.shuffle());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "remove_last":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.removeLast());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "login":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.login());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            case "register":
                if (userCommand.length == 1) {
                    System.out.println(CommandManager.register());
                } else {
                    System.out.println("Это команда не поддерживает данный аргумент");
                }
                break;
            default:
                System.out.println("Unknown command!");
        }
    }
}

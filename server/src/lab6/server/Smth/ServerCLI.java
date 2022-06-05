package lab6.server.Smth;

import java.util.Scanner;

public class ServerCLI {

    private static int serverPort;
    private static Scanner scan;
    private static CommandProcessor processor;
    private static CollectionManager collectionManager;

    public static void setCollectionManager(CollectionManager collectionManager) {
        ServerCLI.collectionManager = collectionManager;
    }

    public static void setServerPort(int serverPort) {
        ServerCLI.serverPort = serverPort;
    }

    public static void setScanner(Scanner scan) {
        ServerCLI.scan = scan;
    }

    public static void setProcessor(CommandProcessor processor) {
        ServerCLI.processor = processor;
    }

    public static void start() {
        while (true) {
            System.out.print(":::" + serverPort + " > ");
            String command = scan.nextLine();

            switch (command) {
                case "exit":
                    processor.stop();
                    collectionManager.save();
                    System.exit(0);
                case "help":
                    System.out.println("Lab6 help page\nhelp: display this text\nexit: stop the server\nsave: save data to file");
                    break;
                case "save":
                    collectionManager.save();
                    System.out.println("Data saved");
                    break;
                case "":
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}

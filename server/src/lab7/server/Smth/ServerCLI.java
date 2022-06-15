package lab7.server.Smth;

import java.util.Scanner;

public class ServerCLI {

    private static int serverPort;
    private static Scanner scan;
    private static RequestProcessor processor;
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

    public static void setProcessor(RequestProcessor processor) {
        ServerCLI.processor = processor;
    }

    public static void start() {
        while (true) {
            System.out.print(":::" + serverPort + " > ");
            String command = scan.nextLine();

            switch (command) {
                case "exit":
                    processor.stop();
                    System.exit(0);
                case "help":
                    System.out.println("Lab7 help page\nhelp: display this text\nexit: stop the server\nsave: save data to file");
                    break;
                case "":
                    break;
                default:
                    System.out.println("Unknown command");
            }
        }
    }
}

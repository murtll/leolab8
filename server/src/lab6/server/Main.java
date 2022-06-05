package lab6.server;

import lab6.server.Commands.*;
import lab6.server.Network.DatagramServer;
import lab6.server.Smth.*;
import sun.misc.Signal;


import java.util.Scanner;
import java.util.Vector;

/**
 * Implementation of the program
 *
 * @author Nagorny Leonid
 */

public class Main {
    public static void main(String[] args) {
        CommandProcessor processor = null;

        try {
            if (args.length != 1) {
                System.out.println("Неверный аргумент!");
                System.exit(0);
            }

            FileM fileM = new FileM(args[0]);
            Vector<Vehicle> vehicles = fileM.readCSV();
            CollectionManager collectionManager = new CollectionManager(vehicles, fileM);

            InputChecker inputChecker = new InputChecker();

            CommandManager commandManager = new CommandManager(new Help(),
                    new Info(collectionManager), new Show(collectionManager), new Add(collectionManager),
                    new Update(collectionManager, inputChecker), new Remove_by_id(collectionManager, inputChecker),
                    new Clear(collectionManager),
                    new Remove_last(collectionManager), new Shuffle(collectionManager),
                    new Group_counting_by_creation_date(collectionManager), new Count_by_engine_power(collectionManager),
                    new Count_less_than_fuel_type(collectionManager));

            Signal.handle(new Signal("INT"), sig -> {
                System.out.println("got SIG" + sig.getName() + ", saving data");
                collectionManager.save();
                System.out.println("data saved");
                System.exit(0);
            });

            final int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));
            DatagramServer server = new DatagramServer(serverPort);

            processor = new CommandProcessor(server);
            processor.start();

            Scanner scan = new Scanner(System.in);

            ServerCLI.setServerPort(serverPort);
            ServerCLI.setProcessor(processor);
            ServerCLI.setScanner(scan);
            ServerCLI.setCollectionManager(collectionManager);

            ServerCLI.start();

        } catch (Exception e) {
            if (processor != null) {
                processor.stop();
            }

            e.printStackTrace();
        }
    }
}
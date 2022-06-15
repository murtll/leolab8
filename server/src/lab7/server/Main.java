package lab7.server;

import lab7.server.Commands.*;
import lab7.server.Network.DatagramServer;
import lab7.server.Smth.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


import java.util.Map;
import java.util.Scanner;
import java.util.Vector;


/**
 * Implementation of the program
 *
 * @author Nagorny Leonid
 */

public class Main {
    public static void main(String[] args) {
        RequestProcessor processor = null;
        DatagramServer server = null;
        Logger logger = LogManager.getLogger("lab7");
        Configurator.setLevel("lab7", Level.ALL);

        try {
            if (args.length > 1) {
                logger.error("Неверный аргумент!");
                System.exit(0);
            }

            System.out.println(" _       _   _____\n" +
                    "| | __ _| |_|___  |\n" +
                    "| |/ _` | '_ \\ / /\n" +
                    "| | (_| | |_) / /\n" +
                    "|_|\\__,_|_.__/_/");


            boolean migrate = args.length == 1 && args[0].equals("migrate");
            DatabaseManager databaseManager = new DatabaseManager(logger, migrate);

            Map<Long, Vector<Vehicle>> vehicles = databaseManager.getAllVehicles();

            CollectionManager collectionManager = new CollectionManager(vehicles, logger);

            InputChecker inputChecker = new InputChecker();

            CommandManager.setHelp(new Help());
            CommandManager.setInfo(new Info(collectionManager));
            CommandManager.setShow(new Show(collectionManager));
            CommandManager.setAdd(new Add(collectionManager, databaseManager));
            CommandManager.setUpdate_id(new Update(collectionManager, inputChecker, databaseManager));
            CommandManager.setRemove_by_id(new Remove_by_id(collectionManager, inputChecker, databaseManager));
            CommandManager.setClear(new Clear(collectionManager, databaseManager));
            CommandManager.setRemove_last(new Remove_last(collectionManager, databaseManager));
            CommandManager.setShuffle(new Shuffle(collectionManager));
            CommandManager.setGroup_counting_by_creation_date(new Group_counting_by_creation_date(collectionManager));
            CommandManager.setCount_by_engine_power(new Count_by_engine_power(collectionManager));
            CommandManager.setCount_less_than_fuel_type(new Count_less_than_fuel_type(collectionManager));

            AuthenticationManager authenticationManager = new AuthenticationManager(databaseManager, logger);
            AuthorizationManager authorizationManager = new AuthorizationManager(databaseManager, logger);

            processor = new RequestProcessor(logger, authenticationManager, authorizationManager);

            final int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));
            server = new DatagramServer(serverPort, logger, processor);

            server.start();

            Scanner scan = new Scanner(System.in);

            ServerCLI.setServerPort(serverPort);
            ServerCLI.setProcessor(processor);
            ServerCLI.setScanner(scan);
            ServerCLI.setCollectionManager(collectionManager);

            ServerCLI.start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                server.stop();
            }
            if (processor != null) {
                processor.stop();
            }
        }
    }
}
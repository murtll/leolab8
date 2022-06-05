package lab6.server;

import lab6.server.Commands.*;
import lab6.server.Network.DatagramServer;
import lab6.server.Smth.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
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
        Logger logger = LogManager.getLogger("lab6");
        Configurator.setLevel("lab6", Level.ALL);

        try {
            if (args.length != 1) {
                logger.error("Неверный аргумент!");
                System.exit(0);
            }

            System.out.println(" _       _      __\n" +
                               "| | __ _| |__  / /_\n" +
                               "| |/ _` | '_ \\| '_ \\\n" +
                               "| | (_| | |_) | (_) |\n" +
                               "|_|\\__,_|_.__/ \\___/");

            FileM fileM = new FileM(args[0], logger);
            Vector<Vehicle> vehicles = fileM.readCSV();
            CollectionManager collectionManager = new CollectionManager(vehicles, fileM, logger);

            InputChecker inputChecker = new InputChecker();

            CommandManager.setHelp(new Help());
            CommandManager.setInfo(new Info(collectionManager));
            CommandManager.setShow(new Show(collectionManager));
            CommandManager.setAdd(new Add(collectionManager));
            CommandManager.setUpdate_id(new Update(collectionManager, inputChecker));
            CommandManager.setRemove_by_id(new Remove_by_id(collectionManager, inputChecker));
            CommandManager.setClear(new Clear(collectionManager));
            CommandManager.setRemove_last(new Remove_last(collectionManager));
            CommandManager.setShuffle(new Shuffle(collectionManager));
            CommandManager.setGroup_counting_by_creation_date(new Group_counting_by_creation_date(collectionManager));
            CommandManager.setCount_by_engine_power(new Count_by_engine_power(collectionManager));
            CommandManager.setCount_less_than_fuel_type(new Count_less_than_fuel_type(collectionManager));

            Signal.handle(new Signal("INT"), sig -> {
                logger.info("Got Signal Interrupt, shutting down");
                collectionManager.save();
                System.exit(0);
            });

            final int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));
            DatagramServer server = new DatagramServer(serverPort, logger);

            processor = new CommandProcessor(server, logger);
            processor.start();

            Scanner scan = new Scanner(System.in);

            ServerCLI.setServerPort(serverPort);
            ServerCLI.setProcessor(processor);
            ServerCLI.setScanner(scan);
            ServerCLI.setCollectionManager(collectionManager);

            ServerCLI.start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (processor != null) {
                processor.stop();
            }
        }
    }
}
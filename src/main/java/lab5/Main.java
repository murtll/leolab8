package lab5;

import lab5.Commands.*;
import lab5.Smth.*;


import java.util.Scanner;
import java.util.Vector;

/**
 * Implementation of the program
 *
 * @author Nagorny Leonid
 */

public class Main {


    public static void main(String[] args) {
        boolean success = false;
        while (!success) {
            try {
                if (args.length != 1) {
                    System.out.println("Неверный аргумент!");
                    System.exit(0);
                }
                FileM fileM = new FileM(args[0]);
                Vector<Vehicle> vehicles = fileM.readCSV();
                CollectionManager collectionManager = new CollectionManager(vehicles, fileM);
                System.out.println("Добро пожаловать!");
                System.out.println("Введите help для того чтобы начать");
                InputChecker inputChecker = new InputChecker();
                VehicleAsk vehicleAsk = new VehicleAsk();

                CommanManager commanManager = new CommanManager(new Help(),
                        new Info(collectionManager), new Show(collectionManager), new Add(collectionManager, vehicleAsk), new Update(
                        collectionManager, inputChecker, vehicleAsk), new Remove_by_id(collectionManager, inputChecker)
                        , new Clear(collectionManager), new Save(collectionManager), new Execute(collectionManager), new Exit(), new Remove_last(collectionManager),
                        new Shuffle(collectionManager),
                        new Group_counting_by_creation_date(collectionManager), new Count_by_engine_power(collectionManager), new Count_less_than_fuel_type(collectionManager));
                Commander commander = new Commander(commanManager, new Scanner(System.in), fileM);
                commander.interactiveMode();
                success = true;
            } catch (Exception e) {
//                System.out.println("Нет прав на чтение.Файла не существует");
            }
        }
    }
}
package lab5.Smth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * class responsible for implementing the commands of my project
 *
 * @author Nagorny Leonid
 */

public class CollectionManager {
    private final LocalDateTime creationDate = LocalDateTime.now();
    private final Vector<Vehicle> vehicles;
    private final FileM fileM;

    public CollectionManager(Vector<Vehicle> vehicles, FileM fileM) {
        this.fileM = fileM;
        this.vehicles = vehicles;
    }

    public void add(Vehicle v) {
        vehicles.add(v);
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void show() {
        for (Vehicle vehicle : vehicles) {
            System.out.print(vehicle.toString());
        }
    }

    public void clear() {
        vehicles.clear();
    }

//    public void Exit() {
//        System.exit(2);
//    }

    public boolean remove_by_id(Long id) {
        boolean flag = false;
        for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator.hasNext(); ) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getId() == id) {
                flag = true;
                iterator.remove();
            }
        }
        return !flag;
    }

    public void save() {
        fileM.writeCSV(vehicles);
    }

    public void Shuffle() {
        Collections.shuffle(vehicles);

    }


    public int size() {
        return vehicles.size();

    }

    public  boolean count_less_than_fuel_type(FuelType fuelType){
        int counter = 0;
        boolean flag = false;
        for (Vehicle vehicle : vehicles){
            if (vehicle.getFuelType().forCompare<fuelType.forCompare){
                counter++;
            }
        }
        System.out.println(counter);
        return !flag;
    }

    public boolean count_by_engine_power(int enginePower) {
        int counter = 0;
        boolean flag = false;
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getEnginePower() == enginePower) {
                flag = true;
                counter++;

            }

        }
        System.out.println(counter);
        return !flag;
    }

    public void group_counting_by_creation_date() {
        HashMap<LocalDate, Integer> hashMap = new HashMap<>();
        int count=0;
        int count1 = 0;
        for (Vehicle el : vehicles) {
            if (!hashMap.containsKey(el.getCreationDate())) {
                hashMap.put(el.getCreationDate(), 1);
            } else {
                hashMap.replace(el.getCreationDate(), hashMap.get(el.getCreationDate())+1);
            }

        }

        for (LocalDate date : hashMap.keySet()){
            System.out.println("Дата создания: " + date.toString()+ " - " + hashMap.get(date).toString() + " элементов.");
        }


//        for (Vehicle vehicle : vehicles) {
//            System.out.print(vehicle.toString());
//        }
    }


    public void remove_last() {
        for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator.hasNext(); ) {
            Vehicle vehicle = iterator.next();
            if (vehicle == vehicles.lastElement()) {
                iterator.remove();
            }

        }


    }


}

package lab6.client.Smth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Vector;

/**
 * class responsible for reading and writing to a file
 *
 * @author Nagorny Leonid
 */

public class FileM {
    public String line = "";
    private final String filename;

    public FileM(String filename) {
        this.filename = filename;
    }

    public Vector<Vehicle> readCSV() throws NumberFormatException {
        Vector<Vehicle> vehicles = new Vector<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                Coordinates coordinates = new Coordinates(
                        Integer.parseInt(row[3]),
                        Integer.parseInt(row[2])
                );
                Vehicle vehicle = new Vehicle(
                        Integer.parseInt(row[0]),
                        row[1],
                        coordinates,
                        LocalDate.parse(row[4].replace("/", "-")),
                        Integer.parseInt(row[5]),
                        Float.parseFloat(row[6]),
                        VehicleType.valueOf(row[7]),
                        FuelType.valueOf(row[8]));
                try {
                    vehicles.add(vehicle);
                } catch (NumberFormatException e) {
                    System.out.println("Неверный формат");
                }
            }

        } catch (Exception e) {
            System.out.println("Файла " + filename + " не существует или нет прав на чтение.");
        }

        vehicles.forEach(System.out::println);
        return vehicles;
    }

    public void writeCSV(Vector<Vehicle> vehicles) {
        line = "";
        vehicles.forEach(vehicle -> {
            line += vehicle.getId() + ",";
            line += vehicle.getName() + ",";
            Coordinates coordinates = vehicle.getCoordinates();
            line += coordinates.getX() + "," + coordinates.getY() + ",";
            line += vehicle.getCreationDate() + ",";
            line += vehicle.getEnginePower() + ",";
            line += vehicle.getCapacity() + ",";
            line += vehicle.getVehicleType().name() + ",";
            line += vehicle.getFuelType().name() + "\n";
        });
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(line);
        } catch (Exception e) {
            System.out.println("Файла " + filename + " не существует или нет прав на запись.");
        }

    }
}




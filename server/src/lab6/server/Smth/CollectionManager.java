package lab6.server.Smth;

import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * class responsible for implementing the commands of my project
 *
 * @author Nagorny Leonid
 */

public class CollectionManager {
    private final LocalDateTime creationDate = LocalDateTime.now();
    private final Vector<Vehicle> vehicles;
    private final FileM fileM;

    private final AtomicLong idSetter = new AtomicLong(1);

    private final Logger logger;

    public CollectionManager(Vector<Vehicle> vehicles, FileM fileM, Logger logger) {
        this.fileM = fileM;
        this.vehicles = vehicles;
        this.logger = logger;

        logger.info("Initializing " + this.getClass());

        if (vehicles.size() > 0) {
            this.idSetter.set(vehicles.stream().max(Comparator.comparing(Vehicle::getId)).get().getId() + 1);
        }

        logger.info("Current id is set to " + idSetter.get());
    }

    public void add(Vehicle vehicle) {
        while (vehicles.stream().anyMatch(v -> v.getId() == idSetter.get())) {
            idSetter.incrementAndGet();
        }
        Vehicle newVehicle = vehicle.preparedForSaving(idSetter.getAndIncrement(), LocalDate.now());
        vehicles.add(newVehicle);
        logger.info("Vehicle " + newVehicle + " added");
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Vector<Vehicle> show() {
        return vehicles
                .stream()
                .sorted(Comparator.comparing(Vehicle::getCoordinates))
                .collect(Collectors.toCollection(Vector::new));
    }

    public void clear() {
        vehicles.clear();
        logger.info("Collection cleared");
    }

    public boolean remove_by_id(Long id) {
        boolean result = vehicles.removeIf((v) -> v.getId() == id);
        logger.info("Vehicle with id " + id + "removed");
        return result;
    }

    public void save() {
        fileM.writeCSV(vehicles);
        logger.info("Data saved");
    }

    public void Shuffle() {
        Collections.shuffle(vehicles);
        logger.info("Collection shuffled");
    }

    public int size() {
        return vehicles.size();
    }

    public int count_less_than_fuel_type(FuelType fuelType) {
        return (int) vehicles
                .stream()
                .filter((v) -> v.getFuelType().forCompare < fuelType.forCompare)
                .count();
    }

    public int count_by_engine_power(int enginePower) {
        return (int) vehicles
                .stream()
                .filter((v) -> v.getEnginePower() == enginePower)
                .count();
    }

    public Map<LocalDate, Long> group_counting_by_creation_date() {
        return vehicles.stream()
                .collect(Collectors.groupingBy(Vehicle::getCreationDate, Collectors.counting()));
    }


    public void remove_last() {
        Vehicle last = vehicles.lastElement();
        vehicles.remove(last);
        logger.info("Last vehicle (id " + last.getId() + ") removed");
    }
}

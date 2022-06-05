package lab6.server.Smth;

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

    public CollectionManager(Vector<Vehicle> vehicles, FileM fileM) {
        this.fileM = fileM;
        this.vehicles = vehicles;

        if (vehicles.size() > 0) {
            this.idSetter.set(vehicles.stream().max(Comparator.comparing(Vehicle::getId)).get().getId());
        }
    }

    public void add(Vehicle vehicle) {
        while (vehicles.stream().anyMatch(v -> v.getId() == idSetter.get())) {
            idSetter.incrementAndGet();
        }

        vehicles.add(vehicle.preparedForSaving(idSetter.getAndIncrement(), LocalDate.now()));
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
    }

    public boolean remove_by_id(Long id) {
        return vehicles.removeIf((v) -> v.getId() == id);
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
        Map<LocalDate, Long> hashMap = vehicles.stream()
                .collect(Collectors.groupingBy(Vehicle::getCreationDate, Collectors.counting()));
        return hashMap;
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

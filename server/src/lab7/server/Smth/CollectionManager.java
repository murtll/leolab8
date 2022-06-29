package lab7.server.Smth;

import lab7.server.Entity.FuelType;
import lab7.server.Entity.Vehicle;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * class responsible for implementing the commands of my project
 *
 * @author Nagorny Leonid
 */

public class CollectionManager {
    ReentrantLock lock = new ReentrantLock();
    private final LocalDateTime creationDate = LocalDateTime.now();
    private final Map<Long, Vector<Vehicle>> vehicles;

    private final Logger logger;

    public CollectionManager(Map<Long, Vector<Vehicle>> vehicles, Logger logger) {
        this.vehicles = vehicles;
        this.logger = logger;

        logger.info("Initializing " + this.getClass());
        logger.info("Current number of elements: " + size());
    }

    public void add(Vehicle vehicle, long userId) {
        while (!lock.tryLock()) {}

        if (!vehicles.containsKey(userId))
            vehicles.put(userId, new Vector<>());
        vehicles.get(userId).add(vehicle);

        lock.unlock();

        logger.info("Vehicle " + vehicle + " added");
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Vector<Vehicle> show() {
        lock.lock();

        Vector<Vehicle> result = vehicles
                .values()
                .stream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Vehicle::getCoordinates))
                .collect(Collectors.toCollection(Vector::new));

        lock.unlock();
        return result;
    }

    public Vector<Vehicle> show(long userId) {
        lock.lock();

        Vector<Vehicle> result = vehicles
                .get(userId)
                .stream()
                .sorted(Comparator.comparing(Vehicle::getCoordinates))
                .collect(Collectors.toCollection(Vector::new));

        lock.unlock();
        return result;
    }


    public void clear(long userId) {
        lock.lock();

        if (vehicles.containsKey(userId))
            vehicles.get(userId).clear();

        lock.unlock();

        logger.info("Collection cleared");
    }

    public boolean remove_by_id(Long id, long userId) {
        lock.lock();

        if (vehicles.containsKey(userId)) {
            boolean result = vehicles.get(userId).removeIf((v) -> v.getId() == id);

            lock.unlock();

            logger.info("Vehicle with id " + id + "removed");

            return result;
        }

        lock.unlock();

        return false;
    }

    public void Shuffle(long userId) {
        lock.lock();

        if (vehicles.containsKey(userId))
            Collections.shuffle(vehicles.get(userId));

        lock.unlock();

        logger.info("Collection shuffled");
    }

    public int size() {
        lock.lock();

        int result = (int) vehicles.values()
                .stream()
                .mapToLong(Collection::size)
                .sum();

        lock.unlock();
        return result;
    }

    public int count_less_than_fuel_type(FuelType fuelType) {
        lock.lock();

        int result = (int) vehicles
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter((v) -> v.getFuelType().forCompare < fuelType.forCompare)
                .count();

        lock.unlock();
        return result;
    }

    public int count_by_engine_power(int enginePower) {
        lock.lock();

        int result = (int) vehicles
                .values()
                .stream()
                .flatMap(Collection::stream)
                .filter((v) -> v.getEnginePower() == enginePower)
                .count();

        lock.unlock();
        return result;
    }

    public Map<LocalDate, Long> group_counting_by_creation_date() {
        lock.lock();

        Map<LocalDate, Long> result = vehicles
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Vehicle::getCreationDate, Collectors.counting()));

        lock.unlock();
        return result;
    }

    public long getLastId(long userId) {
        lock.lock();

        long result;
        if (vehicles.containsKey(userId))
            result = vehicles.get(userId).lastElement().getId();
        else result = -1;

        lock.unlock();

        return result;
    }

    public void updateById(long id, Vehicle v, long userId) {
        lock.lock();

        if (vehicles.containsKey(userId))
            vehicles.get(userId)
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .ifPresent(vehicle -> vehicle.update(v.getName(), v.getCoordinates(), v.getEnginePower(), v.getCapacity(), v.getVehicleType(), v.getFuelType()));

        lock.unlock();
    }
}

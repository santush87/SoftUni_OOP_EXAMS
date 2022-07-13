package christmasRaces.core;

import christmasRaces.common.ExceptionMessages;
import christmasRaces.common.OutputMessages;
import christmasRaces.core.interfaces.Controller;
import christmasRaces.entities.cars.Car;
import christmasRaces.entities.cars.MuscleCar;
import christmasRaces.entities.cars.SportsCar;
import christmasRaces.entities.drivers.Driver;
import christmasRaces.entities.drivers.DriverImpl;
import christmasRaces.entities.races.Race;
import christmasRaces.entities.races.RaceImpl;
import christmasRaces.repositories.interfaces.Repository;

import java.util.*;

public class ControllerImpl implements Controller {
    private final Repository<Driver> driverRepository;
    private final Repository<Car> carRepository;
    private final Repository<Race> raceRepository;

    public ControllerImpl(Repository driverRepository, Repository carRepository, Repository raceRepository) {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
        this.raceRepository = raceRepository;
    }

    @Override
    public String createDriver(String driverName) {
        if (this.driverRepository.getByName(driverName) != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_EXISTS, driverName));
        } else {
            this.driverRepository.add(new DriverImpl(driverName));
            return String.format(OutputMessages.DRIVER_CREATED, driverName);
        }
    }

    @Override
    public String createCar(String type, String model, int horsePower) {
        if (this.carRepository.getByName(model) == null) {
            switch (type) {
                case "Muscle":
                    this.carRepository.add(new MuscleCar(model, horsePower));
                    return String.format(OutputMessages.CAR_CREATED, "MuscleCar", model);
                case "Sports":
                    this.carRepository.add(new SportsCar(model, horsePower));
                    return String.format(OutputMessages.CAR_CREATED, "SportsCar", model);
            }
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_EXISTS, model));
        }
        return null;
    }

    @Override
    public String addCarToDriver(String driverName, String carModel) {
        Driver driver = this.driverRepository.getByName(driverName);
        Car car = this.carRepository.getByName(carModel);

        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }
        if (car == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.CAR_NOT_FOUND, carModel));
        }
        this.driverRepository.getByName(driverName).addCar(car);
        /*********  Не е казано дали да се премахне колата !!! **************/
//        this.carRepository.remove(car);

        return String.format(OutputMessages.CAR_ADDED, driverName, carModel);
    }

    @Override
    public String addDriverToRace(String raceName, String driverName) {
        Race race = this.raceRepository.getByName(raceName);
        Driver driver = this.driverRepository.getByName(driverName);

        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }
        if (driver == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_FOUND, driverName));
        }

        this.raceRepository.getByName(raceName).addDriver(driver);

        return String.format(OutputMessages.DRIVER_ADDED, driverName, raceName);
    }

    @Override
    public String createRace(String name, int laps) {
        if (this.raceRepository.getByName(name) == null) {
            this.raceRepository.add(new RaceImpl(name, laps));
            return String.format(OutputMessages.RACE_CREATED, name);
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, name));
        }
    }

    @Override
    public String startRace(String raceName) {
        Race race = this.raceRepository.getByName(raceName);
        if (race == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }
        if (race.getDrivers().size() < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID, raceName, 3));
        }

        Map<Double, Driver> driverMap = new TreeMap<>(Collections.reverseOrder());

        for (Driver driver : race.getDrivers()) {
            driverMap.put(driver.getCar().calculateRacePoints(race.getLaps()), driver);
        }

        List<Driver> drivers = new ArrayList<>(driverMap.values());
        StringBuilder builder = new StringBuilder();

        int num = 0;
        for (Driver driver : drivers) {
            if (num == 0) {
                builder.append(String.format(OutputMessages.DRIVER_FIRST_POSITION, driver.getName(), raceName)).append(System.lineSeparator());
            }
            if (num == 1) {
                builder.append(String.format(OutputMessages.DRIVER_SECOND_POSITION, driver.getName(), raceName)).append(System.lineSeparator());
            }
            if (num == 2) {
                builder.append(String.format(OutputMessages.DRIVER_THIRD_POSITION, driver.getName(), raceName));
                break;
            }
            num++;
        }

        this.raceRepository.remove(race);
        return builder.toString().trim();
    }
}

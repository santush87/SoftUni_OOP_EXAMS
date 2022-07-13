package christmasRaces.entities.races;

import christmasRaces.common.ExceptionMessages;
import christmasRaces.entities.drivers.Driver;

import java.util.ArrayList;
import java.util.Collection;

public class RaceImpl implements Race {
    private String name;
    private int laps;
    private final Collection<Driver> drivers;

    public RaceImpl(String name, int laps) {
        setName(name);
        setLaps(laps);
        this.drivers = new ArrayList<>();
    }

    @Override
    public void addDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException(ExceptionMessages.DRIVER_INVALID);
        } else if (!driver.getCanParticipate()) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_NOT_PARTICIPATE, driver.getName()));
        } else if (this.drivers.contains(driver)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.DRIVER_EXISTS, driver.getName()));
        } else {
            this.drivers.add(driver);
        }
    }

    public void setName(String name) {
        if (name.length() >= 5) {
            this.name = name;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_NAME, name, 5));
        }
    }

    public void setLaps(int laps) {
        if (laps >= 1) {
            this.laps = laps;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_NUMBER_OF_LAPS,1));
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLaps() {
        return this.laps;
    }

    @Override
    public Collection<Driver> getDrivers() {
        return this.drivers;
    }

}

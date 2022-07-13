package christmasRaces.repositories;

import christmasRaces.entities.drivers.Driver;
import christmasRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DriverRepository implements Repository<Driver> {
    private final Collection<Driver> data;

    public DriverRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public Driver getByName(String name) {
        return this.data.stream()
                .filter(s->s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Driver> getAll() {
        return Collections.unmodifiableCollection(this.data);
    }

    @Override
    public void add(Driver driver) {
        this.data.add(driver);
    }

    @Override
    public boolean remove(Driver driver) {
        return this.data.remove(driver);
    }
}

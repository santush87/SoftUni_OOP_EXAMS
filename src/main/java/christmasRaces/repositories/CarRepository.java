package christmasRaces.repositories;

import christmasRaces.entities.cars.Car;
import christmasRaces.repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CarRepository implements Repository<Car> {
    private Collection<Car> data;

    public CarRepository() {
        this.data = new ArrayList<>();
    }

    @Override
    public Car getByName(String carModel) {
        return this.data.stream()
                .filter(s->s.getModel().equals(carModel))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Car> getAll() {
        return Collections.unmodifiableCollection(this.data);
    }

    @Override
    public void add(Car car) {
        this.data.add(car);
    }

    @Override
    public boolean remove(Car car) {
        return this.data.remove(car);
    }
}

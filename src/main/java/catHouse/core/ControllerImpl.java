package catHouse.core;

import catHouse.entities.houses.House;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller{
    private ToyRepository toys;
    private Collection<House> houses;
    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new ArrayList<>();
    }

    @Override
    public String addHouse(String type, String name) {
        return null;
    }

    @Override
    public String buyToy(String type) {
        return null;
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        return null;
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        return null;
    }

    @Override
    public String feedingCat(String houseName) {
        return null;
    }

    @Override
    public String sumOfAll(String houseName) {
        return null;
    }

    @Override
    public String getStatistics() {
        return null;
    }
}

package catHouse.core;

import catHouse.common.ConstantMessages;
import catHouse.common.ExceptionMessages;
import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private final ToyRepository toys;
    private final Collection<House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new ArrayList<>();
    }

    @Override
    public String addHouse(String type, String name) {
        if (type.equals("ShortHouse")) {
            this.houses.add(new ShortHouse(name));
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
        } else if (type.equals("LongHouse")) {
            this.houses.add(new LongHouse(name));
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_HOUSE_TYPE, type);

        } else {
            throw new NullPointerException(ExceptionMessages.INVALID_HOUSE_TYPE);
        }
    }

    @Override
    public String buyToy(String type) {
        if (type.equals("Ball")) {
            this.toys.buyToy(new Ball());
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE, type);
        } else if (type.equals("Mouse")) {
            this.toys.buyToy(new Mouse());
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_TYPE, type);

        } else {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TOY_TYPE);
        }
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy toy = toys.findFirst(toyType);
        if (toy != null) {
            for (House house : this.houses) {
                if (house.getName().equals(houseName)) {
                    house.buyToy(toy);
                    this.toys.removeToy(toy);
                    return String.format(ConstantMessages.SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
                }
            }
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_TOY_FOUND, toyType));
        }
        return null;
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        Cat cat;
        if (catType.equals("ShorthairCat")) {
            cat = new ShorthairCat(catName, catBreed, price);
        } else if (catType.equals("LonghairCat")) {
            cat = new LonghairCat(catName, catBreed, price);
        } else {
            cat = null;
        }

        if (cat == null) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_CAT_TYPE);
        }

        for (House house : this.houses) {
            if (house.getName().equals(houseName)) {
                if (house.getClass().getSimpleName().equals("LongHouse") &&
                        catType.equals("LonghairCat")) {
                    house.addCat(cat);
                    return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
                } else if (house.getClass().getSimpleName().equals("ShortHouse") &&
                        catType.equals("ShorthairCat")) {
                    house.addCat(cat);
                    return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
                } else {
                    return ConstantMessages.UNSUITABLE_HOUSE;
                }
            }
        }
        return null;
    }

    @Override
    public String feedingCat(String houseName) {
        for (House house : this.houses) {
            if (house.getName().equals(houseName)) {
                int fedCount = house.getCats().size();
                house.feeding();
                return String.format(ConstantMessages.FEEDING_CAT, fedCount);
            }
        }
        return null;
    }

    @Override
    public String sumOfAll(String houseName) {
        for (House house : this.houses) {
            if (house.getName().equals(houseName)) {
                double catsValue = house.getCats().stream()
                        .mapToDouble(Cat::getPrice)
                        .sum();
                double toysValue = house.getToys().stream()
                        .mapToDouble(Toy::getPrice)
                        .sum();
                double value = catsValue + toysValue;

                return String.format(ConstantMessages.VALUE_HOUSE, houseName, value);
            }
        }
        return null;
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        this.houses.stream()
                .forEach(s->builder.append(s.getStatistics()).append(System.lineSeparator()));

        return builder.toString().trim();
    }
}

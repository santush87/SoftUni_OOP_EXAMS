package zoo.core;

import zoo.common.ConstantMessages;
import zoo.common.ExceptionMessages;
import zoo.entities.areas.Area;
import zoo.entities.areas.LandArea;
import zoo.entities.areas.WaterArea;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private FoodRepository foodRepository;
    private Collection<Area> areas;

    public ControllerImpl() {
        this.foodRepository = new FoodRepositoryImpl();
        this.areas = new ArrayList<>();
    }

    @Override
    public String addArea(String areaType, String areaName) {
        switch (areaType) {
            case "WaterArea":
                this.areas.add(new WaterArea(areaName));
                break;
            case "LandArea":
                this.areas.add(new LandArea(areaName));
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_AREA_TYPE);
        }
        return String.format("Successfully added %s.", areaType);
    }

    @Override
    public String buyFood(String foodType) {
        switch (foodType) {
            case "Vegetable":
                foodRepository.add(new Vegetable());
                break;
            case "Meat":
                foodRepository.add(new Meat());
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_FOOD_TYPE);
        }
        return String.format("Successfully added %s.", foodType);
    }

    @Override
    public String foodForArea(String areaName, String foodType) {
        Food food = foodRepository.findByType(foodType);
        if (food == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_FOOD_FOUND, foodType));
        } else {
//            this.areas.add(new )
        }
        return null;
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {
        return null;
    }

    @Override
    public String feedAnimal(String areaName) {
        return null;
    }

    @Override
    public String calculateKg(String areaName) {
        return null;
    }

    @Override
    public String getStatistics() {
        return null;
    }
}

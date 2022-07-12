package zoo.core;

import zoo.common.ConstantMessages;
import zoo.common.ExceptionMessages;
import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.animals.TerrestrialAnimal;
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
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_AREA_TYPE, areaType);
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
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FOOD_TYPE, foodType);
    }

    @Override
    public String foodForArea(String areaName, String foodType) {
        Food food = foodRepository.findByType(foodType);
        if (food == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_FOOD_FOUND, foodType));
        } else {
          Area area = getAreaByName(areaName);
          if (area != null){
              area.addFood(food);
              this.foodRepository.remove(food);
              return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FOOD_IN_AREA, foodType, areaName);
          }
        }
        return null;
    }

    private Area getAreaByName(String areaName) {
        return this.areas.stream()
                .filter(s->s.getName().equals(areaName))
                .findFirst()
                .get();
    }

    @Override
    public String addAnimal(String areaName, String animalType, String animalName, String kind, double price) {
        Area area = getAreaByName(areaName);
        String areaType = area.getClass().getSimpleName();
        switch (animalType){
            case "AquaticAnimal":
                if (areaType.equals("WaterArea")){
                    area.addAnimal(new AquaticAnimal(animalName, kind, price));
                } else {
                    return String.format(ConstantMessages.AREA_NOT_SUITABLE);
                }
                break;
            case "TerrestrialAnimal":
                if (areaType.equals("LandArea")){
                    area.addAnimal(new TerrestrialAnimal(animalName, kind, price));
                } else {
                    return String.format(ConstantMessages.AREA_NOT_SUITABLE);
                }
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_ANIMAL_TYPE);
        }
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_ANIMAL_IN_AREA, animalType, areaName);
    }

    @Override
    public String feedAnimal(String areaName) {
        Area area = getAreaByName(areaName);
        area.feed();
        return String.format(ConstantMessages.ANIMALS_FED, area.getAnimals().size());
    }

    @Override
    public String calculateKg(String areaName) {
        Area area = getAreaByName(areaName);
        double totalKg = area.getAnimals().stream()
                .mapToDouble(Animal::getKg)
                .sum();
        return String.format(ConstantMessages.KILOGRAMS_AREA, areaName, totalKg);
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        this.areas.stream()
                .forEach(s->builder.append(s.getInfo()).append(System.lineSeparator()));

        return builder.toString().trim();
    }
}

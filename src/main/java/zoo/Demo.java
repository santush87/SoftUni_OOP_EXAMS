package zoo;

import zoo.entities.animals.Animal;
import zoo.entities.animals.AquaticAnimal;
import zoo.entities.foods.Food;
import zoo.entities.foods.Meat;
import zoo.entities.foods.Vegetable;
import zoo.repositories.FoodRepository;
import zoo.repositories.FoodRepositoryImpl;

public class Demo {
    public static void main(String[] args) {
        Animal first = new AquaticAnimal("Ribka", "Fish", 10);
        first.eat();

        Food someFood = new Vegetable();
        Food otherFood = new Meat();

        FoodRepository repository = new FoodRepositoryImpl();
        repository.add(someFood);
        repository.add(otherFood);
        System.out.println();

    }
}

package zoo.entities.areas;

import zoo.common.ExceptionMessages;
import zoo.entities.animals.Animal;
import zoo.entities.foods.Food;
import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseArea implements Area{
    private String name;
    private int capacity;
    private final Collection<Food> foods;
    private final Collection<Animal> animals;

    public BaseArea(String name, int capacity) {
        setName(name);
        setCapacity(capacity);
        this.foods = new ArrayList<>();
        this.animals = new ArrayList<>();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new NullPointerException(ExceptionMessages.AREA_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int sumCalories(){
        return this.foods.stream()
                .mapToInt(Food::getCalories).sum();
    }

    public void addAnimal(Animal animal){
        if (this.animals.size() < this.capacity){
            this.animals.add(animal);
        } else {
            throw new IllegalStateException(ExceptionMessages.NOT_ENOUGH_CAPACITY);
        }
    }

    public void removeAnimal(Animal animal){
        this.animals.remove(animal);
    }

    public void addFood(Food food){
        this.foods.add(food);
    }

    public void feed(){
        this.animals
                .forEach(Animal::eat);
    }

    public  String getInfo(){
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s (%s)",this.getName(), this.getClass().getSimpleName())).append(System.lineSeparator());
        builder.append("Animals: ");
        if (getAnimals().isEmpty()){
            builder.append("none");
        } else {
            for (Animal animal : getAnimals()) {
                builder.append(animal.getName()).append(" ");
            }
            builder.append(System.lineSeparator());
        }
        builder.append(String.format("Foods: %d", getFoods().size())).append(System.lineSeparator());
        builder.append(String.format("Calories: %d", sumCalories())).append(System.lineSeparator());

        return builder.toString().trim();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Collection<Animal> getAnimals() {
        return this.animals;
    }

    @Override
    public Collection<Food> getFoods() {
        return this.foods;
    }
}

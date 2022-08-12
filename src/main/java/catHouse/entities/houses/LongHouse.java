package catHouse.entities.houses;

import catHouse.common.ConstantMessages;
import catHouse.entities.cat.Cat;

import java.util.stream.Collectors;

public class LongHouse extends BaseHouse{
    private static final int CAPACITY = 30;

    public LongHouse(String name) {
        super(name, CAPACITY);
    }

    @Override
    public void addCat(Cat cat) {
        if (super.getCats().size() >= CAPACITY){
            throw new IllegalArgumentException(ConstantMessages.NOT_ENOUGH_CAPACITY_FOR_CAT);
        } else if (cat.getClass().getSimpleName().equals("LonghairCat")) {
            super.getCats().add(cat);
        }
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        builder.append(getName()).append(" LongHouse:");
        builder.append(System.lineSeparator());
        builder.append("Cats: ");
        if (super.getCats().size() == 0) {
            builder.append("none");
        } else {
            builder.append(super.getCats().stream()
                    .map(Cat::getName)
                    .collect(Collectors.joining(" ")));
        }
        builder.append(System.lineSeparator());
        builder.append("Toys: ").append(super.getToys().size()).append(" ").append("Softness: ").append(sumSoftness());
        return builder.toString().trim();
    }
}

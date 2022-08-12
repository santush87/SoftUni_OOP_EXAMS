package catHouse.entities.cat;

public class ShorthairCat extends BaseCat {
    private static final int KILOGRAMS = 7;
    private static final int INCREASING_KG = 1;

    public ShorthairCat(String name, String breed, double price) {
        super(name, breed, price);
    }

    @Override
    public void setKilograms(int kilograms) {
        super.setKilograms(KILOGRAMS);
    }

    @Override
    public void eating() {
        setKilograms(getKilograms() + INCREASING_KG);
    }
}

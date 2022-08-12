package catHouse.entities.cat;

public class LonghairCat extends BaseCat{

    private static final int KILOGRAMS = 9;
    private static final int INCREASING_KG = 3;

    public LonghairCat(String name, String breed, double price) {
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

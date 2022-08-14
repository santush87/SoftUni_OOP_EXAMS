package football.entities.supplement;

public class Liquid extends BaseSupplement{

    private static final int ENERGY_LIQUID = 90;
    private static final double PRICE_LIQUID = 25;

    public Liquid() {
        super(ENERGY_LIQUID, PRICE_LIQUID);
    }
}

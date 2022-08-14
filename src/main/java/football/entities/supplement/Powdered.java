package football.entities.supplement;

public class Powdered extends BaseSupplement{
    private static final int ENERGY_POWDERED = 120;
    private static final double PRICE_POWDERED = 15;

    public Powdered() {
        super(ENERGY_POWDERED, PRICE_POWDERED);
    }
}

package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public class SportsCar extends BaseCar {
    private final static int SPORTS_HORSEPOWER_MIN = 250;
    private final static int SPORTS_HORSEPOWER_MAX = 450;
    private final static double CUBIC_CENTIMETERS = 3000;

    public SportsCar(String model, int horsePower) {
        super(model, horsePower, CUBIC_CENTIMETERS);
    }

    @Override
    protected void setHorsePower(int horsePower) {
        if (horsePower >= SPORTS_HORSEPOWER_MIN && horsePower <= SPORTS_HORSEPOWER_MAX) {
            super.setHorsePower(horsePower);
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }

    }
}

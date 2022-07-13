package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public class SportsCar extends BaseCar {
    private final static double CUBIC_CENTIMETERS = 3000;
    private final static int HORSEPOWER_MIN = 250;
    private final static int HORSEPOWER_MAX = 450;

    public SportsCar(String model, int horsePower) {
        super(model, horsePower, CUBIC_CENTIMETERS);
    }

    @Override
    protected void setHorsePower(int horsePower) {
        if (horsePower >= HORSEPOWER_MIN && horsePower <= HORSEPOWER_MAX) {
            setHorsePower(horsePower);
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }
    }
}

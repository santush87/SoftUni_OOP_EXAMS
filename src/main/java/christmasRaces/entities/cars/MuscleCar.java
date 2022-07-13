package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public class MuscleCar extends BaseCar {
    private final static double CUBIC_CENTIMETERS = 5000;
    private final static int HORSEPOWER_MIN = 400;
    private final static int HORSEPOWER_MAX = 600;

    public MuscleCar(String model, int horsePower) {
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

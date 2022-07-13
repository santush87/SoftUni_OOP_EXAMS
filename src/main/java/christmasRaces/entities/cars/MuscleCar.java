package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public class MuscleCar extends BaseCar {
    private final static int MUSCLE_HORSEPOWER_MIN = 400;
    private final static int MUSCLE_HORSEPOWER_MAX = 600;
    private final static double CUBIC_CENTIMETERS = 5000;

    public MuscleCar(String model, int horsePower) {
        super(model, horsePower, CUBIC_CENTIMETERS);
    }

    @Override
    protected void setHorsePower(int horsePower) {
        if (horsePower >= MUSCLE_HORSEPOWER_MIN && horsePower <= MUSCLE_HORSEPOWER_MAX) {
            super.setHorsePower(horsePower);
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
        }

    }
}

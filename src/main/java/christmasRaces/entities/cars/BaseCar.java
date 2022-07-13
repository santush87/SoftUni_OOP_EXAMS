package christmasRaces.entities.cars;

import christmasRaces.common.ExceptionMessages;

public abstract class BaseCar implements Car {

    private String model;
    private int horsePower;
    private double cubicCentimeters;

    public BaseCar(String model, int horsePower, double cubicCentimeters) {
        setModel(model);
        setHorsePower(horsePower);
        setCubicCentimeters(cubicCentimeters);
    }

    public double calculateRacePoints(int laps) {
        return getCubicCentimeters() / getHorsePower() * laps;
    }

    protected void setModel(String model) {
        if (model.length() >= 4) {
            this.model = model;
        } else {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_MODEL, model, 4));
        }
    }

    protected void  setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    protected void setCubicCentimeters(double cubicCentimeters) {
        this.cubicCentimeters = cubicCentimeters;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }
}

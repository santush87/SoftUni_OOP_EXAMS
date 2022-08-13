package glacialExpedition.models.explorers;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.suitcases.Carton;
import glacialExpedition.models.suitcases.Suitcase;

public abstract class BaseExplorer implements Explorer {
    private String name;
    private double energy;
    private final Suitcase suitcase;

    public BaseExplorer(String name, double energy) {
        setName(name);
        setEnergy(energy);
        this.suitcase = new Carton();
    }

    public void setName(String name) {
        if (name.trim().isEmpty()) {
            throw new NullPointerException(ExceptionMessages.EXPLORER_NAME_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    public void setEnergy(double energy) {
        if (energy < 0) {
            throw new IllegalArgumentException(ExceptionMessages.EXPLORER_ENERGY_LESS_THAN_ZERO);
        }
        this.energy = energy;
    }

    @Override
    public boolean canSearch() {
        return getEnergy() > 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public Suitcase getSuitcase() {
        return suitcase;
    }

    @Override
    public void search() {
        double currEnergy = getEnergy() - 15;
        if (currEnergy < 0) {
            currEnergy = 0;
        }
        setEnergy(currEnergy);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(ConstantMessages.FINAL_EXPLORER_NAME, getName())).append(System.lineSeparator());
        builder.append(String.format(ConstantMessages.FINAL_EXPLORER_ENERGY, getEnergy())).append(System.lineSeparator());
        if (getSuitcase().getExhibits().isEmpty()) {
            builder.append(String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS, "None")).append(System.lineSeparator());
        } else {
            builder.append(String.format(ConstantMessages.FINAL_EXPLORER_SUITCASE_EXHIBITS,String.join(", ", getSuitcase().getExhibits())));
        }
        return builder.toString().trim();
    }
}

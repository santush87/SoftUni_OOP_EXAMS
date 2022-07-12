package fairyShop.models;

public class Sleepy extends BaseHelper{

    private final static int ENERGY = 50;

    public Sleepy(String name) {
        super(name, ENERGY);
    }

    @Override
    public void work() {
        int currEnergy = getEnergy() - 5;
        setEnergy(Math.max(currEnergy, 0));
    }
}

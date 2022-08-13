package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer{
    private final static double ENERGY = 60;
    private final static double DECREASING_ENERGY = 7;

    public NaturalExplorer(String name) {
        super(name, ENERGY);
    }

    @Override
    public void search() {
        double currEnergy = getEnergy()-DECREASING_ENERGY;
        if (currEnergy < 0){
            currEnergy = 0;
        }
        setEnergy(currEnergy);
    }
}

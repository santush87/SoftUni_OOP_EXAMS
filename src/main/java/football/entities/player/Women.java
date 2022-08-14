package football.entities.player;

public class Women extends BasePlayer {
    private static final double WOMEN_KG = 60;
    private static final int WOMEN_STRENGTH = 115;

    public Women(String name, String nationality, int strength) {
        super(name, nationality, strength);
    }

    @Override
    public void setKg(double kg) {
        super.setKg(WOMEN_KG);
    }

    @Override
    public void stimulation() {
        setStrength(getStrength() + WOMEN_STRENGTH);
    }
}

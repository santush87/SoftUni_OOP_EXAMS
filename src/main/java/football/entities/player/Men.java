package football.entities.player;

public class Men extends BasePlayer{
    private static final double MEN_KG = 85.50;
    private static final int MEN_STRENGTH = 145;

    public Men(String name, String nationality, int strength) {
        super(name, nationality, strength);
    }

    @Override
    public void setKg(double kg) {
        super.setKg(MEN_KG);
    }

    @Override
    public void stimulation() {
        setStrength(getStrength() + MEN_STRENGTH);
    }
}

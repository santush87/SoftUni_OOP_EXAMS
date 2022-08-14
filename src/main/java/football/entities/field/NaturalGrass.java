package football.entities.field;

import football.entities.player.Player;

import java.util.stream.Collectors;

public class NaturalGrass extends BaseField{
    private static final int CAPACITY_NATURE = 250;

    public NaturalGrass(String name) {
        super(name, CAPACITY_NATURE);
    }

    @Override
    public String getInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s (%s):", getName(), "NaturalGrass")).append(System.lineSeparator());
        builder.append("Player: ");
        if (getPlayers().size() == 0) {
            builder.append("none").append(System.lineSeparator());
        } else {
            builder.append(getPlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.joining(" "))).append(System.lineSeparator());
        }
        builder.append(String.format("Supplement: %d", getSupplement().size())).append(System.lineSeparator());
        builder.append(String.format("Energy: %d", sumEnergy()));
        return builder.toString().trim();
    }
}

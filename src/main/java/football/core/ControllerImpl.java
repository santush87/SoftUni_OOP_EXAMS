package football.core;


import football.common.ConstantMessages;
import football.common.ExceptionMessages;
import football.entities.field.ArtificialTurf;
import football.entities.field.Field;
import football.entities.field.NaturalGrass;
import football.entities.player.Men;
import football.entities.player.Player;
import football.entities.player.Women;
import football.entities.supplement.Liquid;
import football.entities.supplement.Powdered;
import football.entities.supplement.Supplement;
import football.repositories.SupplementRepository;
import football.repositories.SupplementRepositoryImpl;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private SupplementRepository supplement;
    private Collection<Field> fields;

    public ControllerImpl() {
        this.supplement = new SupplementRepositoryImpl();
        this.fields = new ArrayList<>();
    }

    @Override
    public String addField(String fieldType, String fieldName) {
        Field theField;
        switch (fieldType) {
            case "ArtificialTurf":
                theField = new ArtificialTurf(fieldName);
                break;
            case "NaturalGrass":
                theField = new NaturalGrass(fieldName);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_FIELD_TYPE);
        }

        this.fields.add(theField);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FIELD_TYPE, fieldType);
    }

    @Override
    public String deliverySupplement(String type) {
        Supplement supplement;
        switch (type) {
            case "Powdered":
                supplement = new Powdered();
                break;
            case "Liquid":
                supplement = new Liquid();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_SUPPLEMENT_TYPE);
        }

        this.supplement.add(supplement);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_TYPE, type);
    }

    @Override
    public String supplementForField(String fieldName, String supplementType) {
        Supplement supplement1 = this.supplement.findByType(supplementType);
        if (supplement1 == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_SUPPLEMENT_FOUND, supplementType));
        }
        Field field = this.fields.stream()
                .filter(s -> s.getName().equals(fieldName))
                .findFirst()
                .orElse(null);
        field.addSupplement(supplement1);
        this.supplement.remove(supplement1);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_SUPPLEMENT_IN_FIELD, supplementType, fieldName);
    }

    @Override
    public String addPlayer(String fieldName, String playerType, String playerName, String nationality, int strength) {
        Player player;
        switch (playerType) {
            case "Men":
                player = new Men(playerName, nationality, strength);
                break;
            case "Women":
                player = new Women(playerName, nationality, strength);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_PLAYER_TYPE);
        }

        Field field = findFild(fieldName);
        String theType = field.getClass().getSimpleName();
        if (theType.equals("ArtificialTurf") && player.getClass().getSimpleName().equals("Women")) {
            field.addPlayer(player);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType, fieldName);
        } else if (theType.equals("NaturalGrass") && player.getClass().getSimpleName().equals("Men")) {
            field.addPlayer(player);
            return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER_IN_FIELD, playerType, fieldName);
        }

        return ConstantMessages.FIELD_NOT_SUITABLE;
    }

    @Override
    public String dragPlayer(String fieldName) {
        Field field = findFild(fieldName);
        int players = field.getPlayers().size();
        return String.format(ConstantMessages.PLAYER_DRAG, players);
    }

    @Override
    public String calculateStrength(String fieldName) {
        Field field = findFild(fieldName);
        int sum = field.getPlayers().stream()
                .mapToInt(Player::getStrength)
                .sum();
        return String.format(ConstantMessages.STRENGTH_FIELD, fieldName, sum);
    }

    @Override
    public String getStatistics() {
        StringBuilder builder = new StringBuilder();
        for (Field field : this.fields) {
            builder.append(field.getInfo()).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    private Field findFild(String fieldName) {
        return this.fields.stream()
                .filter(s -> s.getName().equals(fieldName))
                .findFirst()
                .orElse(null);
    }
}

package glacialExpedition.core;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.Repository;
import glacialExpedition.repositories.StateRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ControllerImpl implements Controller {
    private final Repository<State> states;
    private final Repository<Explorer> explorers;
    private int explorerStates;

    public ControllerImpl() {
        this.states = new StateRepository();
        this.explorers = new ExplorerRepository();
        this.explorerStates = 0;
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        switch (type) {
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.EXPLORER_INVALID_TYPE);
        }

        this.explorers.add(explorer);
        return String.format(ConstantMessages.EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        StateImpl state = new StateImpl(stateName);
        state.setExhibits(List.of(exhibits));
        this.states.add(state);
        return String.format(ConstantMessages.STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer explorer = this.explorers.byName(explorerName);
        if (explorer == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        this.explorers.remove(explorer);
        return String.format(ConstantMessages.EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        State state = this.states.byName(stateName);
        Mission mission = new MissionImpl();
        List<Explorer> above50 = this.explorers.getCollection()
                .stream()
                .filter(s -> s.getEnergy() > 50)
                .collect(Collectors.toList());
        if (above50.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.STATE_EXPLORERS_DOES_NOT_EXISTS);
        } else {
            mission.explore(state, above50);
        }
        long retiredExplorers = this.explorers.getCollection()
                .stream()
                .filter(s -> s.getEnergy() == 0)
                .count();
        explorerStates++;

        return String.format(ConstantMessages.STATE_EXPLORER, stateName, retiredExplorers);
    }

    @Override
    public String finalResult() {
        StringBuilder builder = new StringBuilder();


            //"{exploredStatesCount} states were explored.
            builder.append(String.format(ConstantMessages.FINAL_STATE_EXPLORED, explorerStates)).append(System.lineSeparator());
            builder.append(ConstantMessages.FINAL_EXPLORER_INFO).append(System.lineSeparator());
            builder.append(explorers.toString());

        return builder.toString().trim();
    }
}

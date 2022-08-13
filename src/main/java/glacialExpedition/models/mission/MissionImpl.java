package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.ArrayDeque;
import java.util.Collection;

public class MissionImpl implements Mission {
    public MissionImpl() {
    }

    @Override
    public void explore(State state, Collection<Explorer> explorers) {
        Collection<String> stateExhibits = new ArrayDeque<>(state.getExhibits());
        for (Explorer currExplorer : explorers) {
            while (!stateExhibits.isEmpty() && currExplorer.canSearch()) {
                currExplorer.search();
                String currExhibit = stateExhibits.iterator().next();
                currExplorer.getSuitcase().getExhibits().add(currExhibit);
                stateExhibits.remove(currExhibit);
            }
        }
    }
}

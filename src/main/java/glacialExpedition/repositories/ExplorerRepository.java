package glacialExpedition.repositories;

import glacialExpedition.models.explorers.Explorer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ExplorerRepository implements Repository<Explorer>{
    private final Collection<Explorer> explorers;

    public ExplorerRepository() {
        this.explorers = new ArrayList<>();
    }

    @Override
    public Collection<Explorer> getCollection() {
        return Collections.unmodifiableCollection(explorers);
    }

    @Override
    public void add(Explorer explorer) {
        Explorer explorer1 = byName(explorer.getName());
        if (explorer1 == null){
            this.explorers.add(explorer);
        }
    }

    @Override
    public boolean remove(Explorer explorer) {
        return this.explorers.remove(explorer);
    }

    @Override
    public Explorer byName(String name) {
        return this.explorers
                .stream()
                .filter(s->s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}

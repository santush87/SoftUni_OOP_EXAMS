package fairyShop.repositories;

import fairyShop.models.Helper;
import fairyShop.models.Present;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PresentRepository implements Repository<Present>{
    private final Collection<Present> presents;

    public PresentRepository() {
        this.presents = new ArrayList<>();
    }


    @Override
    public Collection<Present> getModels() {
        return Collections.unmodifiableCollection(this.presents);
    }

    @Override
    public void add(Present present) {
        boolean isThere = false;
        for (Present currPresent : this.presents) {
            if (currPresent.getName().equals(present.getName())){
                isThere = true;
            }
        }
        if (!isThere){
            this.presents.add(present);
        }
    }

    @Override
    public boolean remove(Present present) {
        return this.presents.remove(present);
    }

    @Override
    public Present findByName(String name) {
        return this.presents.stream()
                .filter(s->s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}

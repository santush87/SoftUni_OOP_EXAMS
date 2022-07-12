package fairyShop.repositories;

import fairyShop.models.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HelperRepository implements Repository<Helper>{
    private final Collection<Helper> helpers;

    public HelperRepository() {
        this.helpers = new ArrayList<>();
    }

    @Override
    public Collection<Helper> getModels() {
        return Collections.unmodifiableCollection(this.helpers);
    }

    @Override
    public void add(Helper helper) {
        boolean isThere = false;
        for (Helper currHelper : this.helpers) {
            if (currHelper.getName().equals(helper.getName())){
                isThere = true;
            }
        }
        if (!isThere){
            this.helpers.add(helper);
        }
    }

    @Override
    public boolean remove(Helper helper) {
        return this.helpers.remove(helper);
    }

    @Override
    public Helper findByName(String name) {
        return this.helpers.stream()
                .filter(s->s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}

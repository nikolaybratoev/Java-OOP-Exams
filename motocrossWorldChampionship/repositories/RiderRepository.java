package repositories;

import common.ExceptionMessages;
import entities.interfaces.Rider;
import repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RiderRepository implements Repository<Rider> {
    private Collection<Rider> riders;

    public RiderRepository() {
        this.riders = new ArrayList<>();
    }

    @Override
    public Rider getByName(String name) {
        return this.riders.stream()
                .filter(motorcycle -> motorcycle.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Rider> getAll() {
        return Collections.unmodifiableCollection(this.riders);
    }

    @Override
    public void add(Rider model) {
        if (this.riders.contains(model)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RIDER_EXISTS, model.getName()));
        }

        this.riders.add(model);
    }

    @Override
    public boolean remove(Rider model) {
        return this.riders.remove(model);
    }
}

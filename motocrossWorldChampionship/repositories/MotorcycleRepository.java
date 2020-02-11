package repositories;

import common.ExceptionMessages;
import entities.interfaces.Motorcycle;
import repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MotorcycleRepository implements Repository<Motorcycle> {
    private Collection<Motorcycle> motorcycles;

    public MotorcycleRepository() {
        this.motorcycles = new ArrayList<>();
    }

    @Override
    public Motorcycle getByName(String name) {
        return this.motorcycles.stream()
                .filter(motorcycle -> motorcycle.getModel().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Motorcycle> getAll() {
        return Collections.unmodifiableCollection(this.motorcycles);
    }

    @Override
    public void add(Motorcycle model) {
        if (this.motorcycles.contains(model)) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.MOTORCYCLE_EXISTS, model.getModel()));
        }

        this.motorcycles.add(model);
    }

    @Override
    public boolean remove(Motorcycle model) {
        return this.motorcycles.remove(model);
    }
}

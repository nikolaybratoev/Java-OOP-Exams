package core;

import common.ExceptionMessages;
import common.OutputMessages;
import core.interfaces.ChampionshipController;
import entities.PowerMotorcycle;
import entities.RaceImpl;
import entities.RiderImpl;
import entities.SpeedMotorcycle;
import entities.interfaces.Motorcycle;
import entities.interfaces.Race;
import entities.interfaces.Rider;
import repositories.interfaces.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChampionshipControllerImpl implements ChampionshipController {
    private Repository<Rider> riders;
    private Repository<Motorcycle> motorcycles;
    private Repository<Race> races;

    public ChampionshipControllerImpl(Repository<Rider> riders, Repository<Motorcycle> motorcycles, Repository<Race> races) {
        this.riders = riders;
        this.motorcycles = motorcycles;
        this.races = races;
    }

    @Override
    public String createRider(String riderName) {
        Rider rider = this.riders.getByName(riderName);

        if (rider != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RIDER_EXISTS, riderName));
        } else {
            rider = new RiderImpl(riderName);
        }

        this.riders.add(rider);
        return String.format(OutputMessages.RIDER_CREATED, riderName);
    }

    @Override
    public String createMotorcycle(String type, String model, int horsePower) {
        Motorcycle motorcycle = this.motorcycles.getByName(model);

        String message = null;
        if (motorcycle != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.MOTORCYCLE_EXISTS, model));
        } else {
            if (type.equalsIgnoreCase("Power")) {
                motorcycle = new PowerMotorcycle(model, horsePower);
                this.motorcycles.add(motorcycle);
                message = String.format(OutputMessages.MOTORCYCLE_CREATED, "PowerMotorcycle", model);
            } else if (type.equalsIgnoreCase("Speed")) {
                motorcycle = new SpeedMotorcycle(model, horsePower);
                this.motorcycles.add(motorcycle);
                message = String.format(OutputMessages.MOTORCYCLE_CREATED, "SpeedMotorcycle", model);
            }
        }
        return message;
    }

    @Override
    public String addMotorcycleToRider(String riderName, String motorcycleModel) {
        Rider rider = this.riders.getByName(riderName);
        Motorcycle motorcycle = this.motorcycles.getByName(motorcycleModel);

        if (rider == null) {
            throw new NullPointerException(String.format(ExceptionMessages.RIDER_NOT_FOUND, riderName));
        }

        if (motorcycle == null) {
            throw new NullPointerException(String.format(ExceptionMessages.MOTORCYCLE_NOT_FOUND, motorcycleModel));
        }

        rider.addMotorcycle(motorcycle);
        return String.format(OutputMessages.MOTORCYCLE_ADDED, riderName, motorcycleModel);
    }

    @Override
    public String addRiderToRace(String raceName, String riderName) {
        Race race = this.races.getByName(raceName);
        Rider rider = this.riders.getByName(riderName);

        if (race == null) {
            throw new NullPointerException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }

        if (rider == null) {
            throw new NullPointerException(String.format(ExceptionMessages.RIDER_NOT_FOUND, riderName));
        }

        race.addRider(rider);
        return String.format(OutputMessages.RIDER_ADDED, riderName, raceName);
    }

    @Override
    public String startRace(String raceName) {
        Race race = this.races.getByName(raceName);

        if (race == null) {
            throw new NullPointerException(String.format(ExceptionMessages.RACE_NOT_FOUND, raceName));
        }

        List<Rider> riders = new ArrayList<>(race.getRiders());

        if (riders.size() < 3) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_INVALID, raceName, 3));
        }

        riders = riders.stream()
                .sorted((a, b) -> {
                    double sort = b.getMotorcycle().calculateRacePoints(race.getLaps()) -
                            a.getMotorcycle().calculateRacePoints(race.getLaps());
                    return (int) (sort * 100);
                }).collect(Collectors.toList());

        riders.get(0).winRace();

        this.races.remove(race);

        return String.format(OutputMessages.RIDER_FIRST_POSITION, riders.get(0).getName(), raceName) +
                System.lineSeparator() +
                String.format(OutputMessages.RIDER_SECOND_POSITION, riders.get(1).getName(), raceName) +
                System.lineSeparator() +
                String.format(OutputMessages.RIDER_THIRD_POSITION, riders.get(2).getName(), raceName);
    }

    @Override
    public String createRace(String name, int laps) {
        Race race = this.races.getByName(name);

        if (race != null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.RACE_EXISTS, name));
        }

        race = new RaceImpl(name, laps);
        this.races.add(race);
        return String.format(OutputMessages.RACE_CREATED, name);
    }
}

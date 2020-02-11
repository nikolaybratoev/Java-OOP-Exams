package entities;

import common.ExceptionMessages;
import entities.interfaces.Motorcycle;

public abstract class MotorcycleImpl implements Motorcycle {
    private static final int MIN_HORSE_POWER_MOTORCYCLE = 70;
    private static final int MAX_HORSE_POWER_MOTORCYCLE = 100;

    private static final int MIN_HORSE_POWER_SPEED_MOTORCYCLE = 50;
    private static final int MAX_HORSE_POWER_SPEED_MOTORCYCLE = 69;

    private String model;
    private int horsePower;
    private double cubicCentimeters;

    protected MotorcycleImpl(String model, int horsePower, double cubicCentimeters) {
        this.setModel(model);
        this.setHorsePower(horsePower);
        this.cubicCentimeters = cubicCentimeters;
    }

    private void setHorsePower(int horsePower) {
        if (this.getModel().getClass().getSimpleName().equalsIgnoreCase("PowerMotorcycle")) {
            if (horsePower < MIN_HORSE_POWER_MOTORCYCLE || horsePower > MAX_HORSE_POWER_MOTORCYCLE) {
                throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
            }
            this.horsePower = horsePower;
        } else if (this.getModel().getClass().getSimpleName().equalsIgnoreCase("SpeedMotorcycle")) {
            if (horsePower < MIN_HORSE_POWER_SPEED_MOTORCYCLE || horsePower > MAX_HORSE_POWER_SPEED_MOTORCYCLE) {
                throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_HORSE_POWER, horsePower));
            }
            this.horsePower = horsePower;
        }
    }

    private void setModel(String model) {
        if (model == null || model.trim().isEmpty() || model.length() < 4) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.INVALID_MODEL, model, 4));
        }
        this.model = model;
    }

    @Override
    public String getModel() {
        return this.model;
    }

    @Override
    public int getHorsePower() {
        return this.horsePower;
    }

    @Override
    public double getCubicCentimeters() {
        return this.cubicCentimeters;
    }

    @Override
    public double calculateRacePoints(int laps) {
        return this.getCubicCentimeters() / this.getHorsePower() * laps;
    }
}

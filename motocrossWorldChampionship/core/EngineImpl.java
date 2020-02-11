package core;

import core.interfaces.ChampionshipController;
import core.interfaces.Engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class EngineImpl implements Engine{
    private final BufferedReader reader;
    private ChampionshipController championshipController;

    public EngineImpl(ChampionshipController championshipController) {
        this.championshipController = championshipController;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        while (true) {
            String result = null;

            try {
                result = processInput();

                if (result.equals("End")) {
                    break;
                }
            } catch (NullPointerException | IllegalArgumentException | IOException e) {
                result = e.getMessage();
            }
            System.out.println(result);
        }
    }

    private String processInput() throws IOException {
        String input = this.reader.readLine();
        String[] tokens = input.split("\\s+");

        String command = tokens[0];
        String result = null;

        String[] data = Arrays.stream(tokens).skip(1).toArray(String[]::new);

        switch (command) {
            case "CreateRider":
                result = this.championshipController.createRider(data[0]);
                break;
            case "CreateMotorcycle":
                result = this.championshipController.createMotorcycle(data[0], data[1], Integer.parseInt(data[2]));
                break;
            case "CreateRace":
                result = this.championshipController.createRace(data[0], Integer.parseInt(data[1]));
                break;
            case "AddMotorcycleToRider":
                result = this.championshipController.addMotorcycleToRider(data[0], data[1]);
                break;
            case "AddRiderToRace":
                result = this.championshipController.addRiderToRace(data[0], data[1]);
                break;
            case "StartRace":
                result = this.championshipController.startRace(data[0]);
                break;
            case "End":
                result = "End";
                break;
        }

        return result;
    }
}

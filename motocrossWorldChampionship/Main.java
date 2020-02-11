import core.ChampionshipControllerImpl;
import core.EngineImpl;
import core.interfaces.ChampionshipController;
import core.interfaces.Engine;
import repositories.MotorcycleRepository;
import repositories.RaceRepository;
import repositories.RiderRepository;

public class Main {
    public static void main(String[] args) {
        ChampionshipController championshipController = new ChampionshipControllerImpl(new RiderRepository(),
                new MotorcycleRepository(), new RaceRepository());
        Engine engine = new EngineImpl(championshipController);
        engine.run();
    }
}

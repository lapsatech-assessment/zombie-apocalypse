package test.zombieApocalypse;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleWriterZombieApocalypseGameEventHandler extends ZombieApocalypseGameEventHandler {

    @Override
    public void gameStarting() {
        System.out.println("------------");
        System.out.println("Game has started");
    }

    @Override
    public void initalZombiesPositions(Stream<Position> positions) {
        System.out.println(String.format("Initial zombies positions are: %s", positionsAsString(positions)));
    }

    @Override
    public void initalCreaturesPositions(Stream<Position> positions) {
        System.out.println(String.format("Initial creatures positions are: %s", positionsAsString(positions)));
    }

    @Override
    public void zombieNextMovement(Position previousPosition, Position currentPosition, int zombieId) {
        System.out.println(
                String.format("Zombie %s has moved from %s to %s", zombieId, previousPosition, currentPosition));
    }

    @Override
    public void creatureConverted(Position newZombiePosition, int newZombieId, int convertedCount) {
        System.out.println(String.format("Zombie %s has been created", newZombieId));
    }

    @Override
    public void zombieFinishedMovement(Position finalZombiePosition, int zombieId) {
        System.out.println(String.format("Zombie %s has finished its movement at %s", zombieId, finalZombiePosition));
    }

    @Override
    public void gameFinished(int zombiesScore, Stream<Position> zombies, Stream<Position> survivedCreatures) {
        System.out.println(String.format("Zombies finished the game at positions: %s", positionsAsString(zombies)));
        System.out.println(String.format("Creatures survived at positions: %s", positionsAsString(survivedCreatures)));
        System.out.println(String.format("Zombies score is %s", zombiesScore));
        System.out.println("Game is over");
        System.out.println("------------");
    }

    private static String positionsAsString(Stream<Position> positions) {
        return Optional.of(positions.map(Position::toString).collect(Collectors.joining(", ")))
                .filter(x -> !x.isEmpty())
                .orElse("none");
    }

}

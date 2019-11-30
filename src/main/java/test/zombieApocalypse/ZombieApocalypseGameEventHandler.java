package test.zombieApocalypse;

import java.util.stream.Stream;

public class ZombieApocalypseGameEventHandler {

    public void initalZombiesPositions(Stream<Position> positions) {
    }

    public void initalCreaturesPositions(Stream<Position> positions) {
    }

    public void zombieNextMovement(Position previousPosition, Position currentPosition, int zombieId) {
    }

    public void creatureConverted(Position newZombiePosition, int newZombieId, int convertedCount) {
    }

    public void zombieFinishedMovement(Position finalZombiePosition, int zombieId) {
    }

    public void gameFinished(int zombiesScore, Stream<Position> zombies, Stream<Position> survivedCreatures) {
    }

    public void gameStarting() {
    }

}

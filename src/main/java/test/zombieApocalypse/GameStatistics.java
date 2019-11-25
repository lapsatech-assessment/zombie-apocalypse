package test.zombieApocalypse;

import java.util.List;

public class GameStatistics {

    private final int zombiesScore;
    private final List<Position> zombiesPositions;

    public GameStatistics(int zombiesScore, List<Position> zombiesPositions) {
        this.zombiesScore = zombiesScore;
        this.zombiesPositions = zombiesPositions;
    }

    public int getZombiesScore() {
        return zombiesScore;
    }

    public List<Position> getZombiesPositions() {
        return zombiesPositions;
    }

}

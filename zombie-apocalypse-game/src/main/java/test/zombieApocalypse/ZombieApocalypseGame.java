package test.zombieApocalypse;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class ZombieApocalypseGame {

    public static ZombieApocalypseGame of(Configuration configuration) {
        return new ZombieApocalypseGame(configuration);
    }

    private final Configuration configuration;

    final BorderRule borderRule;
    final List<Movement> movements;
    int zombieIdCounter = 0;

    private ZombieApocalypseGame(Configuration configuration) {
        this.configuration = configuration;
        this.borderRule = configuration.getBoardRule();
        this.movements = configuration.getZombieMovements().collect(toList());
    }

    public GameStatistics play() {
        return play(ZombieApocalypseGameEventHandler.empty());
    }

    public GameStatistics play(ZombieApocalypseGameEventHandler zombieApocalypseGameEventHandler) {
        zombieApocalypseGameEventHandler.gameStarting();
        final Deque<MovableMember> zombiesQueue = configuration.getInitialZombiePositions()
                .map(MovableMember::new)
                .collect(toCollection(LinkedList::new));

        zombieApocalypseGameEventHandler
                .initalZombiesPositions(zombiesQueue.stream().map(MovableMember::getCurrentPosition));

        final List<Position> creatures = configuration.getInitialCreaturePositions()
                .collect(toList());

        zombieApocalypseGameEventHandler.initalCreaturesPositions(creatures.stream());

        int convertedCount = 0;
        final List<Position> zombiesFinalPositions = new ArrayList<>();

        while (!zombiesQueue.isEmpty()) {
            final MovableMember zombie = zombiesQueue.removeFirst();
            if (zombie.nextStep()) {
                zombiesQueue.addLast(zombie);
                zombieApocalypseGameEventHandler.zombieNextMovement(zombie.getPreviousPosition(),
                        zombie.getCurrentPosition(), zombie.id);
                while (creatures.contains(zombie.currentPosition)) {
                    creatures.remove(zombie.currentPosition);
                    final MovableMember newZombie = new MovableMember(zombie.currentPosition);
                    zombiesQueue.addLast(newZombie);
                    convertedCount++;
                    zombieApocalypseGameEventHandler.creatureConverted(newZombie.currentPosition, newZombie.id,
                            convertedCount);
                }
            } else {
                zombiesFinalPositions.add(zombie.currentPosition);
                zombieApocalypseGameEventHandler.zombieFinishedMovement(zombie.currentPosition,
                        zombie.id);
            }
        }

        zombieApocalypseGameEventHandler.gameFinished(convertedCount, zombiesFinalPositions.stream(),
                creatures.stream());
        return new GameStatistics(convertedCount, zombiesFinalPositions);
    }

    private class MovableMember {

        private final int id = ++zombieIdCounter;

        private final Queue<Movement> pending;
        private Position currentPosition;
        private Position previousPosition = null;

        private MovableMember(Position initialPosition) {
            this.pending = new LinkedList<>(movements);
            this.currentPosition = initialPosition;
        }

        boolean nextStep() {
            if (pending.isEmpty()) {
                return false;
            }
            previousPosition = currentPosition;
            currentPosition = currentPosition.transform()
                    .apply(pending.poll())
                    .apply(borderRule)
                    .complete();
            return true;
        }

        private Position getCurrentPosition() {
            return currentPosition;
        }

        private Position getPreviousPosition() {
            return previousPosition;
        }
    }
}

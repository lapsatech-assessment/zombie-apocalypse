package test.zombieApocalypse;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ZombieApocalypseGame {

    public static ZombieApocalypseGame of(Configuration configuration) {
        return new ZombieApocalypseGame(configuration);
    }

    private final Configuration configuration;

    private ZombieApocalypseGame(Configuration configuration) {
        this.configuration = configuration;
    }

    public GameStatistics play() {

        final List<Movement> movements = configuration.getZombieMovements().collect(toList());
        final Board board = configuration.getBoard();
        final BorderRule borderRule = BorderRule.cyclingBorder(board);

        final Deque<Group> zombiesQueue = configuration.getInitialZombiePositions()
                .collect(groupingBy(p -> p, reducing(0, e -> 1, Integer::sum)))
                .entrySet()
                .stream()
                .map(Group::new)
                .collect(toCollection(LinkedList::new));

        final Map<Position, Group> creaturesGrouped = configuration.getInitialCreaturePositions()
                .collect(groupingBy(p -> p, reducing(0, e -> 1, Integer::sum)))
                .entrySet()
                .stream()
                .map(Group::new)
                .collect(toMap(g -> g.position, g -> g));

        final Map<Position, Integer> zombiesResult = new HashMap<>();

        int convertedCount = 0;
        Group groupOfZombies;
        while ((groupOfZombies = zombiesQueue.pollFirst()) != null) {
            Position zombie = groupOfZombies.position;
            for (Movement movement : movements) {
                zombie = zombie.transform()
                        .apply(movement)
                        .apply(borderRule)
                        .complete();
                final Group groupOfCreatures;
                if ((groupOfCreatures = creaturesGrouped.remove(zombie)) != null) {
                    zombiesQueue.offerLast(groupOfCreatures);
                    convertedCount += groupOfCreatures.count;
                }
            }
            final int groupOfZombiesCount = groupOfZombies.count;
            zombiesResult.compute(zombie, (k, v) -> (v != null ? v.intValue() : 0) + groupOfZombiesCount);
        }

        final List<Position> allTheZombies = zombiesResult.entrySet()
                .stream()
                .flatMap(e -> Collections.nCopies(e.getValue(), e.getKey()).stream())
                .collect(Collectors.toList());

        return new GameStatistics(convertedCount, allTheZombies);
    }


    private class Group {
        private final Position position;
        private final int count;

        private Group(Map.Entry<Position, Integer> entry) {
            this.position = entry.getKey();
            this.count = entry.getValue();
        }
    }

}

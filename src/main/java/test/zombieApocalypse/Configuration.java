package test.zombieApocalypse;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class Configuration {

    public static Configuration parse(InputStream input) throws NumberFormatException, IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(input));

        final Board board = Board.square(Integer.parseInt(br.readLine()));

        final List<Position> initialZombiePositions = parsePositionsLine(board, br.readLine()).collect(toList());
        final List<Position> initialCreaturePositions = parsePositionsLine(board, br.readLine()).collect(toList());
        final List<Movement> movements = parseMovementsLine(br.readLine()).collect(toList());

        return Configuration.of(board, initialZombiePositions, initialCreaturePositions, movements);
    }

    private static final Pattern POSITIONS_PATTERN = Pattern.compile("^ *\\( *(\\d+) *\\, *(\\d+) *\\) *");

    private static Stream<Position> parsePositionsLine(Board board, String s) {
        Stream.Builder<Position> builder = Stream.builder();
        final Matcher matcher = POSITIONS_PATTERN.matcher(s);
        while (matcher.lookingAt()) {
            final MatchResult result = matcher.toMatchResult();
            final int x = Integer.parseInt(result.group(1));
            final int y = Integer.parseInt(result.group(2));
            final Position pos = Position.of(x, y);
            board.validate(pos);
            builder.add(pos);
            s = matcher.replaceFirst("");
            matcher.reset(s);
        }
        if (!s.isEmpty()) {
            throw new IllegalArgumentException("Invalid position definition");
        }
        return builder.build();
    }

    public static Configuration of(Board board, List<Position> initialZombiePositions,
            List<Position> initialCreaturePositions,
            List<Movement> zombieMovements) {
        return new Configuration(board, initialZombiePositions, initialCreaturePositions, zombieMovements);
    }

    private static Stream<Movement> parseMovementsLine(String s) {
        return s.chars().mapToObj(c -> {
            switch (c) {
            case 'U':
                return Movement.up();
            case 'D':
                return Movement.down();
            case 'L':
                return Movement.left();
            case 'R':
                return Movement.right();
            default:
                throw new IllegalArgumentException("Invalid movement definition");
            }
        });
    }

    private final Board board;
    private final List<Position> initialZombiePositions;
    private final List<Position> initialCreaturePositions;
    private final List<Movement> zombieMovements;

    private Configuration(Board board, List<Position> initialZombiePositions,
            List<Position> initialCreaturePositions,
            List<Movement> zombieMovements) {
        this.board = board;
        this.initialZombiePositions = new ArrayList<>(initialZombiePositions);
        this.initialCreaturePositions = new ArrayList<>(initialCreaturePositions);
        this.zombieMovements = new ArrayList<>(zombieMovements);
    }

    public Board getBoard() {
        return board;
    }

    public Stream<Position> getInitialZombiePositions() {
        return initialZombiePositions.stream();
    }

    public Stream<Position> getInitialCreaturePositions() {
        return initialCreaturePositions.parallelStream();
    }

    public Stream<Movement> getZombieMovements() {
        return zombieMovements.stream();
    }

    public BorderRule getBoardRule() {
        return BorderRule.wrappingBorder(board);
    }
}

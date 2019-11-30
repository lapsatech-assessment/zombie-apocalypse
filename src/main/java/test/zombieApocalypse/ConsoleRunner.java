package test.zombieApocalypse;

import java.io.IOException;

public final class ConsoleRunner {

    public static void main(String[] args) throws IOException {
        final Configuration configuration;
        try {
            configuration = Configuration.parse(System.in);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getClass().getSimpleName() + " : " + e.getMessage());
            System.exit(1);
            throw new Error(); // effectively dead code
        }
        final ZombieApocalypseGame zombieApocalypseGame = ZombieApocalypseGame.of(configuration);
        final GameStatistics statistics = zombieApocalypseGame.play(new ConsoleWriterZombieApocalypseGameEventHandler());
        System.out.println("zombies score: " + statistics.getZombiesScore());
        System.out.println("zombies positions:");
        statistics.getZombiesPositions()
                .stream()
                .map(pos -> "(" + pos.getX() + ',' + pos.getY() + ')')
                .forEach(System.out::print);
        System.out.println();
    }
}

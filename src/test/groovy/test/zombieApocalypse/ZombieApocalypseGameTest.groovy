package test.zombieApocalypse

import spock.lang.Specification

class ZombieApocalypseGameTest extends Specification {

    def 'run simple case from the task statement'() {
        given:
        def board = Board.square(4);
        def initialZombiePositions = [Position.of(2, 1)] as List;
        def initialCreaturePositions = [
            Position.of(0, 1),
            Position.of(1, 2),
            Position.of(3, 1)] as List;
        def movements = [
            Movement.down(),
            Movement.left(),
            Movement.up(),
            Movement.up(),
            Movement.right(),
            Movement.right()] as List;

        def configuration = Configuration.of(board, initialZombiePositions, initialCreaturePositions, movements);
        def zombieApocalypse = new ZombieApocalypseGame(configuration);

        when:
        def result = zombieApocalypse.play()

        then:
        result.zombiesScore == 3
        result.getZombiesPositions().sort() == [
            Position.of(3,0),
            Position.of(2,1),
            Position.of(1,0),
            Position.of(0,0)
        ].sort()
    }
}

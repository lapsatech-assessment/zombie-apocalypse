package test.zombieApocalypse

import spock.lang.Specification

class ConfigurationTest extends Specification {

    def 'Check that configuration stream loader parses config'() {
        given:
        InputStream is = ConfigurationTest.class.getResourceAsStream("/test-input.txt")

        when:
        def configuration = Configuration.parse(is)

        then:
        configuration
        configuration.board.width == 4
        configuration.board.height == 4
        configuration.initialZombiePositions.toList() == [Position.of(2,1)]
        configuration.initialCreaturePositions.toList() == [
            Position.of(0,1),
            Position.of(1,2),
            Position.of(3,1)
        ]
        configuration.zombieMovements.toList() == [
            Movement.down(),
            Movement.left(),
            Movement.up(),
            Movement.up(),
            Movement.right(),
            Movement.right()
        ]
    }
}

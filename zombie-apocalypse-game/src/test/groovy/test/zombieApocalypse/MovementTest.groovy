package test.zombieApocalypse

import spock.lang.Specification
import spock.lang.Unroll
import test.zombieApocalypse.Board

class MovementTest extends Specification{

    @Unroll
    def 'check that movement #name transforms correctly'() {
        when:
        def builder = movement.transform(Position.of(initialX, initialY).transform())

        then:
        builder.x == expectingX
        builder.y == expectingY
        
        where:
        name | movement         | initialX | initialY | expectingX | expectingY
        'U'  | Movement.up()    |        0 |        0 |          0 |         -1
        'D'  | Movement.down()  |        0 |        0 |          0 |          1
        'L'  | Movement.left()  |        0 |        0 |         -1 |          0
        'R'  | Movement.right() |        0 |        0 |          1 |          0
    }
}

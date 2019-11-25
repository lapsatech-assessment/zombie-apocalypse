package test.zombieApocalypse

import spock.lang.Specification

class BorderRuleTest extends Specification {
    
    def 'Check that wrapping rules works correctly'() {
        given:
        def rules = BorderRule.cyclingBorder(Board.square(3));
        
        when:
        def positionBuilder = rules.transform(Position.of(initialX, initialY).transform())
        
        then:
        positionBuilder
        positionBuilder.x == expectingX
        positionBuilder.y == expectingY
        
        where:
        initialX | initialY | expectingX | expectingY
               0 |        0 |          0 |          0
              -1 |        0 |          2 |          0
               3 |        0 |          0 |          0
               0 |        0 |          0 |          0
               0 |       -1 |          0 |          2
               0 |        3 |          0 |          0
              31 |       31 |          1 |          1
             -31 |      -31 |          2 |          2
    }
}

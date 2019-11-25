package test.zombieApocalypse

import spock.lang.Specification
import spock.lang.Unroll

class PositionTest extends Specification {

    @Unroll
    def 'Check hashcode/equals equality of the given two positions (mustEqual=#mustEqual)'() {

        when:
        def h1 = p1.hashCode()
        def h2 = p2.hashCode()

        then:
        ( mustEqual && h1 == h2 && p1.equals(p2) ) || (!mustEqual && h1 != h2 && !p1.equals(p2))

        where:
        p1                | p2                | mustEqual
        Position.of(1, 2) | Position.of(1, 2) | true
        Position.of(2, 2) | Position.of(1, 2) | false
    }
}


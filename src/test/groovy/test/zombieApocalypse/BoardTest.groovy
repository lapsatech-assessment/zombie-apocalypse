package test.zombieApocalypse

import spock.lang.Specification

class BoardTest extends Specification {

    def 'Check that creator methods creates square board correctly'() {
        given:
        def squareN = 10

        when:
        def board = Board.square(squareN);

        then:
        board
        board.width == squareN
        board.height == squareN
    }

    def 'Check that creator methods creates non-square board correctly'() {
        given:
        def width = 10
        def height = 10

        when:
        def board = Board.of(width, height);

        then:
        board
        board.width == width
        board.height == height
    }
}

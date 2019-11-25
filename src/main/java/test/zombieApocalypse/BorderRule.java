package test.zombieApocalypse;

import test.zombieApocalypse.Position.Transformation;
import test.zombieApocalypse.Position.Transformer;

public final class BorderRule implements Transformer {

    public static BorderRule cyclingBorder(Board board) {
        return new BorderRule(board);
    }

    private final Board board;

    private BorderRule(Board board) {
        this.board = board;
    }

    private int wrapAxis(int value, int maxSize) {
        return (maxSize + (value % maxSize)) % maxSize;
    }

    @Override
    public Transformation transform(Transformation transformation) {
        transformation.setX(wrapAxis(transformation.getX(), board.getWidth()));
        transformation.setY(wrapAxis(transformation.getY(), board.getHeight()));
        return transformation;
    }
}

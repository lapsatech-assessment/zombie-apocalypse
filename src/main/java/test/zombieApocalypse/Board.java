package test.zombieApocalypse;

public final class Board {

    public static Board of(int width, int height) {
        return new Board(width, height);
    }

    public static Board square(int dimension) {
        return new Board(dimension, dimension);
    }

    private final int width;
    private final int height;

    private Board(int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Board width should by position non-zero integer");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Board height should by position non-zero integer");
        }
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isValid(Position pos) {
        return pos.getX() < width && pos.getX() >= 0 && pos.getY() < height && pos.getY() >= 0;
    }

    public void validate(Position pos) {
        if (!isValid(pos)) {
            throw new IllegalArgumentException("Position definition is invalid for this board");
        }
    }
}

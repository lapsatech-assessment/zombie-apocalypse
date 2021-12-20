package test.zombieApocalypse;

import java.util.Comparator;

public final class Position implements Comparable<Position> {

    @FunctionalInterface
    public interface Transformer {
        Transformation transform(Transformation builder);
    }

    public static final class Transformation {

        private int x;
        private int y;

        private Transformation(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Transformation apply(Transformer transformer) {
            return transformer.transform(this);
        }

        public Position complete() {
            return Position.of(x, y);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

    }

    public static Position of(final int x, final int y) {
        return new Position(x, y);
    }

    private final int x;
    private final int y;

    private Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Transformation transform() {
        return new Transformation(x, y);
    }

    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    private static final Comparator<Position> COMPARATOR = Comparator.comparingInt(Position::getX).thenComparingInt(Position::getY);

    @Override
    public int compareTo(Position o) {
        return COMPARATOR.compare(this, o);
    }
}

package test.zombieApocalypse;

import java.util.function.IntUnaryOperator;

import test.zombieApocalypse.Position.Transformation;
import test.zombieApocalypse.Position.Transformer;

public final class Movement implements Transformer {

    private final static IntUnaryOperator INCREMENTOR = i -> ++i;
    private final static IntUnaryOperator DECREMENTOR = i -> --i;
    private final static IntUnaryOperator AS_IS = i -> i;

    private static final Movement UP = new Movement(AS_IS, DECREMENTOR);
    private static final Movement DOWN = new Movement(AS_IS, INCREMENTOR);
    private static final Movement LEFT = new Movement(DECREMENTOR, AS_IS);
    private static final Movement RIGHT = new Movement(INCREMENTOR, AS_IS);

    public static Movement up() {
        return UP;
    }

    public static Movement down() {
        return DOWN;
    }

    public static Movement left() {
        return LEFT;
    }

    public static Movement right() {
        return RIGHT;
    }

    private final IntUnaryOperator xTransformation;
    private final IntUnaryOperator yTransformation;

    private Movement(IntUnaryOperator xTransformation, IntUnaryOperator yTransformation) {
        this.xTransformation = xTransformation;
        this.yTransformation = yTransformation;
    }

    @Override
    public Transformation transform(Transformation transformation) {
        transformation.setX(xTransformation.applyAsInt(transformation.getX()));
        transformation.setY(yTransformation.applyAsInt(transformation.getY()));
        return transformation;
    }
}

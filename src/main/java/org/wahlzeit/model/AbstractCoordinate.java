package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

public abstract class AbstractCoordinate implements Coordinate {
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract SphericCoordinate asSphericCoordinate();

    @Override
    public boolean isEqual(Coordinate coordinate) {
        // Assert pre-conditions and invariants
        if(coordinate == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("coordinate");

        boolean result = isEqualCore(coordinate);

        // Assert post-conditions and invariants
        // -

        return result;
    }

    private boolean isEqualCore(Coordinate coordinate) {

        // Forward call to cartesian coordinate as our normalized coordinate system to ensure
        // the commutative of equality.
        return asCartesianCoordinate().isEqual(coordinate.asCartesianCoordinate());
    }

    @Override
    public final boolean equals(Object other) {
        // This handles the case that other is a null object reference
        return other instanceof Coordinate && asCartesianCoordinate().isEqual((Coordinate)other);
    }

    @Override
    public final int hashCode() {
        return asCartesianCoordinate().internalHashCode();
    }

    public double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException {
        // Assert pre-conditions and invariants
        if(coordinate == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("coordinate");

        double result = getCartesianDistanceCore(coordinate);

        // Assert post-conditions and invariants
        assert result >= 0;

        return result;
    }

    private double getCartesianDistanceCore(Coordinate coordinate) {
        return asCartesianCoordinate().getCartesianDistance(coordinate.asCartesianCoordinate());
    }

    public double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException {
        // Assert pre-conditions and invariants
        if(coordinate == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("coordinate");

        double result = getCentralAngleCore(coordinate);

        // Assert post-conditions and invariants
        assert result >= 0 && result <= Math.PI;

        return result;
    }

    public double getCentralAngleCore(Coordinate coordinate) throws IllegalArgumentException {
        return asSphericCoordinate().getCentralAngle(coordinate.asSphericCoordinate());
    }

    public abstract String asString();
}

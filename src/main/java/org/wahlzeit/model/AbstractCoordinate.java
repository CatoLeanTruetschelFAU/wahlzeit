package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

public abstract class AbstractCoordinate implements Coordinate {
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract SphericCoordinate asSphericCoordinate();

    public abstract boolean isEqual(Coordinate coordinate);

    @Override
    public final boolean equals(Object other) {
        return other instanceof Coordinate && asCartesianCoordinate().isEqual((Coordinate)other);
    }

    @Override
    public final int hashCode() {
        return asCartesianCoordinate().internalHashCode();
    }

    public double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException {
        // Assert post-conditions and invariants
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

    @Override
    public double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException {
        return asSphericCoordinate().getCentralAngle(coordinate);
    }

    public abstract String asString();
}

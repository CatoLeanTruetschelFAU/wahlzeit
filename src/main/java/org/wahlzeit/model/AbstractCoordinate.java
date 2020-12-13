package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException;
    public abstract SphericCoordinate asSphericCoordinate();
    public abstract double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException;
    public abstract boolean isEqual(Coordinate coordinate);

    @Override
    public final boolean equals(Object other) {
        return other instanceof Coordinate && asCartesianCoordinate().isEqual((Coordinate)other);
    }

    @Override
    public final int hashCode() {
        return asCartesianCoordinate().internalHashCode();
    }

    public abstract String asString();
}

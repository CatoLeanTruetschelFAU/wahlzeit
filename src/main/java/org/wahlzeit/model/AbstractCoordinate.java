package org.wahlzeit.model;

public abstract class AbstractCoordinate implements Coordinate {
    public abstract CartesianCoordinate asCartesianCoordinate();
    public abstract double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException;
    public abstract SphericCoordinate asSphericCoordinate();
    public abstract double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException;
    public abstract boolean isEqual(Coordinate coordinate);

    public abstract String asString();
}

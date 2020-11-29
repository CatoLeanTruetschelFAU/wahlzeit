package org.wahlzeit.model;

public interface Coordinate {
    CartesianCoordinate asCartesianCoordinate();
    double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException;
    SphericCoordinate asSphericCoordinate();
    double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException;
    boolean isEqual(Coordinate coordinate);

    String asString();
}

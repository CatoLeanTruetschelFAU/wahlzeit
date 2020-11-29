package org.wahlzeit.model;

public interface Coordinate {
    CartesianCoordinate asCartesionCoordinate();
    double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException;
    SphericCoordinate asSphericCoordinate();
    double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException;
    boolean isEqual(Coordinate coordinate);
}

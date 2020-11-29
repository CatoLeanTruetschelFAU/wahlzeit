package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

public final class CartesianCoordinate implements Coordinate {
    private double fX;
    private double fY;
    private double fZ;

    private static final NumberFormat INVARIANT_FORMAT = NumberFormat.getInstance(Locale.US);

    public CartesianCoordinate() {
        this(0,0,0);
    }

    public CartesianCoordinate(double x, double y, double z) {
        checkValues(x,y, z);
        fX = x;
        fY = y;
        fZ = z;
    }

    public CartesianCoordinate(String value)
    {
        if(value == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("value");

        String[] components = value.split(" ");

        if(components.length == 0)
            return;

        if(components.length != 3)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");

        try {
            fX = INVARIANT_FORMAT.parse(components[0]).doubleValue();
            fY = INVARIANT_FORMAT.parse(components[1]).doubleValue();
            fZ = INVARIANT_FORMAT.parse(components[2]).doubleValue();
        } catch(ParseException exc) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value", exc);
        }
    }

    private void init(double x, double y, double z)
    {
        fX = x;
        fY = y;
        fZ = z;
    }

    private static void checkValues(double x, double y, double z) throws IllegalArgumentException {
        checkValueNotNanNotInfinity("x", x);
        checkValueNotNanNotInfinity("y", y);
        checkValueNotNanNotInfinity("z", z);
    }

    private static void checkValueNotNanNotInfinity(String argumentName, double value) throws IllegalArgumentException {
        if(Double.isNaN(value))
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage(argumentName, "NaN");

        if(Double.isInfinite(value))
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage(argumentName, "infinity");
    }

    public double getX(){
        return fX;
    }

    public double getY() {
        return fY;
    }

    public double getZ() {
        return fZ;
    }

    public double getCartesianDistance(Coordinate coordinate) throws IllegalArgumentException
    {
        return getDistance(coordinate.asCartesianCoordinate());
    }

    public double getDistance(CartesianCoordinate other) throws IllegalArgumentException
    {
        if(other == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("other");

        if(other == this)
            return 0;

        double distX = other.fX - fX;
        double distY = other.fY - fY;
        double distZ = other.fZ - fZ;

        return Math.sqrt(distX * distX + distY * distY + distZ * distZ);
    }

    @Override
    public double getCentralAngle(Coordinate coordinate) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public boolean isEqual(Coordinate coordinate) {
        return isEqual(coordinate.asCartesianCoordinate(), 0.000001);
    }

    public boolean isEqual(CartesianCoordinate other)
    {
        return isEqual(other, 0.000001);
    }

    public boolean isEqual(CartesianCoordinate other, double tolerance)
    {
        if(other == this)
            return true;

        double distance = getDistance(other);

        return distance <= tolerance;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Coordinate && isEqual((Coordinate)other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fX, fY, fZ);
    }

    public CartesianCoordinate withX(double x)
    {
        checkValueNotNanNotInfinity("x", x);

       CartesianCoordinate result = new CartesianCoordinate();
       result.init(x, fY, fZ);
       return result;
    }

    public CartesianCoordinate withY(double y)
    {
        checkValueNotNanNotInfinity("y", y);

        CartesianCoordinate result = new CartesianCoordinate();
        result.init(fX, y, fZ);
        return result;
    }

    public CartesianCoordinate withZ(double z)
    {
        checkValueNotNanNotInfinity("z", z);

        CartesianCoordinate result = new CartesianCoordinate();
        result.init(fX, fY, z);
        return result;
    }

    public String asString() {
        return INVARIANT_FORMAT.format(fX) + " " + INVARIANT_FORMAT.format(fY) + " " + INVARIANT_FORMAT.format(fZ);
    }

    public CartesianCoordinate asCartesianCoordinate() { return this; }

    public SphericCoordinate asSphericCoordinate() {
        double phi = Math.atan(fY / fX);
        double radius = Math.sqrt(fX * fX + fY * fY + fZ * fZ);
        double theta = Math.acos(fZ / radius);
        return new SphericCoordinate(phi, theta, radius);
    }
}

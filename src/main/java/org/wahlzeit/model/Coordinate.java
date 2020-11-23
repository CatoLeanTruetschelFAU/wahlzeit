package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class Coordinate {
    private double fX;
    private double fY;
    private double fZ;

    private static final NumberFormat INVARIANT_FORMAT = NumberFormat.getInstance(Locale.US);

    public Coordinate() {
        this(0,0,0);
    }

    public Coordinate(double x, double y, double z) {
        checkValues(x,y, z);
        fX = x;
        fY = y;
        fZ = z;
    }

    public Coordinate(String value)
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

    public double getDistance(Coordinate other) throws IllegalArgumentException
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

    public boolean isEqual(Coordinate other)
    {
        return isEqual(other, 0.000001);
    }

    public boolean isEqual(Coordinate other, double tolerance)
    {
        if(other == this)
            return true;

        double distance = getDistance(other);

        return distance <= tolerance;
    }

    public Coordinate withX(double x)
    {
        checkValueNotNanNotInfinity("x", x);

       Coordinate result = new Coordinate();
       result.init(x, fY, fZ);
       return result;
    }

    public Coordinate withY(double y)
    {
        checkValueNotNanNotInfinity("y", y);

        Coordinate result = new Coordinate();
        result.init(fX, y, fZ);
        return result;
    }

    public Coordinate withZ(double z)
    {
        checkValueNotNanNotInfinity("z", z);

        Coordinate result = new Coordinate();
        result.init(fX, fY, z);
        return result;
    }

    public String asString() {
        return INVARIANT_FORMAT.format(fX) + " " + INVARIANT_FORMAT.format(fY) + " " + INVARIANT_FORMAT.format(fZ);
    }
}

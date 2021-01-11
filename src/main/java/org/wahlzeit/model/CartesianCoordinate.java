package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public final class CartesianCoordinate extends AbstractCoordinate {

    public static final CartesianCoordinate ORIGIN = new CartesianCoordinate(0,0,0);

    private final double fX;
    private final double fY;
    private final double fZ;

    private static final NumberFormat INVARIANT_FORMAT = NumberFormat.getInstance(Locale.US);
    private static final Map<CartesianCoordinate, CartesianCoordinate> _values = new HashMap<>();

    public static CartesianCoordinate fromValues(double x, double y, double z) {
        checkValues(x,y, z);
        return uncheckedFromValues(x,y,z);
    }

    public static CartesianCoordinate parse(String value) {
        if(value == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("value");

        String[] components = value.split(" ");

        if(components.length == 0)
            return ORIGIN;

        if(components.length != 3)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");

        double x, y, z;

        try {
            x = INVARIANT_FORMAT.parse(components[0]).doubleValue();
            y = INVARIANT_FORMAT.parse(components[1]).doubleValue();
            z = INVARIANT_FORMAT.parse(components[2]).doubleValue();
        } catch(ParseException exc) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value", exc);
            throw null; // Shut up the compiler.
        }

        return uncheckedFromValues(x, y, z);
    }

    private static CartesianCoordinate uncheckedFromValues(double x, double y, double z) {
        CartesianCoordinate result = new CartesianCoordinate(x, y, z);

        synchronized (_values) {
            CartesianCoordinate previous = _values.putIfAbsent(result, result);

            if(previous != null) {
                result = previous;
            }
        }

        return result;
    }

    private CartesianCoordinate(double x, double y, double z) {
        fX = x;
        fY = y;
        fZ = z;
        assertInvariants();
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

    private void assertInvariants() {
        assert !Double.isNaN(fX) && !Double.isInfinite(fX);
        assert !Double.isNaN(fY) && !Double.isInfinite(fY);
        assert !Double.isNaN(fZ) && !Double.isInfinite(fZ);
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

    public double getDistance(CartesianCoordinate other) throws IllegalArgumentException {
        // Assert pre-conditions and invariants
        assertInvariants();

        if(other == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("other");

        double result = getDistanceCore(other);

        // Assert post-conditions and invariants
        assert result >= 0;
        assertInvariants();

        return result;
    }

    private double getDistanceCore(CartesianCoordinate other) {
        if(other == this)
            return 0;

        double distX = other.fX - fX;
        double distY = other.fY - fY;
        double distZ = other.fZ - fZ;

        return Math.sqrt(distX * distX + distY * distY + distZ * distZ);
    }

    public boolean isEqual(CartesianCoordinate other) {
        // Assert pre-conditions and invariants
        assertInvariants();

        boolean result = isEqualCore(other, 0.000001);

        // Assert post-conditions and invariants
        assertInvariants();

        return result;
    }

    public boolean isEqual(CartesianCoordinate other, double tolerance) {
        // Assert pre-conditions and invariants
        assertInvariants();

        if(tolerance < 0)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("tolerance", "a negative value");

        boolean result = isEqualCore(other, tolerance);

        // Assert post-conditions and invariants
        assertInvariants();

        return result;
    }

    private boolean isEqualCore(CartesianCoordinate other, double tolerance) {
        if(other == this)
            return true;

        double distance = getDistance(other);

        return distance <= tolerance;
    }

    int internalHashCode() {
        return Objects.hash(fX, fY, fZ);
    }

    public String asString() {
        return INVARIANT_FORMAT.format(fX) + " " + INVARIANT_FORMAT.format(fY) + " " + INVARIANT_FORMAT.format(fZ);
    }

    public CartesianCoordinate asCartesianCoordinate() { return this; }

    private static final double EPSILON = Double.longBitsToDouble(971L << 52);

    public SphericCoordinate asSphericCoordinate() {
        // Assert pre-conditions and invariants
        assertInvariants();

        SphericCoordinate result = asSphericCoordinateCore();

        // Assert post-conditions and invariants
        assertInvariants();
        // assert result != null; // The compiler actually marks this as error, as it can statically verify that
                                  // result is always non-null.

        return result;
    }

    public SphericCoordinate asSphericCoordinateCore() {
        double radius = Math.sqrt(fX * fX + fY * fY + fZ * fZ);
        assert radius >= 0;
        // theta will become NaN if radius is exactly 0, but it is a trigonometric function that is only approximated,
        // so the total error will be very small, if we divide through radius + epsilon instead of radius.
        // This will guarantee that the fraction will not have a result of infinity (or NaN if fz = 0).
        double theta = Math.acos(fZ / (radius + EPSILON));
        double phi = Math.signum(fX) * Math.atan(fY / (Math.abs(fX) + EPSILON));

        return SphericCoordinate.fromValues(phi, theta, radius);
    }
}


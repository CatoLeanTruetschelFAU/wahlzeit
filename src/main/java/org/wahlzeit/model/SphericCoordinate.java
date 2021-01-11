package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SphericCoordinate extends AbstractCoordinate {

    public static final SphericCoordinate ORIGIN = new SphericCoordinate(0,0,0);

    private final double fPhi; // = Longitude e [0; 2PI]
    private final double fTheta; // = Latitude e [0; PI]
    private final double fRadius; // = Elevation >= 0

    private static final NumberFormat INVARIANT_FORMAT = NumberFormat.getInstance(Locale.US);
    private static final Map<SphericCoordinate, SphericCoordinate> _values = new HashMap<>();

    public static SphericCoordinate fromValues(double phi, double theta, double radius) {
        checkValues(phi, theta, radius);
        return uncheckedFromValues(phi, theta, radius);
    }

    public static SphericCoordinate parse(String value) {
        if(value == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("value");

        String[] components = value.split(" ");

        if(components.length == 0)
            return ORIGIN;

        if(components.length != 3)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");

        double phi, theta, radius;

        try {
            phi = INVARIANT_FORMAT.parse(components[0]).doubleValue();
            theta = INVARIANT_FORMAT.parse(components[1]).doubleValue();
            radius = INVARIANT_FORMAT.parse(components[2]).doubleValue();
        } catch(ParseException exc) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value", exc);
            throw null; // Make the compiler happy
        }

        return uncheckedFromValues(phi, theta, radius);
    }

    private static SphericCoordinate uncheckedFromValues(double phi, double theta, double radius) {
        SphericCoordinate result = new SphericCoordinate(phi, theta, radius);

        synchronized (_values) {
            SphericCoordinate previous = _values.putIfAbsent(result, result);

            if(previous != null) {
                result = previous;
            }
        }

        return result;
    }

    private SphericCoordinate(double phi, double theta, double radius) throws IllegalArgumentException {
        fPhi = phi;
        fTheta = theta;
        fRadius = radius;

        assertInvariants();
    }

    private static void checkValues(double phi, double theta, double radius) throws IllegalArgumentException {
        checkValueNotNanNotInfinity("phi", phi);
        checkValueNotNanNotInfinity("theta", theta);
        checkValueNotNanNotInfinity("radius", radius);

        // TODO
        // We could correct all of the following by wandering around the "globe" for phi, wandering over the poles
        // (= inverting phi) for theta and inverting the direction (phi and theta) for a negative radius.
        // -- For now, just check for these values and reject them.
        if(phi < 0)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("phi", "a negative value.");

        if(phi >= 2 * Math.PI)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("phi", "larger or equal 2π.");

        if(theta < 0)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("theta", "a negative value.");

        if(theta >= Math.PI)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("theta", "larger or equal π.");

        if(radius < 0)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("radius", "a negative value.");
    }

    private static void checkValueNotNanNotInfinity(String argumentName, double value) throws IllegalArgumentException {
        if(Double.isNaN(value))
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage(argumentName, "NaN");

        if(Double.isInfinite(value))
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage(argumentName, "infinity");
    }

    private void assertInvariants() {
        assert !Double.isNaN(fPhi) && !Double.isInfinite(fPhi);
        assert !Double.isNaN(fTheta) && !Double.isInfinite(fTheta);
        assert !Double.isNaN(fRadius) && !Double.isInfinite(fRadius);

        assert fPhi >= 0;
        assert fPhi <= 2 * Math.PI;
        assert fTheta >= 0;
        assert fTheta <= Math.PI;
        assert fRadius >= 0;
    }

    public double getCentralAngle(SphericCoordinate coordinate) throws IllegalArgumentException {
        // Assert pre-conditions and invariants
        if(coordinate == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("coordinate");
        assertInvariants();

        double result = getCentralAngleCore(coordinate);

        // Assert post-conditions and invariants
        assert result >= 0 && result <= Math.PI;
        assertInvariants();

        return result;
    }

    private double getCentralAngleCore(SphericCoordinate coordinate) {
        double cosAbsPhiDiff = Math.abs(fPhi - coordinate.fPhi);
        double productSinTheta = Math.sin(fTheta) * Math.sin(coordinate.fTheta);
        double productCosThea = Math.cos(fTheta) * Math.cos(coordinate.fTheta);

        return Math.acos(productSinTheta + productCosThea * cosAbsPhiDiff);
    }

    public String asString() {
        return INVARIANT_FORMAT.format(fPhi) + " " + INVARIANT_FORMAT.format(fTheta) + " " + INVARIANT_FORMAT.format(fRadius);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        // Assert pre-conditions and invariants
        assertInvariants();

        CartesianCoordinate result = asCartesianCoordinateCore();

        // Assert post-conditions and invariants
        //assert result != null; // The compiler actually marks this as error, as it can statically verify that
        //                       // result is always non-null.
        assertInvariants();

        return result;
    }

    private CartesianCoordinate asCartesianCoordinateCore() {
        double sinTheta = Math.sin(fTheta);

        double x = fRadius * sinTheta * Math.cos(fPhi);
        double y = fRadius * sinTheta * Math.sin(fPhi);
        double z = fRadius * Math.cos(fTheta);

        return CartesianCoordinate.fromValues(x, y, z);
    }
}


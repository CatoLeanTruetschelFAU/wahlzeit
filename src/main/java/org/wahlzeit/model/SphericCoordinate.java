package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.nio.DoubleBuffer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public final class SphericCoordinate extends AbstractCoordinate {

    public static final SphericCoordinate ORIGIN = new SphericCoordinate(0,0,0);

    private double fPhi; // = Longitude e [0; 2PI]
    private double fTheta; // = Latitude e [0; PI]
    private double fRadius; // = Elevation >= 0

    private static final NumberFormat INVARIANT_FORMAT = NumberFormat.getInstance(Locale.US);

    public SphericCoordinate(String value)
    {
        if(value == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("value");

        String[] components = value.split(" ");

        if(components.length == 0)
            return;

        if(components.length != 3)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");

        try {
            fPhi = INVARIANT_FORMAT.parse(components[0]).doubleValue();
            fTheta = INVARIANT_FORMAT.parse(components[1]).doubleValue();
            fRadius = INVARIANT_FORMAT.parse(components[2]).doubleValue();
        } catch(ParseException exc) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value", exc);
        }

        assertInvariants();
    }

    public SphericCoordinate(double phi, double theta, double radius) throws IllegalArgumentException {
        checkValues(phi,theta, radius);

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
        // Assert post-conditions and invariants
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
        // Assert post-conditions and invariants
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

        return new CartesianCoordinate(x, y, z);
    }
}


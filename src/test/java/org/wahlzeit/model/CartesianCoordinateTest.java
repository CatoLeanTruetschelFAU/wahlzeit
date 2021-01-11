package org.wahlzeit.model;

import org.junit.*;

public class CartesianCoordinateTest {

    private static final double DEFAULT_TOLERANCE = 0.000001;
    private static final double EPSILON = Math.ulp(1.0);

    @Test
    public void testInit(){
        // Arrange
        double x = 5;
        double y = 10;
        double z = -1.445;

        // Act
        CartesianCoordinate coordinate = CartesianCoordinate.fromValues(x, y, z);

        // Assert
        Assert.assertEquals(x, coordinate.getX(), DEFAULT_TOLERANCE);
        Assert.assertEquals(y, coordinate.getY(), DEFAULT_TOLERANCE);
        Assert.assertEquals(z, coordinate.getZ(), DEFAULT_TOLERANCE);
    }

    @Test
    public void testIsEqualSameInstance(){
        // Arrange
        CartesianCoordinate subject = CartesianCoordinate.ORIGIN;
        CartesianCoordinate comparator = subject;

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void testIsEqualExact(){
        // Arrange
        CartesianCoordinate subject = CartesianCoordinate.ORIGIN;
        CartesianCoordinate comparator = CartesianCoordinate.ORIGIN;

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void testIsEqualWithTolerance(){
        // Arrange
        CartesianCoordinate subject = CartesianCoordinate.ORIGIN;
        CartesianCoordinate comparator = CartesianCoordinate.fromValues(EPSILON, EPSILON, EPSILON);

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void testNotIsEqual(){
        // Arrange
        CartesianCoordinate subject = CartesianCoordinate.ORIGIN;
        CartesianCoordinate comparator = CartesianCoordinate.fromValues(1, 1, 1);

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertFalse(areEqual);
    }

    @Test
    public void testGetDistanceSameInstance() {
        // Arrange
        CartesianCoordinate subject = CartesianCoordinate.ORIGIN;
        CartesianCoordinate other = subject;

        // Act
        double distance = subject.getDistance(other);

        // Assert
        Assert.assertEquals(0, distance, DEFAULT_TOLERANCE);
    }

    @Test
    public void testGetDistance() {
        // Arrange
        CartesianCoordinate subject = CartesianCoordinate.fromValues(-1, -1, -1);
        CartesianCoordinate other = CartesianCoordinate.fromValues(1, 1, 1);
        double expectedDistance = 3.4641016151377545870548926830117; // sqrt(2^2 * 3) = sqrt(12)

        // Act
        double distance = subject.getDistance(other);

        // Assert
        Assert.assertEquals(expectedDistance, distance, DEFAULT_TOLERANCE);
    }

    @Test
    public void testSpericRoundtrip() {
        // Arrange
        CartesianCoordinate expected = CartesianCoordinate.fromValues(1, 1, 1);

        // Act
        CartesianCoordinate coordinate = expected.asSphericCoordinate().asCartesianCoordinate();

        // Assert
        Assert.assertEquals(expected, coordinate);
    }

    @Test
    public void testSpericEqualsOriginal() {
        // Arrange
        CartesianCoordinate expected = CartesianCoordinate.fromValues(1, 1, 1);

        // Act
        SphericCoordinate coordinate = expected.asSphericCoordinate();

        // Assert
        Assert.assertEquals(expected, coordinate);
    }

    @Test
    public void testOriginSpericToCartesian() {
        // Arrange
        SphericCoordinate spheric = SphericCoordinate.ORIGIN;

        // Act
        CartesianCoordinate cartesian = spheric.asCartesianCoordinate();

        // Assert
        Assert.assertEquals(spheric, cartesian);
    }

    @Test
    public void testOriginCartesianToSperic() {
        // Arrange
        CartesianCoordinate cartesian = CartesianCoordinate.ORIGIN;

        // Act
        SphericCoordinate spheric =  cartesian.asSphericCoordinate();

        // Assert
        Assert.assertEquals(cartesian, spheric);
    }
}

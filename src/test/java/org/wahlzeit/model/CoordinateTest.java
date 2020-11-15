package org.wahlzeit.model;

import org.junit.*;

public class CoordinateTest {

    private static final double DEFAULT_TOLERANCE = 0.000001;
    private static final double EPSILON = Math.ulp(1.0);

    @Test
    public void testInit(){
        // Arrange
        double x = 5;
        double y = 10;
        double z = -1.445;

        // Act
        Coordinate coordinate = new Coordinate(x, y, z);

        // Assert
        Assert.assertEquals(x, coordinate.getX(), DEFAULT_TOLERANCE);
        Assert.assertEquals(y, coordinate.getY(), DEFAULT_TOLERANCE);
        Assert.assertEquals(z, coordinate.getZ(), DEFAULT_TOLERANCE);
    }

    @Test
    public void testIsEqualSameInstance(){
        // Arrange
        Coordinate subject = new Coordinate();
        Coordinate comparator = subject;

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void testIsEqualExact(){
        // Arrange
        Coordinate subject = new Coordinate();
        Coordinate comparator = new Coordinate();

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void testIsEqualWithTolerance(){
        // Arrange
        Coordinate subject = new Coordinate();
        Coordinate comparator = new Coordinate(EPSILON, EPSILON, EPSILON);

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertTrue(areEqual);
    }

    @Test
    public void testNotIsEqual(){
        // Arrange
        Coordinate subject = new Coordinate();
        Coordinate comparator = new Coordinate(1, 1, 1);

        // Act
        boolean areEqual = subject.isEqual(comparator);

        // Assert
        Assert.assertFalse(areEqual);
    }

    @Test
    public void testGetDistanceSameInstance() {
        // Arrange
        Coordinate subject = new Coordinate();
        Coordinate other = subject;

        // Act
        double distance = subject.getDistance(other);

        // Assert
        Assert.assertEquals(0, distance, DEFAULT_TOLERANCE);
    }

    @Test
    public void testGetDistance() {
        // Arrange
        Coordinate subject = new Coordinate(-1, -1, -1);
        Coordinate other = new Coordinate(1, 1, 1);
        double expectedDistance = 3.4641016151377545870548926830117; // sqrt(2^2 * 3) = sqrt(12)

        // Act
        double distance = subject.getDistance(other);

        // Assert
        Assert.assertEquals(expectedDistance, distance, DEFAULT_TOLERANCE);
    }
}
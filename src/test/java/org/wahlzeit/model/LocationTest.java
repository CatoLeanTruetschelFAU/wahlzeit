package org.wahlzeit.model;

import org.junit.Assert;
import org.junit.Test;

public final class LocationTest {
    private static final double DEFAULT_TOLERANCE = 0.000001;

    @Test
    public void testDefaultInit() {
        // Arrange
        // -

        // Act
        Location subject = new Location();

        // Assert
        Assert.assertTrue(subject.isDefault());
        Assert.assertEquals(new CartesianCoordinate(), subject.getCoordinate().asCartesionCoordinate());
    }

    @Test
    public void testInit() {
        // Arrange
        double expectedX = 123.456;
        double expectedY = 567.891;
        double expectedZ = 234.567;

        // Act
        Location subject = new Location(new CartesianCoordinate(expectedX, expectedY, expectedZ));

        // Assert
        Assert.assertFalse(subject.isDefault());
        Assert.assertEquals(
                new CartesianCoordinate(expectedX, expectedY, expectedZ),
                subject.getCoordinate().asCartesionCoordinate());
    }
}

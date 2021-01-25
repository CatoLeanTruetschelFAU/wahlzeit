package org.wahlzeit.model;

import org.junit.Assert;
import org.junit.Test;

public class RestaurantTypeTest {
    @Test
    public void constructRootTest() {
        // Arrange
        String name = "abc";

        // Act
        RestaurantType subject = RestaurantType.getRoot(name);

        // Assert
        Assert.assertNotNull(subject);
        Assert.assertEquals(name, subject.getName());
        Assert.assertEquals(name, subject.toString());
        Assert.assertNull(name, subject.getBaseType());
    }

    @Test
    public void lookupRootTest() {
        // Arrange
        String name = "def";
        RestaurantType comparison = RestaurantType.getRoot(name);

        // Act
        RestaurantType subject = RestaurantType.getRoot(name);

        // Assert
        Assert.assertSame(comparison, subject);
    }

    @Test
    public void subTypeTest() {
        // Arrange
        String name = "def";
        String expectedFullName = "abc/" + name;
        RestaurantType subject = RestaurantType.getRoot("abc");

        // Act
        RestaurantType result = subject.subType(name);

        // Assert
        Assert.assertNotNull(result);
        Assert.assertEquals(name, result.getName());
        Assert.assertEquals(expectedFullName, result.toString());
        Assert.assertSame(subject, result.getBaseType());
    }

    @Test
    public void lookupTest() {
        // Arrange
        String name = "def";
        String fullName = "abc/" + name;
        RestaurantType comparison = RestaurantType.getRoot("abc").subType(name);

        // Act
        RestaurantType result = RestaurantType.get(fullName);

        // Assert
        Assert.assertSame(comparison, result);
    }

    @Test
    public void escapeTest() {
        // Arrange
        String baseTypeName = "x/ui";
        String subTypeName = "y/op";
        String fullName = "x//ui/y//op";

        // Act
        RestaurantType subject = RestaurantType.getRoot(baseTypeName).subType(subTypeName);

        // Assert
        Assert.assertEquals(fullName, subject.toString());
    }

    @Test
    public void unescapeTest() {
        // Arrange
        String baseTypeName = "x/ui";
        String subTypeName = "y/op";
        String fullName = "x//ui/y//op";

        // Act
        RestaurantType subject = RestaurantType.get(fullName);

        // Assert
        Assert.assertEquals(subTypeName, subject.getName());
        Assert.assertEquals(baseTypeName, subject.getBaseType().getName());
    }

    @Test
    public void instanceOfTest() {
        // Arrange
        RestaurantType root = RestaurantType.getRoot("a");
        Restaurant instance = new Restaurant(root);

        // Act
        boolean result = root.instanceOf(instance);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void instanceOfBaseTypeTest() {
        // Arrange
        RestaurantType root = RestaurantType.getRoot("a");
        RestaurantType subType = root.subType("b");
        Restaurant instance = new Restaurant(subType);

        // Act
        boolean result = root.instanceOf(instance);

        // Assert
        Assert.assertTrue(result);
    }

    @Test
    public void notInstanceOfBaseTypeTest() {
        // Arrange
        RestaurantType root = RestaurantType.getRoot("a");
        RestaurantType subType = root.subType("b");
        RestaurantType other = RestaurantType.getRoot("x");
        Restaurant instance = new Restaurant(subType);

        // Act
        boolean result = other.instanceOf(instance);

        // Assert
        Assert.assertFalse(result);
    }
}

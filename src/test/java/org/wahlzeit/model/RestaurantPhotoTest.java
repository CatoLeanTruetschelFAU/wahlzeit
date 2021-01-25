package org.wahlzeit.model;
import org.junit.*;
import org.mockito.Mockito;

import java.sql.*;

public class RestaurantPhotoTest extends PhotoSpecification {

    protected static final String EXPECTED_RESTAURANT_NAME = "EXPECTED_RESTAURANT_NAME";
    protected static final String EXPECTED_RESTAURANT_OFFERED_FOOD_TYPE = FoodType.ITALIAN_FOOD.asString();
    protected static final Boolean EXPECTED_RESTAURANT_OFFERS_VEGETARIAN_FOOD = true;
    protected  static final RestaurantType EXPECTED_RESTAURANT_TYPE = RestaurantType.get("Restaurant/BurgerRestaurant");

    @Override
    protected Photo init() {
        return new RestaurantPhoto();
    }

    @Override
    protected Photo init(PhotoId photoId) {
        return new RestaurantPhoto(photoId);
    }

    @Override
    protected Photo init(ResultSet rset) throws SQLException {
        return new RestaurantPhoto(rset);
    }

    @Override
    protected ResultSet buildResultSet() throws SQLException {
        ResultSet rset = super.buildResultSet();
        Mockito.when(rset.getString("restaurant_type")).thenReturn(EXPECTED_RESTAURANT_TYPE.toString());
        Mockito.when(rset.getString("restaurant_name")).thenReturn(EXPECTED_RESTAURANT_NAME);
        Mockito.when(rset.getString("restaurant_offered_food_type")).thenReturn(EXPECTED_RESTAURANT_OFFERED_FOOD_TYPE);
        Mockito.when(rset.getBoolean("restaurant_offers_vegetarian_food")).thenReturn(EXPECTED_RESTAURANT_OFFERS_VEGETARIAN_FOOD);

        return rset;
    }

    @Test
    public void InitRestaurantPhotoWithResultSetTest() throws SQLException {
        // Arrange
        ResultSet rset = buildResultSet();

        // Act
        RestaurantPhoto subject = new RestaurantPhoto(rset);

        // Assert
        Assert.assertNotNull(subject.getRestaurant());
        Assert.assertSame(EXPECTED_RESTAURANT_TYPE, subject.getRestaurant().getRestaurantType());
        Assert.assertEquals(EXPECTED_RESTAURANT_NAME, subject.getRestaurant().getName());
        Assert.assertEquals(EXPECTED_RESTAURANT_OFFERED_FOOD_TYPE, subject.getRestaurant().getOfferedFoodType().asString());
        Assert.assertEquals(EXPECTED_RESTAURANT_OFFERS_VEGETARIAN_FOOD, subject.getRestaurant().offersVegetarianFood());
    }
}

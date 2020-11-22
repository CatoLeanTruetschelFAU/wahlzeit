package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.util.Objects;

public final class FoodType {

    // Something like a built-in NULL OBJECT
    public static  final FoodType NO_FOOD = new FoodType("");
    public static final FoodType BURGERS = new FoodType("Burgers");
    public static final FoodType ASIAN_FOOD = new FoodType("Asian food");
    public static final FoodType ITALIAN_FOOD = new FoodType("Italian food");

    private final String _foodType;

    private FoodType(String foodType) {
        _foodType = foodType;
    }

    public static FoodType create(String foodType) {

        if(foodType == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("foodType");

        if (foodType.equals((NO_FOOD.asString()))) {
            return NO_FOOD;
        }

        if (foodType.equals((BURGERS.asString()))) {
            return BURGERS;
        }

        if (foodType.equals((ASIAN_FOOD.asString()))) {
            return ASIAN_FOOD;
        }

        if (foodType.equals((ITALIAN_FOOD.asString()))) {
            return ITALIAN_FOOD;
        }

        return new FoodType(foodType);
    }

    public String asString() {
        return _foodType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        FoodType foodType = (FoodType) o;
        return _foodType.equals(foodType._foodType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_foodType);
    }
}

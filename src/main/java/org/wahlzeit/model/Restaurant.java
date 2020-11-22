package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Restaurant{
    private String _name;
    private FoodType _offeredFoodType;
    private boolean _offersVegetarianFood;

    public Restaurant()
    {
        this("NoNameRestaurant", FoodType.NO_FOOD, false);
    }

    public Restaurant(String name, FoodType offeredFoodType, boolean offersVegetarianFood) {
        if(name == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("name");

        if(name.length() == 0)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("name", "an empty string.");

        if(offeredFoodType == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("offeredFoodType");

        _name = name;
        _offeredFoodType = offeredFoodType;
        _offersVegetarianFood = offersVegetarianFood;

        incWriteCount();
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        if(name == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("name");

        if(name.length() == 0)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("name", "an empty string.");

        _name = name;

        incWriteCount();
    }

    // I would really like to make this able to store a set of offered food types but I
    // don't know how to get all of this into the database without creating another table.
    public boolean isOffered(FoodType foodType) {
        return _offeredFoodType == foodType;
    }

    public FoodType getOfferedFoodType() {
        return _offeredFoodType;
    }

    public void offersFoodType(FoodType foodType) {
        if(foodType == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("foodType");

        _offeredFoodType = foodType;
        incWriteCount();
    }

    public void doesNotOfferFoodType(FoodType foodType) {
        if(foodType == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("foodType");

        if(_offeredFoodType.equals(foodType)) {
            _offeredFoodType = FoodType.NO_FOOD;
            incWriteCount();
        }
    }

    public boolean offersVegetarianFood() {
        return _offersVegetarianFood;
    }

    public void offersVegetarianFood(boolean offersVegetarianFood) {
        _offersVegetarianFood = offersVegetarianFood;
        incWriteCount();
    }

    // Persistence stuff, mainly copied here... Why don't we use a document database or an ORM if we already use a
    // crappy language like JAVA :(
    public void readFrom(ResultSet rset) throws SQLException {
        _name = rset.getString("restaurant_name");
        _offeredFoodType = FoodType.create(rset.getString("restaurant_offered_food_type"));
        _offersVegetarianFood = rset.getBoolean("restaurant_offers_vegetarian_food");

        resetWriteCount();
    }

    public void writeOn(ResultSet rset) throws SQLException {
        rset.updateString("restaurant_name", _name);
        rset.updateString("restaurant_offered_food_type", _offeredFoodType.asString());
        rset.updateBoolean("restaurant_offers_vegetarian_food", _offersVegetarianFood);

        resetWriteCount();
    }

    protected transient int writeCount = 0;

    public final boolean isDirty() {
        return writeCount != 0;
    }

    public final void resetWriteCount() {
        writeCount = 0;
    }

    public final void incWriteCount() {
        writeCount++;
    }

}

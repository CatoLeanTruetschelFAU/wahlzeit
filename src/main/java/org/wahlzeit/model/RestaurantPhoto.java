package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;


/* Object creation trajectory (static type -> dynamic type):
 * PhotoManager.createObject(ResultSet) -> RestaurantPhotoManager.createObject(ResultSet)
 * PhotoFactory.createPhoto(ResultSet) -> RestaurantPhotoFactory.createPhoto(ResultSet)
 * RestaurantPhoto.ctor(ResultSet)
 * RestaurantPhoto.ctor()
 * Photo.ctor()
 * RestaurantType.getRoot(String)
 * Restaurant.ctor(RestaurantType)
 * RestaurantPhoto.readFrom(ResultSet)
 * Photo.readFrom(ResultSet)
 * Location.parse(String)
 * Restaurant.readFrom(ResultSet)
 *
 * The RestaurantPhoto class takes part in the "restaurant domain" collaboration between Restaurant and RestaurantPhoto
 * and binds the client role.
 *
 * The RestaurantPhoto class takes part in the "factory" collaboration between RestaurantPhotoFactory and RestaurantPhoto
 * and binds the product role.
 */
public final class RestaurantPhoto extends Photo {
    private final Restaurant _restaurant;

    public RestaurantPhoto() {
        _restaurant = new Restaurant(RestaurantType.getRoot("Restaurant"));
    }

    public RestaurantPhoto(PhotoId myId) {
        super(myId); // Base class ctor already checks myId for null
        _restaurant = new Restaurant(RestaurantType.getRoot("Restaurant"));
    }

    public RestaurantPhoto(ResultSet rset) throws SQLException {
        // Do not call the Photo(ResultSet) base type ctor here, as it calls into the virtual method
        // readFrom(ResultSet) where _restaurant is not assigned yet.
        this();

        readFrom(rset);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        if(rset == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("rset");

        super.readFrom(rset);
        _restaurant.readFrom(rset);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        if(rset == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("rset");

        super.writeOn(rset);
        _restaurant.writeOn(rset);
    }

    @Override
    public final boolean isDirty() {
        return super.isDirty() || _restaurant.isDirty();
    }

    public Restaurant getRestaurant() {
        return _restaurant;
    }

    // No need to set the restaurant, as the restaurant is a mutable object.
}

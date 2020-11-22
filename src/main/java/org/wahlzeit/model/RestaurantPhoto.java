package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RestaurantPhoto extends Photo {
    private Restaurant _restaurant;

    public RestaurantPhoto() {
        _restaurant = new Restaurant();
    }

    public RestaurantPhoto(PhotoId myId) {
        super(myId);
        _restaurant = new Restaurant();
    }

    public RestaurantPhoto(ResultSet rset) throws SQLException {
        // Do not call the Photo(ResultSet) base type ctor here, as it calls into the virtual method
        // readFrom(ResultSet) where _restaurant is not assigned yet.
        this();

        readFrom(rset);
    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        super.readFrom(rset);

        _restaurant.readFrom(rset);
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
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

package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RestaurantPhotoFactory extends PhotoFactory {

    @Override
    public RestaurantPhoto createPhoto() {
        return new RestaurantPhoto();
    }

    @Override
    public RestaurantPhoto createPhoto(PhotoId id) {
        // id may be null, if so, the RestaurantPhoto ctor throws. Just escalate this as is to the caller.
        return new RestaurantPhoto(id);
    }

    @Override
    public RestaurantPhoto createPhoto(ResultSet rs) throws SQLException {
        // rset may be null, or it may be the case that the photo cannot be loaded from it.
        // If so, the RestaurantPhoto ctor throws. Just escalate this as is to the caller.
        return new RestaurantPhoto(rs);
    }

    // DI container to the rescue
    public static void initialize() {
        setInstance(new RestaurantPhotoFactory());
    }
}

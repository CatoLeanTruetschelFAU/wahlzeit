package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class RestaurantPhotoFactory extends PhotoFactory {

    @Override
    public Photo createPhoto() {
        return new RestaurantPhoto();
    }

    @Override
    public Photo createPhoto(PhotoId id) {
        return new RestaurantPhoto(id);
    }

    @Override
    public Photo createPhoto(ResultSet rs) throws SQLException {
        return new RestaurantPhoto(rs);
    }

    // DI container to the rescue
    public static void initialize() {
        setInstance(new RestaurantPhotoFactory());
    }
}

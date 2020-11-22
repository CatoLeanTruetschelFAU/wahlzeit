package org.wahlzeit.model;

public final class RestaurantPhotoManager extends PhotoManager {

    // We actually do not have to override anything at the moment, as the base class creates photos only in
    // 1) createObject(ResultSet)
    // 2) PhotoUtil.createPhoto(File source, PhotoId id) via createPhoto(File file)
    // Both use the current instance of the PhotoFactory which is set to an instance of RestaurantPhotoManager globally.

    public static void initialize() {
        setInstance(new RestaurantPhotoManager());
    }
}

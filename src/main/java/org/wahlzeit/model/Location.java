package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

public final class Location {
    private static final CartesianCoordinate DEFAULT_COORDINATE = new CartesianCoordinate();

    private CartesianCoordinate fCoordinate;

    public Location() {
        init(DEFAULT_COORDINATE);
    }

    public Location(CartesianCoordinate coordinate)
    {
        if(coordinate == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("coordinate");

        init(coordinate);
    }

    private void init(CartesianCoordinate coordinate)
    {
        fCoordinate = coordinate;
    }

    public CartesianCoordinate getCoordinate()
    {
        return fCoordinate;
    }

    public boolean isDefault()
    {
        return fCoordinate == DEFAULT_COORDINATE;
    }
}

package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

public final class Location {
    private static final Coordinate DEFAULT_COORDINATE = new Coordinate();

    private Coordinate fCoordinate;

    public Location() {
        init(DEFAULT_COORDINATE);
    }

    public Location(Coordinate coordinate)
    {
        if(coordinate == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("coordinate");

        init(coordinate);
    }

    private void init(Coordinate coordinate)
    {
        fCoordinate = coordinate;
    }

    public Coordinate getCoordinate()
    {
        return fCoordinate;
    }

    public boolean isDefault()
    {
        return fCoordinate == DEFAULT_COORDINATE;
    }
}

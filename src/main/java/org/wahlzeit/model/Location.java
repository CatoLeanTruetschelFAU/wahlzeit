package org.wahlzeit.model;

import org.wahlzeit.utils.ExceptionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;

public final class Location {
    private static final CartesianCoordinate DEFAULT_COORDINATE = new CartesianCoordinate();

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

    public String asString() {
        if(isDefault())
            return "";

        Coordinate coordinate = getCoordinate();
        String discriminator = coordinate.getClass().getTypeName();
        String separator = "_";
        String coordinateStringRepresentation = coordinate.asString();

        return discriminator + separator + coordinateStringRepresentation;
    }

    private static final Class<?>[] COORDINATE_CTOR_PARAMS_LIST = new Class[] { String.class };

    public static Location parse(String value) throws IllegalArgumentException {
        if(value == null || value.equals(""))
            return null;

        String[] components = value.split("_", 2);

        if(components.length == 0)
            return null;

        if(components.length != 2)
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");

        String discriminator = components[0];
        String coordinateStringRepresentation = components[1];
        Class<?> clazz = getClassFromDiscriminator(discriminator);
        Constructor<?> ctor = getCtorFromClass(clazz);

        Coordinate coordinate = instantiate(ctor, coordinateStringRepresentation);

        return new Location(coordinate);
    }

    private static Class<?> getClassFromDiscriminator(String discriminator) throws IllegalArgumentException {
        Class<?> cls;

        try {
            cls = Class.forName(discriminator);
        } catch (ClassNotFoundException e) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");
            throw null;
        }

        return cls;
    }

    private static Constructor<?> getCtorFromClass(Class<?> clazz) throws IllegalArgumentException {
        Constructor<?> ctor;

        try {
            ctor = clazz.getConstructor(COORDINATE_CTOR_PARAMS_LIST);
        } catch (NoSuchMethodException e) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");
            throw null;
        }

        return ctor;
    }

    private static Coordinate instantiate(Constructor<?> ctor, String coordinateStringRepresentation)
        throws IllegalArgumentException
    {
        Coordinate coordinate;

        try {
            coordinate = (Coordinate) ctor.newInstance(coordinateStringRepresentation);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("value");
            throw null;
        }

        return coordinate;
    }
}

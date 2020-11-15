package org.wahlzeit.utils;

public final class ExceptionHelper {
    public static String FormatIllegalArgumentExceptionMessage(String argumentName, Object value) {
        return "The specified argument with name '" + argumentName +"' must not be " + value.toString() + ".";
    }

    public static void ThrowIllegalArgumentExceptionMessage(
            String argumentName,
            Object value) throws IllegalArgumentException {
        throw new IllegalArgumentException(FormatIllegalArgumentExceptionMessage(argumentName, value));
    }

    public static void ThrowNullArgumentExceptionMessage(String argumentName) throws IllegalArgumentException {
        ThrowIllegalArgumentExceptionMessage(argumentName, "a null reference");
    }
}

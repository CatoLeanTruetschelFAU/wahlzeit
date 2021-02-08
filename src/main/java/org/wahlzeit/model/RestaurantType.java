package org.wahlzeit.model;
import org.wahlzeit.utils.ExceptionHelper;

import java.util.HashMap;
import java.util.Map;

/* Object creation trajectory (static type -> dynamic type) continued from Restaurant:
 * Restaurant.readFrom(ResultSet)
 * RestaurantType.get(String)
 * RestaurantType.ctor(String, RestaurantType)
 *
 * The RestaurantType class takes part in the "type-object" collaboration between Restaurant and RestaurantType
 * and binds the type-object role.
 */
public final class RestaurantType {
    private static final Map<String, RestaurantType> _lookup = new HashMap<>();
    private static final Object _syncRoot = new Object();

    // Managing type hierarchy
    private final RestaurantType _baseType;

    // Payload
    private final String _fullName;

    // Ctor
    private RestaurantType(String fullName, RestaurantType baseType)
    {
        assert fullName != null;
        assert baseType != null;

        _baseType = baseType;
        _fullName = fullName;
    }

    private RestaurantType(String fullName) {
        assert fullName != null;

        _baseType = null;
        _fullName = fullName;
    }

    public String getName() {
        String[] escapedNames = _fullName.split("(?<!\\/)\\/(?!\\/)");

        return unescapeName(escapedNames[escapedNames.length - 1]);
    }

    public RestaurantType getBaseType() {
        return _baseType;
    }

    public boolean instanceOf(Restaurant restaurant) {
        if(restaurant == null)
            ExceptionHelper.ThrowNullArgumentExceptionMessage("restaurant");

        RestaurantType current = restaurant.getRestaurantType();

        while(current != null) {
            if(current == this) {
                return true;
            }

            current = current._baseType;
        }

        return false;
    }

    public static RestaurantType getRoot(String name) {
        if(name == null || name.matches("\\s*")) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("name", "null, an empty string nor consist of whitespace only.");
        }

        String fullName = escapeName(name);

        synchronized (_syncRoot)
        {
            RestaurantType result = _lookup.getOrDefault(fullName, null);

            if(result == null) {
                result = new RestaurantType(fullName);
                _lookup.put(fullName, result);
            }

            return result;
        }
    }

    public static RestaurantType get(String fullName) {
        String[] escapedNames = fullName.split("(?<!\\/)\\/(?!\\/)");

        RestaurantType current = getRoot(unescapeName(escapedNames[0]));

        for(int i = 1; i < escapedNames.length; i++){
            current = current.subType(unescapeName(escapedNames[i]));
        }

        return current;
    }

    public RestaurantType subType(String name) {
        if(name == null || name.matches("^\\s*$")) {
            ExceptionHelper.ThrowIllegalArgumentExceptionMessage("name", "null, an empty string nor consist of whitespace only.");
        }

        String fullName = _fullName+ "/" + escapeName(name);

        synchronized (_syncRoot)
        {
            RestaurantType result = _lookup.getOrDefault(fullName, null);

            if(result == null) {
                result = new RestaurantType(fullName, this);
                _lookup.put(fullName, result);
            }

            return result;
        }
    }

    @Override
    public String toString() {
        return _fullName;
    }

    private static String escapeName(String name) {
        return name.replace("/", "//");
    }

    private static String unescapeName(String escapedName) {
        return escapedName.replace("//", "/");
    }
}
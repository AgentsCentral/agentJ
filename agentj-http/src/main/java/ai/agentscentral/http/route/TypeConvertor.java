package ai.agentscentral.http.route;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Utility class that converts raw {@code String} (or {@code String[]}) values to typed
 * Java objects for controller method parameter binding.
 *
 * <p>Supports the following target types out of the box: {@code int}/{@code Integer},
 * {@code long}/{@code Long}, {@code double}/{@code Double}, {@code boolean}/{@code Boolean},
 * {@code float}/{@code Float}, {@code short}/{@code Short}, {@code byte}/{@code Byte},
 * {@code String}, any {@code enum} type, and single-type arrays of the above.</p>
 *
 * @author Rizwan Idrees
 */
public class TypeConvertor {

    /**
     * This is a utility class and cannot be instantiated.
     */
    private TypeConvertor() {
    }

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS = new HashMap<>() {{
        register(int.class, Integer.class, Integer::parseInt);
        register(long.class, Long.class, Long::parseLong);
        register(double.class, Double.class, Double::parseDouble);
        register(boolean.class, Boolean.class, Boolean::parseBoolean);
        register(float.class, Float.class, Float::parseFloat);
        register(short.class, Short.class, Short::parseShort);
        register(byte.class, Byte.class, Byte::parseByte);
        CONVERTERS.put(String.class, s -> s);
    }};


    /**
     * Converts an array of raw string values to the specified target type.
     *
     * <p>If {@code type} is an array type the entire {@code values} array is converted
     * element-by-element.  If {@code type} is an enum the first element is matched by
     * {@code toString()}.  Otherwise {@code values[0]} is converted via
     * {@link #convert(String, Class)}.</p>
     *
     * @param values raw string values from the request; {@code null} returns {@code null}
     * @param type   the target Java type
     * @return the converted value, or {@code null} if {@code values} is {@code null} or
     *         no convertor is registered for {@code type}
     */
    public static Object convert(String[] values, Class<?> type) {

        if (Objects.isNull(values)) {
            return null;
        }

        if (type.isEnum() && values.length > 0) {
            return convertToEnum(values[0], type);
        }

        if (type.isArray()) {
            return convertToArray(type.getComponentType(), values);
        }

        return convert(values[0], type);
    }

    /**
     * Converts a single raw string value to the specified target type.
     *
     * @param value the raw string value to convert
     * @param type  the target Java type; must be registered in the internal converters
     *              map or be an enum type
     * @return the converted value, or {@code null} if no convertor is registered for
     *         {@code type}
     */
    public static Object convert(String value, Class<?> type) {

        if (CONVERTERS.containsKey(type)) {
            return CONVERTERS.get(type).apply(value);
        } else if (type.isEnum()) {
            return convertToEnum(value, type);
        }

        return null;
    }

    private static <T> T convertToEnum(String value, Class<T> clazz) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(e -> e.toString().equals(value)).findFirst().orElse(null);
    }

    private static Object convertToArray(Class<?> componentType, String[] values) {

        final Object array = Array.newInstance(componentType, values.length);

        IntStream.range(0, values.length)
                .forEach(i -> Array.set(array, i, convert(values[i], componentType)));

        return array;
    }

    private static <T> void register(Class<?> prim, Class<T> wrap, Function<String, T> func) {
        CONVERTERS.put(prim, func::apply);
        CONVERTERS.put(wrap, func::apply);
    }
}



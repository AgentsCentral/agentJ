package ai.agentscentral.http.route;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * TypeConvertor
 *
 * @author Rizwan Idrees
 */
class TypeConvertor {

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS = new HashMap<>();

    static {
        register(int.class, Integer.class, Integer::parseInt);
        register(long.class, Long.class, Long::parseLong);
        register(double.class, Double.class, Double::parseDouble);
        register(boolean.class, Boolean.class, Boolean::parseBoolean);
        register(float.class, Float.class, Float::parseFloat);
        register(short.class, Short.class, Short::parseShort);
        register(byte.class, Byte.class, Byte::parseByte);
        CONVERTERS.put(String.class, s -> s);
    }


    static Object convert(String[] values, Class<?> type) {

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

    static Object convert(String value, Class<?> type) {

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

        for (int i = 0; i < values.length; i++) {
            Object convertedElement = convert(values[i], componentType);
            Array.set(array, i, convertedElement);
        }
        return array;
    }

    private static <T> void register(Class<?> prim, Class<T> wrap, Function<String, T> func) {
        CONVERTERS.put(prim, func::apply);
        CONVERTERS.put(wrap, func::apply);
    }
}



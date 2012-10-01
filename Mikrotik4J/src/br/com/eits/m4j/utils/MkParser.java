package br.com.eits.m4j.utils;

import br.com.eits.m4j.bean.ann.MkMapping;
import br.com.eits.m4j.utils.AnnotationProcessor.Element;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class that contains the parser to the API
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 18/07/2012
 */
public class MkParser {

    /**
     * Parse the command result as {@link MkPropertiesMap}
     *
     * @param commandResult the command result data
     *
     * @return the {@link MkPropertiesMap} of the command result
     */
    public static MkPropertiesMap asPropertiesMap(String commandResult) {

        MkPropertiesMap mapped = new MkPropertiesMap();

        String[] resultBlocks = commandResult.split("!re");

        for (int i = 0; i < resultBlocks.length; i++) {

            String[] blockLines = resultBlocks[i].split("\n");

            Properties properties = toProperties(blockLines);

            if (!properties.isEmpty()) {
                mapped.put(i, properties);
            }
        }

        return mapped;
    }

    /**
     * Parse the result of command to a list of complex objects
     *
     * @param beanToMap the bean to map the result
     * @param commandResult the command result
     *
     * @return the list of mapped objects
     *
     * @throws Exception if any problem occur
     */
    public static <T> List<T> asListOfObjects(Class<T> beanToMap,
            String commandResult) throws Exception {

        MkPropertiesMap mapped = asPropertiesMap(commandResult);

        List<T> objects = new ArrayList<>();

        for (Properties p : mapped.values()) {
            objects.add((T) toComplexBean(beanToMap, p));
        }

        return objects;
    }

    /**
     * The mapper method that map one property in a complex bean
     *
     * <b>IMPORTANT:</b> the mapper supports only the types
     * <code>int</code>,
     * <code>boolean</code>,
     * <code>double</code>,<br/>
     * <code>long</code> and
     * <code>string</code>, then the bean should not contain other types or
     * errors may occur
     *
     * @param bean the bean to put property data
     * @param properties the properties to be mapped
     *
     * @return the object of bean
     *
     * @throws Exception if any problem occur
     */
    @SuppressWarnings("unchecked")
    private static <T> T toComplexBean(Class<T> bean, Properties properties) throws Exception {

        Object object = bean.newInstance();

        // map the class to elements and his respective annotations
        List<Element<Field, MkMapping>> elements =
                AnnotationProcessor.getAnnotatedFields(MkMapping.class, object.getClass());

        // iterate over the mapped elements and recognize his instance and realize
        // automatic cast to the typed field in the bean
        for (Element<Field, MkMapping> e : elements) {

            Field f = e.getField();

            f.setAccessible(true);

            // if is primitive, cast as primitive
            if (f.getType().isPrimitive()) {

                if (f.getType().isAssignableFrom(boolean.class)) {
                    f.set(object, Boolean.valueOf((String) properties.get(e.getAnnotation().from())));
                } else if (f.getType().isAssignableFrom(int.class)) {
                    int intValue = Integer.parseInt(properties.get(e.getAnnotation().from()).toString());
                    f.set(object, intValue);
                } else if (f.getType().isAssignableFrom(double.class)) {
                    double doubleValue = Double.parseDouble(properties.get(e.getAnnotation().from()).toString());
                    f.set(object, doubleValue);
                } else if (f.getType().isAssignableFrom(long.class)) {
                    long longValue = Long.parseLong(properties.get(e.getAnnotation().from()).toString());
                    f.set(object, longValue);
                }
            } else {

                // if object, cast normal to the complex object (String, Integer..)

                f.set(object, f.getType().cast(properties.get(e.getAnnotation().from())));
            }
        }

        // return the casted object to set in the mapped objects list
        return (T) object;
    }

    /**
     * Internal method the transform the command result in properties
     *
     * @param blockLines the command result block
     *
     * @return the data of block in properties (key=value) object
     */
    private static Properties toProperties(String[] blockLines) {

        Properties properties = new Properties();

        for (String line : blockLines) {

            if (!line.isEmpty() && line.length() > 2) {

                String[] propertie = line.substring(1).split("=");

                if (propertie.length >= 2 && !propertie[0].isEmpty()) {

                    if (propertie[0].contains(".id")) {
                        properties.put(propertie[0].substring(1),
                                propertie[1].replaceAll("\\*", ""));
                    } else {
                        properties.put(propertie[0], propertie[1]);
                    }
                }
            }
        }

        return properties;
    }
}

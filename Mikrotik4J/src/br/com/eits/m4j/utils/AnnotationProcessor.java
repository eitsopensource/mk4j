/*
 * Mikrotik4J, Intregrate Java and Mikrotik RouterOS
 *
 * Copyright (c) 2012, Eits It Solutions and Arthur Gregório. 
 * or third-party contributors as indicated by the @author tags or express 
 * copyright attribution statements applied by the authors.  All third-party 
 * contributions are distributed under license by Eits It Solutions.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package br.com.eits.m4j.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * The annotation processor to process the fields annotated by users
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 19/07/2012
 */
public class AnnotationProcessor {

    /**
     * Method that get all the annotated fields in the provided class
     *
     * @param annotation the annotation to get on the fields
     * @param clazz the class to get the fields
     *
     * @return list of {@link Element} containing their fields and their
     * annotations
     */
    public static <A extends Annotation> List<Element<Field, A>> 
            getAnnotatedFields(Class<A> annotation, Class<?> clazz) {

        List<Element<Field, A>> elementos = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();

        for (Field f : fields) {

            if (f.isAnnotationPresent(annotation)) {
                elementos.add(new Element<>(f, f.getAnnotation(annotation)));
            }
        }

        return elementos;
    }

    /**
     * Internal class that maps the elements of one class
     *
     * @param <F> the field
     * @param <A> the annotation
     *
     * @author Arthur Gregorio
     *
     * @since 1.0
     * @version 1.0, 19/07/2012
     */
    public static class Element<F, A extends Annotation> {

        private F field;
        private A annotation;

        /**
         *
         * @param field
         * @param annotation
         */
        public Element(F field, A annotation) {
            this.field = field;
            this.annotation = annotation;
        }

        /**
         * @return the field
         */
        public F getField() {
            return field;
        }

        /**
         * @return the annotation
         */
        public A getAnnotation() {
            return annotation;
        }
    }
}

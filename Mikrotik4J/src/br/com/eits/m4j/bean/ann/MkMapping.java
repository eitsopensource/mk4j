package br.com.eits.m4j.bean.ann;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation helps realize the mapping of a particular <br/> command
 * result to an attribute in a bean
 *
 * @author Arthur Gregorio
 *
 * @since 1.0
 * @version 1.0, 19/07/2012
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MkMapping {

    /**
     * The name attribute of the result to be mapped to the attribute of the
     * bean
     *
     * @return the result attribute name to help mapping
     */
    public String from() default "";
}

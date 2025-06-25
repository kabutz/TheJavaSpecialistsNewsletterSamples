package eu.javaspecialists.tjsn.issue097;

import java.lang.annotation.*;

/**
 * Make annotation available at run time and only allow fields to
 * be modified by this annotation.
 * <p>
 * We have 3 properties, defaults for all
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FromXml {
    /**
     * Normally, the field's value is taken from an attribute with
     * an identical name.  xPath can be used to specify a different
     * source, breaking the name linkage
     */
    String xPath() default "";

    /**
     * A default value to be used if field source is not found in
     * the XML (we call it "dflt" since "default" is reserved)
     */
    String dflt() default "";

    /**
     * Flag to trim fetched data.
     */
    boolean trim() default true;
}

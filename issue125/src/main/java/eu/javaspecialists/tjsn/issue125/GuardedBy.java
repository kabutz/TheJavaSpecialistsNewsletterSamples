package eu.javaspecialists.tjsn.issue125;

import java.lang.annotation.*;

/**
 * The field or method to which this annotation is applied can only
 * be accessed when holding a particular lock, which may be a
 * built-in (synchronization) lock, or may be an explicit
 * java.util.concurrent.Lock.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GuardedBy {
    String value();
}

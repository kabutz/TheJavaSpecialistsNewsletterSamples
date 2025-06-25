package eu.javaspecialists.tjsn.issue167;

import java.lang.annotation.*;

@Inherited
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface NoArgsConstructor {
}
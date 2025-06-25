/*
 * Copyright 2000-2025 Heinz Max Kabutz
 * All rights reserved.
 *
 * From The Java Specialists' Newsletter (https://www.javaspecialists.eu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

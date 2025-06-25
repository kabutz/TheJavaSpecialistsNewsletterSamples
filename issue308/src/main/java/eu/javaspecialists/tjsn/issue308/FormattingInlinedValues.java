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

package eu.javaspecialists.tjsn.issue308;

public class FormattingInlinedValues {
    public static final String FIRST_NAME = "Heinz";
    private static final String MIDDLE_NAME = "Max";
    protected static final String LAST_NAME = "Kabutz";

    public static final int AGE = 51;
    public static final double HEIGHT = 1.8824213;
    static final boolean THIN = false;

    /**
     * {@return the details of the author} This includes the
     * {@value "FIRST_NAME=%s" #FIRST_NAME}, the {@value
     * "LAST_NAME=%s" #LAST_NAME}, the {@value "AGE=0x%04x"
     * #AGE} in hexadecimal, and the {@value "HEIGHT=%.2fm"
     * #HEIGHT}. We will leave out the property of {@value
     * "THIN=%B" #THIN} and the {@value "MIDDLE_NAME=%s"
     * #MIDDLE_NAME} as not relevant to writing skills.
     */
    public String toString() {
        return "%s %s of age 0x%04x is %.2fm tall".formatted(FIRST_NAME,
                LAST_NAME, AGE, HEIGHT);
    }

    public static void main(String... args) {
        System.out.println(new FormattingInlinedValues());
    }
}

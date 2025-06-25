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

package eu.javaspecialists.tjsn.issue164.stupidframework;

public class HappyUser extends StupidInhouseFramework {
    private final Long density;

    private static final ThreadLocal<Long> density_param =
            new ThreadLocal<Long>();

    private static String setParams(String title, long density) {
        density_param.set(density);
        return title;
    }

    private long getDensity() {
        Long param = density_param.get();
        if (param != null) {
            return param;
        }
        return density;
    }

    public HappyUser(String title, long density) {
        super(setParams(title, density));
        this.density = density;
        density_param.remove();
    }

    public void draw() {
        long density_fudge_value = getDensity() + 30 * 113;
        System.out.println("draw ... " + density_fudge_value);
    }

    public static void main(String... args) {
        StupidInhouseFramework sif = new HappyUser("Poor Me", 33244L);
        sif.draw();
    }
}

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

package eu.javaspecialists.tjsn.issue139;

import java.util.*;

public class MusicServiceImpl implements MusicService {
    private final String[] titles = {
            "Don't Worry Be Happy - Bobby Mcferrin",
            "I've just seen Jesus - Larnelle Harris",
            "When Praise Demands a Sacrifice - Larnelle Harris",
            "Sultans of Swing - Dire Straits"
    };

    public List<String> getTitleList() {
        return Arrays.asList(titles);
    }

    public void play(String title) {
        System.out.println("Playing: " + title);
    }
}

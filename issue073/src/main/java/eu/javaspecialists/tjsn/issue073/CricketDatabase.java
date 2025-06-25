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

package eu.javaspecialists.tjsn.issue073;

import java.util.*;

/**
 * Cricket is a boring sport that is popular in countries
 * previously occupied by the British Empire.  Like warm
 * beer, the taste for cricket has to be acquired.  It is
 * probably more of a spectator sport than unterwater hockey,
 * but not by much.
 * <p>
 * South Africa does officially have a cricket team.
 */
public class CricketDatabase implements SportDatabase {
    private static final Player[] p = {
            new Player("12341", "Boeta Dippenaar", new Date(77, 5, 14)),
            new Player("23432", "Gary Kirsten", new Date(67, 10, 23)),
            new Player("23411", "Graeme Smith", new Date(81, 1, 1)),
            new Player("55221", "Jonty Rhodes", new Date(69, 6, 27)),
            new Player("61234", "Monde Zondeki", new Date(82, 6, 25)),
            new Player("23415", "Paul Adams", new Date(77, 0, 20)),
    };

    public Player[] getPlayers() {
        return p;
    }
}